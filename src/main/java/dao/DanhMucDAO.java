package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Category;
import store.ProductStore;
import util.JPAUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DanhMucDAO implements CrudDAO<Category, Integer> {

    @Override
    public int create(Category category) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(category);
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
    public int update(Category category) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(category);
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
            Category category = em.find(Category.class, id);
            if (category != null) {
                category.setTrangThai(0);
            }
            tx.commit();
            return category == null ? 0 : 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM DanhMuc c ORDER BY c.maDM", Category.class)
                    .getResultList();
        } catch (Exception e) {
            return ProductStore.getCategories();
        } finally {
            em.close();
        }
    }

    @Override
    public Category findById(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Category category = em.find(Category.class, id);
            if (category != null) {
                return category;
            }
            return ProductStore.findCategoryById(id).orElse(null);
        } catch (Exception e) {
            return ProductStore.findCategoryById(id).orElse(null);
        } finally {
            em.close();
        }
    }

    public List<Category> findActive() throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Category> list = em.createQuery(
                            "SELECT c FROM DanhMuc c WHERE c.trangThai = 1 ORDER BY c.tenDM",
                            Category.class)
                    .getResultList();
            return list.isEmpty() ? ProductStore.getCategories() : list;
        } catch (Exception e) {
            return ProductStore.getCategories();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> findBySql(String sql, Object... value) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            jakarta.persistence.Query query = em.createNativeQuery(sql, Category.class);
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
