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
import java.util.regex.Pattern;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(0|\\+84)[0-9]{9,10}$");

    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String fullName = trim(request.getParameter("fullname"));
        String phone = trim(request.getParameter("phone"));
        String email = trim(request.getParameter("email"));
        String password = trim(request.getParameter("password"));
        String confirm = trim(request.getParameter("confirm"));
        boolean acceptedTerms = request.getParameter("terms") != null;

        String validationError = validate(fullName, phone, email, password, confirm, acceptedTerms);
        if (validationError != null) {
            forwardRegisterError(request, response, validationError);
            return;
        }

        try {
            if (taiKhoanDAO.emailExists(email) || UserStore.emailExists(email)) {
                forwardRegisterError(request, response, "Email này đã được đăng ký. Vui lòng dùng email khác.");
                return;
            }
            if (taiKhoanDAO.phoneExists(phone) || UserStore.phoneExists(phone)) {
                forwardRegisterError(request, response, "Số điện thoại này đã được đăng ký. Vui lòng dùng số khác.");
                return;
            }
            taiKhoanDAO.insertAndReturn(new User(fullName, phone, email, password));
        } catch (SQLException | IllegalStateException ex) {
            if (UserStore.emailExists(email)) {
                forwardRegisterError(request, response, "Email này đã được đăng ký. Vui lòng dùng email khác.");
                return;
            }
            if (UserStore.phoneExists(phone)) {
                forwardRegisterError(request, response, "Số điện thoại này đã được đăng ký. Vui lòng dùng số khác.");
                return;
            }
            UserStore.register(new User(fullName, phone, email, password));
        }

        request.setAttribute("mode", "login");
        request.setAttribute("success", "Tạo tài khoản thành công. Bạn có thể đăng nhập ngay bằng email hoặc số điện thoại vừa đăng ký.");
        request.getRequestDispatcher("/auth.jsp").forward(request, response);
    }

    private String validate(String fullName, String phone, String email, String password, String confirm, boolean acceptedTerms) {
        if (fullName.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            return "Vui lòng nhập đầy đủ thông tin đăng ký.";
        }
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            return "Số điện thoại chưa đúng định dạng. Ví dụ hợp lệ: 0901234567.";
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return "Email chưa đúng định dạng.";
        }
        if (password.length() < 6) {
            return "Mật khẩu cần tối thiểu 6 ký tự.";
        }
        if (!password.equals(confirm)) {
            return "Mật khẩu nhập lại chưa khớp.";
        }
        if (!acceptedTerms) {
            return "Bạn cần đồng ý với điều khoản sử dụng.";
        }
        return null;
    }

    private void forwardRegisterError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("mode", "register");
        request.setAttribute("error", message);
        request.getRequestDispatcher("/auth.jsp").forward(request, response);
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
