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
  int cartCount = CartStore.getTotalQuantity(session);
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Giỏ hàng | Celine Closet</title>
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

  <main class="cart-page">
    <section class="shop-hero mini-hero">
      <span class="tiny-title">Cart</span>
      <h1>Giỏ hàng</h1>
      <p>Chức năng thêm, cập nhật số lượng và xóa sản phẩm trong giỏ hàng theo UC04.</p>
    </section>

    <% if (cartItems.isEmpty()) { %>
      <section class="empty-state">
        <h2>Giỏ hàng đang trống</h2>
        <p>Bạn hãy chọn một vài sản phẩm trước khi đặt hàng nhé.</p>
        <a class="solid-dark-btn" href="<%= ctx %>/products">Xem sản phẩm</a>
      </section>
    <% } else { %>
      <section class="cart-layout">
        <div class="cart-list">
          <% for (CartItem item : cartItems) { %>
            <article class="cart-row">
              <img src="<%= ctx %>/<%= h(item.getProduct().getHinhAnh()) %>" alt="<%= h(item.getProduct().getTenSP()) %>" />
              <div class="cart-row-info">
                <span>MSP<%= item.getProduct().getMaSP() %></span>
                <h3><%= h(item.getProduct().getTenSP()) %></h3>
                <p>Size: <%= h(item.getSize()) %> · Màu: <%= h(item.getColor()) %></p>
                <strong><%= money(item.getProduct().getDonGia()) %></strong>
              </div>
              <form class="cart-row-actions" method="post" action="<%= ctx %>/cart">
                <input type="hidden" name="action" value="update" />
                <input type="hidden" name="key" value="<%= h(item.getKey()) %>" />
                <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1" max="<%= item.getProduct().getSoLuongTon() %>" />
                <button type="submit">Cập nhật</button>
              </form>
              <div class="cart-line-total"><%= money(item.getThanhTien()) %></div>
              <form method="post" action="<%= ctx %>/cart">
                <input type="hidden" name="action" value="remove" />
                <input type="hidden" name="key" value="<%= h(item.getKey()) %>" />
                <button class="remove-btn" type="submit">Xóa</button>
              </form>
            </article>
          <% } %>
        </div>
        <aside class="cart-summary">
          <h2>Tóm tắt đơn hàng</h2>
          <div><span>Tạm tính</span><strong><%= money(CartStore.getSubtotal(session)) %></strong></div>
          <div><span>Phí vận chuyển</span><strong>Miễn phí</strong></div>
          <div class="grand-total"><span>Tổng tiền</span><strong><%= money(CartStore.getSubtotal(session)) %></strong></div>
          <a class="solid-dark-btn wide" href="<%= ctx %>/checkout">Đặt hàng</a>
          <form method="post" action="<%= ctx %>/cart">
            <input type="hidden" name="action" value="clear" />
            <button class="ghost-wide" type="submit">Xóa toàn bộ giỏ</button>
          </form>
        </aside>
      </section>
    <% } %>
  </main>
  <script src="<%= ctx %>/js/main.js"></script>
</body>
</html>
