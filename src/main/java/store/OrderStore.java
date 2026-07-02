package store;

import model.CartItem;
import model.Order;
import model.OrderItem;
import model.Payment;
import model.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public final class OrderStore {
    private static final AtomicInteger ORDER_ID = new AtomicInteger(1001);
    private static final AtomicInteger ORDER_DETAIL_ID = new AtomicInteger(1);
    private static final AtomicInteger PAYMENT_ID = new AtomicInteger(1);
    private static final Map<Integer, Order> ORDERS = new LinkedHashMap<>();

    private OrderStore() {
    }

    public static synchronized Order createOrder(User user, Collection<CartItem> cartItems,
                                                 String hoTenNhan, String soDienThoaiNhan,
                                                 String diaChiNhan, String ghiChu,
                                                 String phuongThucThanhToan) {
        int maDH = ORDER_ID.getAndIncrement();
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem(
                    ORDER_DETAIL_ID.getAndIncrement(),
                    cartItem.getProduct(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getDonGia()
            );
            orderItems.add(orderItem);
            total = total.add(orderItem.getThanhTien());
        }

        Payment payment = new Payment(
                PAYMENT_ID.getAndIncrement(),
                phuongThucThanhToan,
                total,
                LocalDateTime.now(),
                "COD".equals(phuongThucThanhToan) ? "Chờ thanh toán" : "Chờ xác nhận",
                maDH
        );

        Order order = new Order(
                maDH,
                LocalDateTime.now(),
                total,
                "Chờ xác nhận",
                phuongThucThanhToan,
                user == null ? 0 : user.getMaTK(),
                hoTenNhan,
                soDienThoaiNhan,
                diaChiNhan,
                ghiChu,
                orderItems,
                payment
        );
        ORDERS.put(maDH, order);
        return order;
    }

    public static Optional<Order> findById(int maDH) {
        return Optional.ofNullable(ORDERS.get(maDH));
    }

    public static Collection<Order> getAllOrders() {
        return Collections.unmodifiableCollection(ORDERS.values());
    }
}
