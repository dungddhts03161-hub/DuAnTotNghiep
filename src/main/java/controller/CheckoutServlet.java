package controller;

import dao.DonHangDAO;
import model.Order;
import model.User;
import store.CartStore;
import store.OrderStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DonHangDAO donHangDAO = new DonHangDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!ensureLoggedIn(request, response)) {
            return;
        }
        if (CartStore.isEmpty(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "/products?emptyCart=1");
            return;
        }
        request.setAttribute("cartItems", CartStore.getItems(request.getSession()));
        request.setAttribute("subtotal", CartStore.getSubtotal(request.getSession()));
        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (!ensureLoggedIn(request, response)) {
            return;
        }
        if (CartStore.isEmpty(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "/products?emptyCart=1");
            return;
        }

        String receiverName = trim(request.getParameter("receiverName"));
        String receiverPhone = trim(request.getParameter("receiverPhone"));
        String address = trim(request.getParameter("address"));
        String note = trim(request.getParameter("note"));
        String paymentMethod = trim(request.getParameter("paymentMethod"));

        if (receiverName.isBlank() || receiverPhone.isBlank() || address.isBlank() || paymentMethod.isBlank()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ họ tên, số điện thoại, địa chỉ và phương thức thanh toán.");
            request.setAttribute("cartItems", CartStore.getItems(request.getSession()));
            request.setAttribute("subtotal", CartStore.getSubtotal(request.getSession()));
            request.getRequestDispatcher("/checkout.jsp").forward(request, response);
            return;
        }

        User user = (User) request.getSession().getAttribute("authUser");
        Order order;
        try {
            order = donHangDAO.createOrder(
                    user,
                    CartStore.getItems(request.getSession()),
                    receiverName,
                    receiverPhone,
                    address,
                    note,
                    paymentMethod
            );
        } catch (SQLException | IllegalStateException ex) {
            // Nếu chưa kết nối SQL Server, vẫn tạo đơn demo bằng Store để giao diện chạy được.
            order = OrderStore.createOrder(
                    user,
                    CartStore.getItems(request.getSession()),
                    receiverName,
                    receiverPhone,
                    address,
                    note,
                    paymentMethod
            );
        }

        request.getSession().setAttribute("lastOrder", order);
        CartStore.clear(request.getSession());
        response.sendRedirect(request.getContextPath() + "/order-success?id=" + order.getMaDH());
    }

    private boolean ensureLoggedIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession().getAttribute("authUser") != null) {
            return true;
        }
        request.getSession().setAttribute("afterLoginRedirect", "/checkout");
        response.sendRedirect(request.getContextPath() + "/auth.jsp?loginRequired=1");
        return false;
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
