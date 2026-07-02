<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%@ page import="model.Order" %>
<%@ page import="model.OrderItem" %>
<%@ page import="store.CartStore" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%!
  private String h(String value) {
    if (value == null) return "";
    return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
  }
  private String money(java.math.BigDecimal value) {
    return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(value);
  }
%>
<%
  String ctx = request.getContextPath();
  User authUser = (User) session.getAttribute("authUser");
  Order order = (Order) request.getAttribute("order");
  int cartCount = CartStore.getTotalQuantity(session);
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Đặt hàng thành công | Celine Closet</title>
  <link rel="stylesheet" href="<%= ctx %>/css/style.css" />
</head>
<body>
  <div class="top-line">Miễn phí vận chuyển mọi đơn hàng</div>
  <header class="main-header shop-header">
    <a class="logo" href="<%= ctx %>/index.jsp"><span class="logo-main">Celine</span><span class="logo-sub">Closet</span></a>
    <button class="menu-toggle" aria-label="Mở menu" aria-expanded="false"><span></span><span></span><span></span></button>
    <nav class="nav">
      <a href="<%= ctx %>/products">Shop Online</a>
      <a href="<%= ctx %>/cart">Giỏ hàng (<%= cartCount %>)</a>
      <% if (authUser == null) { %><a href="<%= ctx %>/auth.jsp">Tài khoản</a><% } else { %><span class="hello">Hi, <%= h(authUser.getFullName()) %></span><a href="<%= ctx %>/logout">Đăng xuất</a><% } %>
    </nav>
  </header>

  <main class="success-page">
    <section class="success-card">
      <span class="success-icon">✓</span>
      <span class="tiny-title">Order created</span>
      <h1>Đặt hàng thành công</h1>
      <p>Mã đơn hàng của bạn là <strong>#<%= order.getMaDH() %></strong>. Trạng thái hiện tại: <strong><%= h(order.getTrangThai()) %></strong>.</p>
      <div class="success-grid">
        <div><span>Ngày đặt</span><strong><%= h(order.getNgayDatText()) %></strong></div>
        <div><span>Người nhận</span><strong><%= h(order.getHoTenNhan()) %></strong></div>
        <div><span>Số điện thoại</span><strong><%= h(order.getSoDienThoaiNhan()) %></strong></div>
        <div><span>Thanh toán</span><strong><%= h(order.getPhuongThucThanhToan()) %></strong></div>
        <div class="wide-info"><span>Địa chỉ</span><strong><%= h(order.getDiaChiNhan()) %></strong></div>
      </div>

      <div class="success-items">
        <% for (OrderItem item : order.getItems()) { %>
          <div>
            <span><%= h(item.getProduct().getTenSP()) %> × <%= item.getSoLuong() %></span>
            <strong><%= money(item.getThanhTien()) %></strong>
          </div>
        <% } %>
        <div class="grand-total"><span>Tổng tiền</span><strong><%= money(order.getTongTien()) %></strong></div>
      </div>

      <div class="success-actions">
        <a class="solid-dark-btn" href="<%= ctx %>/products">Tiếp tục mua sắm</a>
        <a class="ghost-btn dark" href="<%= ctx %>/index.jsp">Về trang chủ</a>
      </div>
    </section>
  </main>
  <script src="<%= ctx %>/js/main.js"></script>
</body>
</html>
