package controller;

import dao.TaiKhoanDAO;
import model.User;
import store.UserStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String identity = trim(request.getParameter("identity"));
        String password = trim(request.getParameter("password"));

        if (identity.isEmpty() || password.isEmpty()) {
            forwardError(request, response, "Vui lòng nhập đầy đủ email/số điện thoại và mật khẩu.");
            return;
        }

        User user = findUser(identity);
        if (user == null || !user.checkPassword(password)) {
            forwardError(request, response, "Thông tin đăng nhập chưa đúng. Bạn có thể thử tài khoản demo@celinecloset.vn / 123456.");
            return;
        }

        request.getSession().setAttribute("authUser", user);
        String redirect = (String) request.getSession().getAttribute("afterLoginRedirect");
        if (redirect != null && redirect.startsWith("/")) {
            request.getSession().removeAttribute("afterLoginRedirect");
            response.sendRedirect(request.getContextPath() + redirect);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    private User findUser(String identity) {
        try {
            return taiKhoanDAO.findByIdentity(identity).orElseGet(() -> UserStore.findByIdentity(identity));
        } catch (SQLException | IllegalStateException ex) {
            return UserStore.findByIdentity(identity);
        }
    }

    private void forwardError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("mode", "login");
        request.setAttribute("error", message);
        request.getRequestDispatcher("/auth.jsp").forward(request, response);
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
