package controller;

import dao.SanPhamDAO;
import model.Product;
import store.CartStore;
import store.ProductStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final SanPhamDAO sanPhamDAO = new SanPhamDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("cartItems", CartStore.getItems(request.getSession()));
        request.setAttribute("subtotal", CartStore.getSubtotal(request.getSession()));
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String action = trim(request.getParameter("action"));

        if ("add".equals(action)) {
            try {
				addToCart(request, response);
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return;
        }

        if ("update".equals(action)) {
            String key = trim(request.getParameter("key"));
            int quantity = parseInt(request.getParameter("quantity"), 1);
            CartStore.updateItem(request.getSession(), key, quantity);
            response.sendRedirect(request.getContextPath() + "/cart?updated=1");
            return;
        }

        if ("remove".equals(action)) {
            CartStore.removeItem(request.getSession(), trim(request.getParameter("key")));
            response.sendRedirect(request.getContextPath() + "/cart?removed=1");
            return;
        }

        if ("clear".equals(action)) {
            CartStore.clear(request.getSession());
            response.sendRedirect(request.getContextPath() + "/cart?cleared=1");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int productId = parseInt(request.getParameter("productId"), 0);
        int quantity = parseInt(request.getParameter("quantity"), 1);
        String size = trim(request.getParameter("size"));
        String color = trim(request.getParameter("color"));

        Optional<Product> product;
        try {
            product = sanPhamDAO.findById(productId);
        } catch (IllegalStateException ex) {
            product = ProductStore.findById(productId);
        }

        if (product.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/products?error=" + encode("Không tìm thấy sản phẩm."));
            return;
        }

        Product selected = product.get();
        if (!selected.isAvailable()) {
            response.sendRedirect(request.getContextPath() + "/product?id=" + productId + "&error=" + encode("Sản phẩm đã hết hàng."));
            return;
        }

        if (size.isBlank()) {
            size = selected.getSizes().isEmpty() ? "Free size" : selected.getSizes().get(0);
        }
        if (color.isBlank()) {
            color = selected.getColors().isEmpty() ? "Mặc định" : selected.getColors().get(0);
        }

        CartStore.addItem(request.getSession(), selected, size, color, quantity);
        response.sendRedirect(request.getContextPath() + "/cart?added=1");
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

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
