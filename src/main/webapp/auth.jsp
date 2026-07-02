<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%!
  private String h(String value) {
    if (value == null) return "";
    return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
  }
%>
<%
  String ctx = request.getContextPath();
  User authUser = (User) session.getAttribute("authUser");
  String mode = (String) request.getAttribute("mode");
  if (mode == null || mode.isBlank()) {
    mode = "register".equals(request.getParameter("mode")) ? "register" : "login";
  }
  String error = (String) request.getAttribute("error");
  String success = (String) request.getAttribute("success");
  if (success == null && "1".equals(request.getParameter("logout"))) {
    success = "Bạn đã đăng xuất thành công.";
  }
  if (success == null && "1".equals(request.getParameter("loginRequired"))) {
    success = "Vui lòng đăng nhập trước khi đặt hàng.";
  }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Tài khoản | Celine Closet</title>
  <link rel="preconnect" href="https://images.unsplash.com" />
  <link rel="stylesheet" href="<%= ctx %>/css/style.css" />
</head>
<body class="auth-body">
  <div class="top-line">Miễn phí vận chuyển mọi đơn hàng</div>

  <header class="main-header" id="top">
    <a class="logo" href="<%= ctx %>/index.jsp" aria-label="Celine Closet">
      <span class="logo-main">Celine</span>
      <span class="logo-sub">Closet</span>
    </a>

    <button class="menu-toggle" aria-label="Mở menu" aria-expanded="false">
      <span></span><span></span><span></span>
    </button>

    <nav class="nav" aria-label="Điều hướng chính">
      <a href="<%= ctx %>/index.jsp#about">Giới thiệu</a>
      <a href="<%= ctx %>/products">Shop Online</a>
      <a href="<%= ctx %>/blog">Blog</a>
      <a href="<%= ctx %>/lookbook">Lookbook</a>
      <a href="<%= ctx %>/index.jsp#showroom">Hệ thống showroom</a>
      <a href="<%= ctx %>/index.jsp#guide">Hướng dẫn mua hàng</a>
      <a href="<%= ctx %>/products" class="nav-icon" aria-label="Tìm kiếm">
        <svg viewBox="0 0 24 24" aria-hidden="true"><path d="M10.8 18.1a7.3 7.3 0 1 1 0-14.6 7.3 7.3 0 0 1 0 14.6Zm0-1.3a6 6 0 1 0 0-12 6 6 0 0 0 0 12Zm5.2.1 4.1 4.1-.9.9-4.1-4.1.9-.9Z"/></svg>
      </a>
      <a href="<%= ctx %>/cart">Giỏ hàng</a>
      <% if (authUser == null) { %>
        <a class="account-link active-link" href="<%= ctx %>/auth.jsp">Tài khoản</a>
      <% } else { %>
        <span class="hello">Hi, <%= h(authUser.getFullName()) %></span>
        <a class="account-link" href="<%= ctx %>/logout">Đăng xuất</a>
      <% } %>
    </nav>
  </header>

  <main class="auth-layout">
    <section class="auth-visual" aria-label="Celine Closet visual">
      <img src="https://images.unsplash.com/photo-1483985988355-763728e1935b?auto=format&fit=crop&w=1300&q=85" alt="Celine Closet fashion" />
      <div class="auth-visual-overlay">
        <span class="tiny-title">Welcome</span>
        <h1>Celine Closet</h1>
        <p>Đăng nhập để tiếp tục trải nghiệm giao diện boutique thời trang nữ.</p>
      </div>
    </section>

    <section class="auth-box" aria-label="Form tài khoản">
      <div class="auth-tabs" role="tablist">
        <button class="tab-btn active" data-tab="login" type="button">Đăng nhập</button>
        <button class="tab-btn" data-tab="register" type="button">Tạo tài khoản</button>
      </div>

      <% if (error != null) { %>
        <div class="server-alert error"><%= h(error) %></div>
      <% } %>
      <% if (success != null) { %>
        <div class="server-alert success"><%= h(success) %></div>
      <% } %>

      <form class="auth-form active" id="loginForm" data-form="login" method="post" action="<%= ctx %>/login" novalidate>
        <span class="form-kicker">Xin chào nàng</span>
        <h2>Đăng nhập</h2>
        <p class="form-note">Có thể thử nhanh bằng tài khoản demo: <b>demo@celinecloset.vn</b> / <b>123456</b></p>

        <label>
          Email / Số điện thoại
          <input type="text" name="identity" placeholder="Nhập email hoặc số điện thoại" required />
          <small class="error"></small>
        </label>
        <label>
          Mật khẩu
          <input type="password" name="password" placeholder="Nhập mật khẩu" required minlength="6" />
          <small class="error"></small>
        </label>

        <div class="form-row">
          <label class="checkbox-line"><input type="checkbox" /> Ghi nhớ đăng nhập</label>
          <a href="#">Quên mật khẩu?</a>
        </div>

        <button class="submit-btn" type="submit">Đăng nhập</button>
        <p class="switch-text">Chưa có tài khoản? <button type="button" data-switch="register">Tạo tài khoản mới</button></p>
      </form>

      <form class="auth-form" id="registerForm" data-form="register" method="post" action="<%= ctx %>/register" novalidate>
        <span class="form-kicker">Join us</span>
        <h2>Tạo tài khoản</h2>
        <p class="form-note">Nhập thông tin cơ bản để tạo tài khoản mua sắm.</p>

        <div class="two-cols">
          <label>
            Họ tên
            <input type="text" name="fullname" placeholder="Nguyễn Celine" required />
            <small class="error"></small>
          </label>
          <label>
            Số điện thoại
            <input type="tel" name="phone" placeholder="0901234567" required pattern="^(0|\+84)[0-9]{9,10}$" />
            <small class="error"></small>
          </label>
        </div>

        <label>
          Email
          <input type="email" name="email" placeholder="celine@email.com" required />
          <small class="error"></small>
        </label>
        <label>
          Mật khẩu
          <input type="password" name="password" placeholder="Tối thiểu 6 ký tự" required minlength="6" />
          <small class="error"></small>
        </label>
        <label>
          Nhập lại mật khẩu
          <input type="password" name="confirm" placeholder="Nhập lại mật khẩu" required minlength="6" />
          <small class="error"></small>
        </label>

        <label class="checkbox-line terms"><input type="checkbox" name="terms" required /> Tôi đồng ý với điều khoản sử dụng</label>
        <small class="error terms-error"></small>

        <button class="submit-btn" type="submit">Tạo tài khoản</button>
        <p class="switch-text">Đã có tài khoản? <button type="button" data-switch="login">Đăng nhập</button></p>
      </form>

      <div class="toast" aria-live="polite"></div>
    </section>
  </main>

  <script>
    window.CELINE_AUTH_MODE = "<%= h(mode) %>";
  </script>
  <script src="<%= ctx %>/js/auth.js"></script>
</body>
</html>