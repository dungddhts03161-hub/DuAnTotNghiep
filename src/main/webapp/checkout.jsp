<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%@ page import="model.CartItem" %>
<%@ page import="store.CartStore" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Collection" %>
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
  Collection<CartItem> cartItems = (Collection<CartItem>) request.getAttribute("cartItems");
  if (cartItems == null) cartItems = CartStore.getItems(session);
  String error = (String) request.getAttribute("error");
  int cartCount = CartStore.getTotalQuantity(session);
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Đặt hàng | Celine Closet</title>
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

  <main class="checkout-page">
    <section class="shop-hero mini-hero">
      <span class="tiny-title">Checkout</span>
      <h1>Đặt hàng</h1>
      <p>Nhập thông tin giao hàng, chọn phương thức thanh toán và hệ thống tự tạo mã đơn hàng theo UC05.</p>
    </section>

    <% if (error != null) { %><div class="server-alert error checkout-alert"><%= h(error) %></div><% } %>

    <section class="checkout-layout">
      <form class="checkout-form" method="post" action="<%= ctx %>/checkout">
        <h2>Thông tin giao hàng</h2>
        <label>Họ tên người nhận
          <input type="text" name="receiverName" value="<%= authUser == null ? "" : h(authUser.getFullName()) %>" required />
        </label>
        <label>Số điện thoại liên hệ
          <input type="tel" name="receiverPhone" value="<%= authUser == null ? "" : h(authUser.getPhone()) %>" required />
        </label>
        <label>Địa chỉ nhận hàng
          <textarea name="address" rows="4" placeholder="Số nhà, đường, phường/xã, quận/huyện, tỉnh/thành" required></textarea>
        </label>
        <label>Ghi chú
          <textarea name="note" rows="3" placeholder="Ví dụ: giao giờ hành chính, gọi trước khi giao..."></textarea>
        </label>

        <h2>Phương thức thanh toán</h2>
        <label class="radio-card">
          <input type="radio" name="paymentMethod" value="COD" checked />
          <span><strong>COD</strong> Thanh toán khi nhận hàng</span>
        </label>
        <label class="radio-card">
          <input type="radio" name="paymentMethod" value="TRANSFER" />
          <span><strong>Chuyển khoản</strong> Nhân viên xác nhận sau khi đặt</span>
        </label>

        <button class="solid-dark-btn wide" type="submit">Xác nhận đặt hàng</button>
      </form>

      <aside class="order-review">
        <h2>Sản phẩm đã chọn</h2>
        <% for (CartItem item : cartItems) { %>
          <div class="review-row">
            <img src="<%= ctx %>/<%= h(item.getProduct().getHinhAnh()) %>" alt="<%= h(item.getProduct().getTenSP()) %>" />
            <div>
              <strong><%= h(item.getProduct().getTenSP()) %></strong>
              <span>SL: <%= item.getQuantity() %> · Size <%= h(item.getSize()) %> · <%= h(item.getColor()) %></span>
            </div>
            <b><%= money(item.getThanhTien()) %></b>
          </div>
        <% } %>
        <div class="grand-total review-total"><span>Tổng thanh toán</span><strong><%= money(CartStore.getSubtotal(session)) %></strong></div>
      </aside>
    </section>
  </main>
  <script src="<%= ctx %>/js/main.js"></script>
</body>
</html>
