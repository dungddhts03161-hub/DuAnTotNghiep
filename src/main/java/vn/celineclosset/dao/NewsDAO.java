package vn.celineclosset.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class NewsDAO extends CrudDAO {
    private final NewsCategoryDAO categoryDAO = new NewsCategoryDAO();

    public List<Map<String, Object>> all(String q) throws SQLException {
        categoryDAO.ensureSchema();
        if (q == null || q.isBlank()) {
            return query("""
                    SELECT tt.*, lt.tenLoai
                    FROM TIN_TUC tt
                    LEFT JOIN LOAI_TIN_TUC lt ON tt.maLoaiTin=lt.maLoaiTin
                    ORDER BY tt.ngayCapNhat DESC, tt.maTin DESC
                    """);
        }
        String like = "%" + q.trim() + "%";
        return query("""
                SELECT tt.*, lt.tenLoai
                FROM TIN_TUC tt
                LEFT JOIN LOAI_TIN_TUC lt ON tt.maLoaiTin=lt.maLoaiTin
                WHERE tt.tieuDe LIKE ? OR tt.tomTat LIKE ? OR tt.noiDung LIKE ? OR lt.tenLoai LIKE ?
                ORDER BY tt.ngayCapNhat DESC, tt.maTin DESC
                """, like, like, like, like);
    }

    public List<Map<String, Object>> published(String categoryId) throws SQLException {
        categoryDAO.ensureSchema();
        if (categoryId == null || categoryId.isBlank()) {
            return query("""
                    SELECT tt.*, lt.tenLoai
                    FROM TIN_TUC tt
                    LEFT JOIN LOAI_TIN_TUC lt ON tt.maLoaiTin=lt.maLoaiTin
                    WHERE tt.trangThai=1 AND (lt.trangThai=1 OR tt.maLoaiTin IS NULL)
                    ORDER BY tt.ngayCapNhat DESC, tt.maTin DESC
                    """);
        }
        int parsedCategoryId;
        try {
            parsedCategoryId = Integer.parseInt(categoryId.trim());
        } catch (NumberFormatException e) {
            return published(null);
        }
        return query("""
                SELECT tt.*, lt.tenLoai
                FROM TIN_TUC tt
                LEFT JOIN LOAI_TIN_TUC lt ON tt.maLoaiTin=lt.maLoaiTin
                WHERE tt.trangThai=1 AND tt.maLoaiTin=? AND lt.trangThai=1
                ORDER BY tt.ngayCapNhat DESC, tt.maTin DESC
                """, parsedCategoryId);
    }

    public Map<String, Object> byId(int id) throws SQLException {
        categoryDAO.ensureSchema();
        return queryOne("""
                SELECT tt.*, lt.tenLoai
                FROM TIN_TUC tt
                LEFT JOIN LOAI_TIN_TUC lt ON tt.maLoaiTin=lt.maLoaiTin
                WHERE tt.maTin=?
                """, id);
    }

    /** Chỉ trả bài viết đang được phép hiển thị ở phía khách hàng. */
    public Map<String, Object> publishedById(int id) throws SQLException {
        categoryDAO.ensureSchema();
        return queryOne("""
                SELECT tt.*, lt.tenLoai
                FROM TIN_TUC tt
                LEFT JOIN LOAI_TIN_TUC lt ON tt.maLoaiTin=lt.maLoaiTin
                WHERE tt.maTin=? AND tt.trangThai=1
                  AND (lt.trangThai=1 OR tt.maLoaiTin IS NULL)
                """, id);
    }

    public void save(String idValue, String title, String summary, String content,
                     String image, int categoryId, int status, int authorId) throws SQLException {
        categoryDAO.ensureSchema();
        int id = parseId(idValue);
        String cleanImage = image == null || image.isBlank() ? null : image.trim();
        Integer cleanCategoryId = categoryId > 0 ? categoryId : null;
        if (id <= 0) {
            executeUpdate("""
                    INSERT INTO TIN_TUC(tieuDe,tomTat,noiDung,hinhAnh,maLoaiTin,trangThai,maNguoiTao,ngayTao,ngayCapNhat)
                    VALUES(?,?,?,?,?,?,?,SYSDATETIME(),SYSDATETIME())
                    """, text(title), text(summary), text(content), cleanImage, cleanCategoryId, status, authorId);
        } else {
            executeUpdate("""
                    UPDATE TIN_TUC
                    SET tieuDe=?, tomTat=?, noiDung=?, hinhAnh=?, maLoaiTin=?, trangThai=?, ngayCapNhat=SYSDATETIME()
                    WHERE maTin=?
                    """, text(title), text(summary), text(content), cleanImage, cleanCategoryId, status, id);
        }
    }

    public void toggle(int id, int status) throws SQLException {
        categoryDAO.ensureSchema();
        executeUpdate("UPDATE TIN_TUC SET trangThai=?, ngayCapNhat=SYSDATETIME() WHERE maTin=?", status, id);
    }

    public void delete(int id) throws SQLException {
        categoryDAO.ensureSchema();
        executeUpdate("DELETE FROM TIN_TUC WHERE maTin=?", id);
    }

    private int parseId(String value) {
        try {
            return value == null || value.isBlank() ? 0 : Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String text(String value) {
        return value == null ? "" : value.trim();
    }
}
