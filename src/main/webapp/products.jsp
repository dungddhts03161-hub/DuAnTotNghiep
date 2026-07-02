<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%@ page import="model.Product" %>
<%@ page import="model.Category" %>
<%@ page import="store.CartStore" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%!
  private String h(String value) {
    if (value == null) return "";
    return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
  }
  private String money(java.math.BigDecimal value) {
    return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(value);
  }
%>
<%
  String ctx = request.getContextPath();
  User authUser = (User) session.getAttribute("authUser");
  Collection<Product> products = (Collection<Product>) request.getAttribute("products");
  List<Category> categories = (List<Category>) request.getAttribute("categories");
  String keyword = (String) request.getAttribute("keyword");
  Integer selectedCategory = (Integer) request.getAttribute("categoryId");
  String dbNotice = (String) request.getAttribute("dbNotice");
  if (products == null) products = java.util.Collections.emptyList();
  if (categories == null) categories = java.util.Collections.emptyList();
  if (keyword == null) keyword = "";
  if (selectedCategory == null) selectedCategory = 0;
  int cartCount = CartStore.getTotalQuantity(session);
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Sản phẩm | Celine Closet</title>
  <link rel="stylesheet" href="<%= ctx %>/css/style.css" />
</head>
<body>
  <div class="top-line">Miễn phí vận chuyển mọi đơn hàng</div>
  <header class="main-header shop-header">
    <a class="logo" href="<%= ctx %>/index.jsp" aria-label="Celine Closet">
      <span class="logo-main">Celine</span><span class="logo-sub">Closet</span>
    </a>
    <button class="menu-toggle" aria-label="Mở menu" aria-expanded="false"><span></span><span></span><span></span></button>
    <nav class="nav" aria-label="Điều hướng chính">
      <a href="<%= ctx %>/index.jsp#about">Giới thiệu</a>
      <a href="<%= ctx %>/products">Shop Online</a>
      <a href="<%= ctx %>/index.jsp#blog">Blog</a>
      <a href="<%= ctx %>/index.jsp#lookbook">Lookbook</a>
      <a href="<%= ctx %>/cart">Giỏ hàng (<%= cartCount %>)</a>
      <% if (authUser == null) { %>
        <a class="account-link" href="<%= ctx %>/auth.jsp">Tài khoản</a>
      <% } else { %>
        <span class="hello">Hi, <%= h(authUser.getFullName()) %></span>
        <a class="account-link" href="<%= ctx %>/logout">Đăng xuất</a>
      <% } %>
    </nav>
  </header>

  <main class="shop-page">
    <section class="shop-hero">
      <span class="tiny-title">Shop Online</span>
      <h1>Xem và tìm kiếm sản phẩm</h1>
      <p>Tìm theo tên sản phẩm, mã sản phẩm hoặc lọc theo danh mục đúng chức năng UC03.</p>
    </section>

    <% if (dbNotice != null) { %>
      <section class="db-notice"><%= h(dbNotice) %></section>
    <% } %>

    <section class="shop-tools">
      <form class="search-panel" method="get" action="<%= ctx %>/products">
        <label>
          Từ khóa
          <input type="text" name="q" value="<%= h(keyword) %>" placeholder="Ví dụ: đầm, áo, mã 1..." />
        </label>
        <label>
          Danh mục
          <select name="category">
            <option value="0">Tất cả danh mục</option>
            <% for (Category category : categories) { %>
              <option value="<%= category.getMaDM() %>" <%= selectedCategory == category.getMaDM() ? "selected" : "" %>><%= h(category.getTenDM()) %></option>
            <% } %>
          </select>
        </label>
        <button type="submit" class="submit-btn compact-btn">Tìm kiếm</button>
        <a href="<%= ctx %>/products" class="ghost-btn dark compact-link">Xóa lọc</a>
      </form>

      <div class="category-pills">
        <a class="<%= selectedCategory == 0 ? "active" : "" %>" href="<%= ctx %>/products?q=<%= java.net.URLEncoder.encode(keyword, java.nio.charset.StandardCharsets.UTF_8) %>">Tất cả</a>
        <% for (Category category : categories) { %>
          <a class="<%= selectedCategory == category.getMaDM() ? "active" : "" %>" href="<%= ctx %>/products?category=<%= category.getMaDM() %>&q=<%= java.net.URLEncoder.encode(keyword, java.nio.charset.StandardCharsets.UTF_8) %>"><%= h(category.getTenDM()) %></a>
        <% } %>
      </div>
    </section>

    <% if (products.isEmpty()) { %>
      <section class="empty-state">
        <h2>Không tìm thấy sản phẩm phù hợp</h2>
        <p>Bạn thử đổi từ khóa hoặc chọn lại danh mục khác nhé.</p>
        <a class="solid-dark-btn" href="<%= ctx %>/products">Xem toàn bộ sản phẩm</a>
      </section>
    <% } else { %>
      <section class="product-grid catalog-grid">
        <% for (Product product : products) { %>
          <article class="product-card catalog-card">
            <a class="product-img" href="<%= ctx %>/product?id=<%= product.getMaSP() %>">
              <img src="<%= ctx %>/<%= h(product.getHinhAnh()) %>" alt="<%= h(product.getTenSP()) %>" />
            </a>
            <div class="product-meta-line">
              <span>MSP<%= product.getMaSP() %></span>
              <span><%= h(product.getDanhMuc().getTenDM()) %></span>
            </div>
            <h3><a href="<%= ctx %>/product?id=<%= product.getMaSP() %>"><%= h(product.getTenSP()) %></a></h3>
            <p class="catalog-price"><%= money(product.getDonGia()) %></p>
            <p class="stock-line">Còn <%= product.getSoLuongTon() %> sản phẩm</p>
            <form class="quick-cart" method="post" action="<%= ctx %>/cart">
              <input type="hidden" name="action" value="add" />
              <input type="hidden" name="productId" value="<%= product.getMaSP() %>" />
              <select name="size" aria-label="Chọn size">
                <% for (String size : product.getSizes()) { %>
                  <option value="<%= h(size) %>"><%= h(size) %></option>
                <% } %>
              </select>
              <select name="color" aria-label="Chọn màu">
                <% for (String color : product.getColors()) { %>
                  <option value="<%= h(color) %>"><%= h(color) %></option>
                <% } %>
              </select>
              <input type="number" name="quantity" value="1" min="1" max="<%= product.getSoLuongTon() %>" aria-label="Số lượng" />
              <button type="submit">Thêm giỏ</button>
            </form>
          </article>
        <% } %>
      </section>
    <% } %>
  </main>

  <script src="<%= ctx %>/js/main.js"></script>
</body>
</html>
