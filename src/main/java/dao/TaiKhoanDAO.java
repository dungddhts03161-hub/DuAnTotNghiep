package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.User;
import util.JPAUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaiKhoanDAO implements CrudDAO<User, Integer> {

    @Override
    public int create(User user) {
        try {
            insertAndReturn(user);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public User insertAndReturn(User user) throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (user.getVaiTro() == null || user.getVaiTro().isBlank()) {
                user.setVaiTro("KHACH_HANG");
            }
            if (user.getTrangThai() != 0) {
                user.setTrangThai(1);
            }
            em.persist(user);
            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new SQLException("Không thể thêm tài khoản bằng JPA.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public int update(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(user);
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
    public int delete(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User user = em.find(User.class, id);
            if (user != null) {
                user.setTrangThai(0);
            }
            tx.commit();
            return user == null ? 0 : 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public List<User> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM TaiKhoan u ORDER BY u.maTK DESC", User.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public User findById(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public Optional<User> findByIdentity(String identity) throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<User> list = em.createQuery(
                            "SELECT u FROM TaiKhoan u WHERE u.trangThai = 1 AND (u.email = :identity OR u.phone = :identity)",
                            User.class)
                    .setParameter("identity", identity)
                    .setMaxResults(1)
                    .getResultList();
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
        } catch (Exception e) {
            throw new SQLException("Không thể tìm tài khoản bằng JPA.", e);
        } finally {
            em.close();
        }
    }

    public boolean emailExists(String email) throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long count = em.createQuery("SELECT COUNT(u) FROM TaiKhoan u WHERE u.email = :email", Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return count != null && count > 0;
        } catch (Exception e) {
            throw new SQLException("Không thể kiểm tra email bằng JPA.", e);
        } finally {
            em.close();
        }
    }

    public boolean phoneExists(String phone) throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long count = em.createQuery("SELECT COUNT(u) FROM TaiKhoan u WHERE u.phone = :phone", Long.class)
                    .setParameter("phone", phone)
                    .getSingleResult();
            return count != null && count > 0;
        } catch (Exception e) {
            throw new SQLException("Không thể kiểm tra số điện thoại bằng JPA.", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<User> findBySql(String sql, Object... value) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            jakarta.persistence.Query query = em.createNativeQuery(sql, User.class);
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
}
