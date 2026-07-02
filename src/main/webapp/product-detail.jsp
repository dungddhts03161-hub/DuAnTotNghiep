<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%@ page import="model.Product" %>
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
  Product product = (Product) request.getAttribute("product");
  int cartCount = CartStore.getTotalQuantity(session);
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title><%= h(product.getTenSP()) %> | Celine Closet</title>
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
      <% if (authUser == null) { %>
        <a href="<%= ctx %>/auth.jsp">Tài khoản</a>
      <% } else { %>
        <span class="hello">Hi, <%= h(authUser.getFullName()) %></span>
        <a href="<%= ctx %>/logout">Đăng xuất</a>
      <% } %>
    </nav>
  </header>

  <main class="detail-page">
    <a class="back-to-shop" href="<%= ctx %>/products">← Quay lại danh sách sản phẩm</a>
    <section class="detail-layout">
      <div class="detail-image">
        <img src="<%= ctx %>/<%= h(product.getHinhAnh()) %>" alt="<%= h(product.getTenSP()) %>" />
      </div>
      <div class="detail-info">
        <span class="tiny-title"><%= h(product.getDanhMuc().getTenDM()) %> / MSP<%= product.getMaSP() %></span>
        <h1><%= h(product.getTenSP()) %></h1>
        <p class="detail-price"><%= money(product.getDonGia()) %></p>
        <p class="detail-desc"><%= h(product.getMoTa()) %></p>
        <div class="detail-table">
          <div><span>Chất liệu</span><strong>Vải cao cấp, mềm, dễ mặc</strong></div>
          <div><span>Tồn kho</span><strong><%= product.getSoLuongTon() %> sản phẩm</strong></div>
          <div><span>Trạng thái</span><strong><%= product.isAvailable() ? "Còn hàng" : "Hết hàng" %></strong></div>
        </div>

        <form class="detail-cart-form" method="post" action="<%= ctx %>/cart">
          <input type="hidden" name="action" value="add" />
          <input type="hidden" name="productId" value="<%= product.getMaSP() %>" />
          <label>Size
            <select name="size">
              <% for (String size : product.getSizes()) { %><option value="<%= h(size) %>"><%= h(size) %></option><% } %>
            </select>
          </label>
          <label>Màu sắc
            <select name="color">
              <% for (String color : product.getColors()) { %><option value="<%= h(color) %>"><%= h(color) %></option><% } %>
            </select>
          </label>
          <label>Số lượng
            <input type="number" name="quantity" value="1" min="1" max="<%= product.getSoLuongTon() %>" />
          </label>
          <button class="solid-dark-btn" type="submit">Thêm vào giỏ hàng</button>
        </form>
      </div>
    </section>
  </main>
  <script src="<%= ctx %>/js/main.js"></script>
</body>
</html>
