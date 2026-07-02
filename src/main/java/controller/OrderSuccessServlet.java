package controller;

import dao.DonHangDAO;
import model.Order;
import store.OrderStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/order-success")
public class OrderSuccessServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DonHangDAO donHangDAO = new DonHangDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = parseInt(request.getParameter("id"), 0);
        Order sessionOrder = (Order) request.getSession().getAttribute("lastOrder");
        if (sessionOrder != null && sessionOrder.getMaDH() == orderId) {
            request.setAttribute("order", sessionOrder);
            request.getRequestDispatcher("/order-success.jsp").forward(request, response);
            return;
        }

        Order order;
        try {
            order = donHangDAO.findById(orderId);
        } catch (IllegalStateException ex) {
            order = OrderStore.findById(orderId).orElse(null);
        }

        if (order == null) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }
        request.setAttribute("order", order);
        request.getRequestDispatcher("/order-success.jsp").forward(request, response);
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return value == null ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }
}
