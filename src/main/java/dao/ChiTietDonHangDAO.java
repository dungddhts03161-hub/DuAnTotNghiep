package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.OrderItem;
import model.Product;
import store.ProductStore;
import util.JPAUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChiTietDonHangDAO implements CrudDAO<OrderItem, Integer> {

    @Override
    public int create(OrderItem item) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(item);
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

    public int create(EntityManager em, int maDH, OrderItem item) {
        item.setMaDH(maDH);
        if (item.getMaSP() <= 0 && item.getProduct() != null) {
            item.setMaSP(item.getProduct().getMaSP());
        }
        em.persist(item);
        return 1;
    }

    public int insert(EntityManager em, int maDH, OrderItem item) {
        return create(em, maDH, item);
    }

    @Override
    public int update(OrderItem item) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(item);
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
            OrderItem item = em.find(OrderItem.class, id);
            if (item != null) {
                em.remove(item);
            }
            tx.commit();
            return item == null ? 0 : 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    public int deleteByOrderId(int maDH) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int result = em.createQuery("DELETE FROM ChiTietDonHang i WHERE i.maDH = :maDH")
                    .setParameter("maDH", maDH)
                    .executeUpdate();
            tx.commit();
            return result;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public List<OrderItem> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM ChiTietDonHang i ORDER BY i.maCTDH DESC", OrderItem.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public OrderItem findById(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(OrderItem.class, id);
        } finally {
            em.close();
        }
    }

    public List<OrderItem> findByOrderId(int maDH) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM ChiTietDonHang i LEFT JOIN FETCH i.product WHERE i.maDH = :maDH", OrderItem.class)
                    .setParameter("maDH", maDH)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    @Override
    public List<OrderItem> findBySql(String sql, Object... value) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            jakarta.persistence.Query query = em.createNativeQuery(sql, OrderItem.class);
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
