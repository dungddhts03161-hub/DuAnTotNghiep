<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.PostItem" %>
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
  List<PostItem> lookbooks = (List<PostItem>) request.getAttribute("lookbooks");
  int currentPage = (Integer) request.getAttribute("currentPage");
  int totalPages = (Integer) request.getAttribute("totalPages");
  int totalItems = (Integer) request.getAttribute("totalItems");
  int pageSize = (Integer) request.getAttribute("pageSize");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Lookbook | Celine Closet</title>
  <link rel="stylesheet" href="<%= ctx %>/css/style.css" />
</head>
<body>
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
      <a class="active-link" href="<%= ctx %>/lookbook">Lookbook</a>
      <a href="<%= ctx %>/index.jsp#showroom">Hệ thống showroom</a>
      <a href="<%= ctx %>/index.jsp#guide">Hướng dẫn mua hàng</a>
      <a href="<%= ctx %>/products" class="nav-icon" aria-label="Tìm kiếm">
        <svg viewBox="0 0 24 24" aria-hidden="true"><path d="M10.8 18.1a7.3 7.3 0 1 1 0-14.6 7.3 7.3 0 0 1 0 14.6Zm0-1.3a6 6 0 1 0 0-12 6 6 0 0 0 0 12Zm5.2.1 4.1 4.1-.9.9-4.1-4.1.9-.9Z"/></svg>
      </a>
      <a href="<%= ctx %>/cart">Giỏ hàng</a>
      <% if (authUser == null) { %>
        <a class="account-link" href="<%= ctx %>/auth.jsp">Tài khoản</a>
      <% } else { %>
        <span class="hello">Hi, <%= h(authUser.getFullName()) %></span>
        <a class="account-link" href="<%= ctx %>/logout">Đăng xuất</a>
      <% } %>
    </nav>
  </header>

  <main class="content-page lookbook-page">
    <section class="listing-hero lookbook-listing-hero">
      <span class="tiny-title">Celine Lookbook</span>
      <h1>Lookbook</h1>
      <p>Mỗi trang hiển thị tối đa <%= pageSize %> bộ ảnh. Khi có nhiều lookbook, hệ thống sẽ tự chia sang trang kế tiếp.</p>
    </section>

    <section class="listing-wrap">
      <div class="listing-info">
        <span>Trang <%= currentPage %>/<%= totalPages %></span>
        <span><%= totalItems %> lookbook</span>
      </div>

      <div class="post-grid lookbook-grid-page">
        <% for (PostItem item : lookbooks) { %>
          <article class="post-card lookbook-card-page">
            <a class="post-image tall" href="<%= h(item.getDetailUrl()) %>">
              <img src="<%= h(item.getImageUrl()) %>" alt="<%= h(item.getTitle()) %>" />
            </a>
            <div class="post-body">
              <span class="post-category"><%= h(item.getCategory()) %></span>
              <h2><a href="<%= h(item.getDetailUrl()) %>"><%= h(item.getTitle()) %></a></h2>
              <p><%= h(item.getSummary()) %></p>
              <a class="read-more" href="<%= h(item.getDetailUrl()) %>">Xem thêm</a>
            </div>
          </article>
        <% } %>
      </div>

      <% if (totalPages > 1) { %>
        <nav class="pagination" aria-label="Phân trang lookbook">
          <a class="page-arrow <%= currentPage == 1 ? "disabled" : "" %>" href="<%= currentPage == 1 ? "#" : ctx + "/lookbook?page=" + (currentPage - 1) %>">«</a>
          <% for (int i = 1; i <= totalPages; i++) { %>
            <a class="page-number <%= i == currentPage ? "active" : "" %>" href="<%= ctx %>/lookbook?page=<%= i %>"><%= i %></a>
          <% } %>
          <a class="page-arrow <%= currentPage == totalPages ? "disabled" : "" %>" href="<%= currentPage == totalPages ? "#" : ctx + "/lookbook?page=" + (currentPage + 1) %>">»</a>
        </nav>
      <% } %>
    </section>
  </main>

  <footer class="footer">
    <div>
      <a class="footer-logo" href="<%= ctx %>/index.jsp">Celine Closet</a>
      <p>Fashion boutique interface prototype.</p>
    </div>
    <div>
      <a href="<%= ctx %>/blog">Blog</a>
      <a href="<%= ctx %>/lookbook">Lookbook</a>
      <a href="<%= ctx %>/products">Shop Online</a>
    </div>
  </footer>

  <script src="<%= ctx %>/js/main.js"></script>
</body>
</html>
