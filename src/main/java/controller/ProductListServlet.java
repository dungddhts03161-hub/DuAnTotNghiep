package controller;

import dao.DanhMucDAO;
import dao.SanPhamDAO;
import store.ProductStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final SanPhamDAO sanPhamDAO = new SanPhamDAO();
    private final DanhMucDAO danhMucDAO = new DanhMucDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String keyword = trim(request.getParameter("q"));
        int categoryId = parseInt(request.getParameter("category"), 0);

        try {
            request.setAttribute("products", sanPhamDAO.search(keyword, categoryId));
            request.setAttribute("categories", danhMucDAO.findActive());
            request.setAttribute("dataMode", "database");
        } catch (SQLException | IllegalStateException ex) {
            // Nếu chưa cấu hình SQL Server, vẫn cho giao diện chạy bằng dữ liệu mẫu.
            request.setAttribute("products", ProductStore.search(keyword, categoryId));
            request.setAttribute("categories", ProductStore.getCategories());
            request.setAttribute("dataMode", "demo");
            request.setAttribute("dbNotice", "Đang chạy dữ liệu demo. Muốn dùng SQL Server bằng JPA, hãy chạy Database.sql và chỉnh src/main/resources/META-INF/persistence.xml.");
        }

        request.setAttribute("keyword", keyword);
        request.setAttribute("categoryId", categoryId);
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return value == null ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }
}
