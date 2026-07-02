package controller;

import dao.SanPhamDAO;
import model.Product;
import store.ProductStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/product")
public class ProductDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final SanPhamDAO sanPhamDAO = new SanPhamDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = parseInt(request.getParameter("id"), 0);
        Optional<Product> product;

        try {
            product = sanPhamDAO.findById(productId);
        } catch (IllegalStateException ex) {
            product = ProductStore.findById(productId);
        }

        if (product.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/products?notFound=1");
            return;
        }
        request.setAttribute("product", product.get());
        request.getRequestDispatcher("/product-detail.jsp").forward(request, response);
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return value == null ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }
}
