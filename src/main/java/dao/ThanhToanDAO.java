package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Payment;
import util.JPAUtil;

import java.util.ArrayList;
import java.util.List;

public class ThanhToanDAO implements CrudDAO<Payment, Integer> {

    @Override
    public int create(Payment payment) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(payment);
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

    public int create(EntityManager em, Payment payment) {
        em.persist(payment);
        return 1;
    }

    public int insert(EntityManager em, Payment payment) {
        return create(em, payment);
    }

    @Override
    public int update(Payment payment) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(payment);
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
            Payment payment = em.find(Payment.class, id);
            if (payment != null) {
                em.remove(payment);
            }
            tx.commit();
            return payment == null ? 0 : 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Payment> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM ThanhToan p ORDER BY p.maTT DESC", Payment.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Payment findById(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Payment.class, id);
        } finally {
            em.close();
        }
    }

    public Payment findByOrderId(int maDH) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Payment> list = em.createQuery("SELECT p FROM ThanhToan p WHERE p.maDH = :maDH", Payment.class)
                    .setParameter("maDH", maDH)
                    .setMaxResults(1)
                    .getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    public int updateStatus(int maTT, String trangThai) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Payment payment = em.find(Payment.class, maTT);
            if (payment != null) {
                payment.setTrangThai(trangThai);
            }
            tx.commit();
            return payment == null ? 0 : 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    public int updateStatusByOrderId(int maDH, String trangThai) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int result = em.createQuery("UPDATE ThanhToan p SET p.trangThai = :trangThai WHERE p.maDH = :maDH")
                    .setParameter("trangThai", trangThai)
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
    public List<Payment> findBySql(String sql, Object... value) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            jakarta.persistence.Query query = em.createNativeQuery(sql, Payment.class);
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
