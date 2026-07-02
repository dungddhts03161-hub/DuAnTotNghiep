package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Product;
import store.ProductStore;
import util.JPAUtil;
import util.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SanPhamDAO {

    public List<Product> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Product> list = em.createQuery(
                            "SELECT p FROM SanPham p WHERE p.trangThai = 1 ORDER BY p.maSP DESC",
                            Product.class)
                    .getResultList();
            return list.isEmpty() ? ProductStore.getAllProducts() : list;
        } catch (Exception e) {
            return ProductStore.getAllProducts();
        } finally {
            em.close();
        }
    }

    public Optional<Product> findById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Product product = em.find(Product.class, id);
            if (product != null) {
                return Optional.of(product);
            }
            return ProductStore.findById(id);
        } catch (Exception e) {
            return ProductStore.findById(id);
        } finally {
            em.close();
        }
    }

    public List<Product> search(String keyword) {
        return search(keyword, 0);
    }

    public List<Product> search(String keyword, int categoryId) {
        EntityManager em = JPAUtil.getEntityManager();
        String key = TextUtil.trim(keyword);
        try {
            String jpql = "SELECT p FROM SanPham p "
                    + "LEFT JOIN FETCH p.danhMuc c "
                    + "WHERE p.trangThai = 1 "
                    + "AND (:categoryId <= 0 OR c.maDM = :categoryId) "
                    + "AND (:keyword = '' OR LOWER(p.tenSP) LIKE :likeKeyword OR STR(p.maSP) LIKE :likeKeyword) "
                    + "ORDER BY p.maSP DESC";

            List<Product> list = em.createQuery(jpql, Product.class)
                    .setParameter("categoryId", categoryId)
                    .setParameter("keyword", key.toLowerCase())
                    .setParameter("likeKeyword", "%" + key.toLowerCase() + "%")
                    .getResultList();

            return list.isEmpty() ? ProductStore.search(keyword, categoryId) : list;
        } catch (Exception e) {
            return ProductStore.search(keyword, categoryId);
        } finally {
            em.close();
        }
    }

    public boolean decreaseStock(EntityManager em, int maSP, int soLuong) {
        Product product = em.find(Product.class, maSP);
        if (product == null || product.getSoLuongTon() < soLuong) {
            return false;
        }
        product.setSoLuongTon(product.getSoLuongTon() - soLuong);
        em.merge(product);
        return true;
    }

    public boolean decreaseStock(int maSP, int soLuong) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            boolean result = decreaseStock(em, maSP, soLuong);
            tx.commit();
            return result;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public List<Product> findBySql(String sql, Object... value) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            jakarta.persistence.Query query = em.createNativeQuery(sql, Product.class);
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
