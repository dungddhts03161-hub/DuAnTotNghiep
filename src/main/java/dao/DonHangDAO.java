package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.CartItem;
import model.Order;
import model.OrderItem;
import model.Payment;
import model.User;
import util.JPAUtil;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DonHangDAO implements CrudDAO<Order, Integer> {

    private final ChiTietDonHangDAO chiTietDonHangDAO = new ChiTietDonHangDAO();
    private final ThanhToanDAO thanhToanDAO = new ThanhToanDAO();
    private final SanPhamDAO sanPhamDAO = new SanPhamDAO();

    public Order createOrder(User user,
                             Collection<CartItem> cartItems,
                             String hoTenNhan,
                             String soDienThoaiNhan,
                             String diaChiNhan,
                             String ghiChu,
                             String phuongThucThanhToan) throws SQLException {

        if (user == null) {
            throw new SQLException("Người dùng chưa đăng nhập.");
        }
        if (cartItems == null || cartItems.isEmpty()) {
            throw new SQLException("Giỏ hàng đang trống.");
        }

        String dbPaymentMethod = normalizePaymentMethod(phuongThucThanhToan);
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal tongTien = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            OrderItem item = new OrderItem(0, cartItem.getProduct(), cartItem.getQuantity(), cartItem.getProduct().getDonGia());
            orderItems.add(item);
            tongTien = tongTien.add(item.getThanhTien());
        }

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Order order = new Order(
                    0,
                    LocalDateTime.now(),
                    tongTien,
                    "CHO_XAC_NHAN",
                    dbPaymentMethod,
                    user.getMaTK(),
                    hoTenNhan,
                    soDienThoaiNhan,
                    diaChiNhan,
                    ghiChu,
                    orderItems,
                    null
            );

            em.persist(order);
            em.flush();

            for (OrderItem item : orderItems) {
                boolean updatedStock = sanPhamDAO.decreaseStock(em, item.getMaSP(), item.getSoLuong());
                if (!updatedStock) {
                    throw new SQLException("Sản phẩm mã " + item.getMaSP() + " không đủ tồn kho.");
                }

                int inserted = chiTietDonHangDAO.create(em, order.getMaDH(), item);
                if (inserted <= 0) {
                    throw new SQLException("Không thêm được chi tiết đơn hàng.");
                }
            }

            Payment payment = new Payment(
                    0,
                    dbPaymentMethod,
                    tongTien,
                    LocalDateTime.now(),
                    "COD".equalsIgnoreCase(dbPaymentMethod) ? "CHUA_THANH_TOAN" : "DA_THANH_TOAN",
                    order.getMaDH()
            );
            thanhToanDAO.create(em, payment);

            tx.commit();

            order.setItems(orderItems);
            order.setPayment(payment);
            return order;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            if (e instanceof SQLException) {
                throw (SQLException) e;
            }
            throw new SQLException("Không thể tạo đơn hàng bằng JPA.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public int create(Order order) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (order.getNgayDat() == null) {
                order.setNgayDat(LocalDateTime.now());
            }
            em.persist(order);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public int update(Order order) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(order);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    public int updateStatus(int maDH, String trangThai) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Order order = em.find(Order.class, maDH);
            if (order != null) {
                order.setTrangThai(trangThai);
            }
            tx.commit();
            return order == null ? 0 : 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public int delete(Integer maDH) {
        return updateStatus(maDH, "DA_HUY");
    }

    @Override
    public List<Order> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT o FROM DonHang o ORDER BY o.maDH DESC", Order.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Order findById(Integer maDH) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Order order = em.find(Order.class, maDH);
            if (order != null) {
                order.setItems(chiTietDonHangDAO.findByOrderId(maDH));
                order.setPayment(thanhToanDAO.findByOrderId(maDH));
            }
            return order;
        } finally {
            em.close();
        }
    }

    public List<Order> findByUserId(int maTK) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT o FROM DonHang o WHERE o.maTK = :maTK ORDER BY o.maDH DESC", Order.class)
                    .setParameter("maTK", maTK)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Order> findBySql(String sql, Object... value) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            jakarta.persistence.Query query = em.createNativeQuery(sql, Order.class);
            for (int i = 0; i < value.length; i++) {
                query.setParameter(i + 1, value[i]);
            }
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    private String normalizePaymentMethod(String method) {
        if (method == null || method.isBlank()) {
            return "COD";
        }
        String cleaned = method.trim().toUpperCase();
        switch (cleaned) {
            case "TRANSFER":
            case "BANK":
            case "BANK_TRANSFER":
                return "CHUYEN_KHOAN";
            case "CARD":
                return "THE";
            case "WALLET":
                return "VI_DIEN_TU";
            default:
                return cleaned;
        }
    }
}
