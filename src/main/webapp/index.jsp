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
  User authUser = (User) session.getAttribute("authUser");
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Giới thiệu | Celine Closet</title>
  <link rel="stylesheet" href="<%= ctx %>/css/style.css" />
</head>
<body class="intro-body">
  <div class="top-line">Miễn phí vận chuyển mọi đơn hàng</div>

  <header class="main-header intro-header" id="top">
    <a class="logo" href="<%= ctx %>/index.jsp" aria-label="Celine Closet">
      <span class="logo-main">Celine</span>
      <span class="logo-sub">Closet</span>
    </a>

    <button class="menu-toggle" aria-label="Mở menu" aria-expanded="false">
      <span></span><span></span><span></span>
    </button>

    <nav class="nav" aria-label="Điều hướng chính">
      <a class="active-link" href="<%= ctx %>/index.jsp#about">Giới thiệu</a>
      <a href="<%= ctx %>/products">Shop Online</a>
      <a href="<%= ctx %>/blog">Blog</a>
      <a href="<%= ctx %>/lookbook">Lookbook</a>
      <a href="<%= ctx %>/index.jsp#showroom">Hệ thống showroom</a>
      <a href="<%= ctx %>/index.jsp#guide">Hướng dẫn mua hàng</a>
      <a href="<%= ctx %>/products" class="nav-icon" aria-label="Tìm kiếm sản phẩm">
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

  <main class="intro-page">
    <section class="intro-hero" id="about" aria-label="Giới thiệu Celine Closet">
      <div class="intro-hero-media">
        <img src="<%= ctx %>/assets/hero-1.svg" alt="Celine Closet collection" />
      </div>
      <div class="intro-hero-copy">
        <div class="breadcrumb">
          <a href="<%= ctx %>/index.jsp">Trang chủ</a>
          <span>/</span>
          <strong>Giới thiệu</strong>
        </div>
        <p class="tiny-title">Về chúng tôi</p>
        <h1>Giới thiệu | Celine Closet</h1>
        <p>Celine Closet là không gian thời trang nữ theo tinh thần thanh lịch, tối giản và dễ ứng dụng cho đi học, đi làm, dạo phố hay những buổi hẹn nhẹ nhàng.</p>
        <div class="intro-actions">
          <a class="solid-dark-btn" href="<%= ctx %>/products">Shop Online</a>
          <a class="ghost-btn dark" href="<%= ctx %>/lookbook">Xem Lookbook</a>
        </div>
      </div>
    </section>

    <section class="intro-container">
      <aside class="intro-sidebar" aria-label="Liên kết nhanh">
        <a href="<%= ctx %>/lookbook">
          <span>Look Book</span>
          <strong>Bộ sưu tập phối đồ</strong>
        </a>
        <a href="#showroom">
          <span>Showroom</span>
          <strong>Thông tin cửa hàng</strong>
        </a>
      </aside>

      <article class="intro-content">
        <section class="intro-block opening-block">
          <p class="block-kicker">Đôi nét</p>
          <div class="opening-grid">
            <figure>
              <img src="<%= ctx %>/assets/lookbook.svg" alt="Không gian thời trang Celine Closet" />
            </figure>
            <div>
              <p>Celine Closet được xây dựng như một boutique thời trang nữ hiện đại, tập trung vào những thiết kế có phom dáng gọn gàng, màu sắc trang nhã và chất liệu dễ mặc trong đời sống hằng ngày.</p>
              <p>Trang web được thiết kế để khách hàng có thể xem bộ sưu tập, tìm sản phẩm, thêm vào giỏ hàng, đặt hàng và theo dõi cảm hứng phối đồ một cách rõ ràng, sang trọng và thuận tiện.</p>
            </div>
          </div>
        </section>

        <section class="intro-block split-block">
          <h2>Về sản phẩm</h2>
          <p>Celine Closet hướng đến các sản phẩm nữ tính, thanh lịch và có tính ứng dụng cao: đầm công sở, áo kiểu, chân váy, quần, áo khoác nhẹ và các set phối sẵn. Mỗi sản phẩm được trình bày với hình ảnh rõ, thông tin size, giá và thao tác mua hàng nhanh.</p>
          <p>Các danh mục được sắp xếp theo phong cách mua sắm online quen thuộc, giúp người dùng dễ tìm kiếm sản phẩm theo nhu cầu và có thể chuyển thẳng sang giỏ hàng chỉ trong vài bước.</p>
        </section>

        <section class="intro-block split-block">
          <h2>Về khách hàng</h2>
          <p>Website ưu tiên trải nghiệm nhẹ nhàng, dễ thao tác và tạo cảm giác tin cậy cho khách hàng nữ yêu thích phong cách chỉn chu. Các mục như Lookbook, Blog, hướng dẫn mua hàng và chính sách hỗ trợ giúp người mua hiểu sản phẩm trước khi lựa chọn.</p>
          <p>Celine Closet mong muốn trở thành tủ đồ thân thiết của khách hàng: đẹp vừa đủ, tinh tế vừa đủ và luôn phù hợp với nhịp sống hiện đại.</p>
        </section>

        <section class="intro-block policy-block" id="guide">
          <h2>Hướng dẫn mua hàng</h2>
          <div class="policy-grid">
            <div>
              <span>01</span>
              <strong>Chọn sản phẩm</strong>
              <p>Vào Shop Online, lọc danh mục hoặc tìm theo tên/mã sản phẩm.</p>
            </div>
            <div>
              <span>02</span>
              <strong>Thêm vào giỏ</strong>
              <p>Chọn size, số lượng và kiểm tra lại đơn hàng trong giỏ.</p>
            </div>
            <div>
              <span>03</span>
              <strong>Đặt hàng</strong>
              <p>Điền thông tin nhận hàng, phương thức thanh toán và xác nhận.</p>
            </div>
          </div>
        </section>

        <section class="intro-block contact-block" id="showroom">
          <h2>Thông tin liên hệ</h2>
          <div class="contact-list">
            <p><strong>Hotline:</strong> 0909 000 999</p>
            <p><strong>Email:</strong> celinecloset@example.com</p>
            <p><strong>Showroom:</strong> 12 Nguyễn Trãi, Quận 1, TP.HCM</p>
            <p><strong>Thời gian mở cửa:</strong> 08:30 - 21:30, tất cả các ngày trong tuần</p>
          </div>
        </section>
      </article>
    </section>
  </main>

  <footer class="kk-style-footer">
    <div class="footer-column brand-column">
      <a class="footer-logo" href="#top">Celine Closet</a>
      <p>Fashion boutique dành cho những cô gái yêu vẻ đẹp thanh lịch, nhẹ nhàng và hiện đại.</p>
    </div>
    <div class="footer-column">
      <h3>Thông tin liên hệ</h3>
      <a href="<%= ctx %>/index.jsp#about">Về chúng tôi</a>
      <a href="<%= ctx %>/index.jsp#showroom">Hệ thống showroom</a>
      <a href="<%= ctx %>/lookbook">Lookbook</a>
      <a href="<%= ctx %>/blog">Blog</a>
    </div>
    <div class="footer-column">
      <h3>Hỗ trợ khách hàng</h3>
      <a href="<%= ctx %>/index.jsp#guide">Các bước mua hàng</a>
      <a href="<%= ctx %>/cart">Giỏ hàng</a>
      <a href="<%= ctx %>/products">Sản phẩm mới</a>
      <a href="<%= ctx %>/auth.jsp">Tài khoản</a>
    </div>
    <div class="footer-column newsletter-column">
      <h3>Đăng ký nhận ưu đãi</h3>
      <p>Nhận thông tin bộ sưu tập mới và các gợi ý phối đồ từ Celine Closet.</p>
      <form action="#" method="post" class="newsletter-form">
        <input type="email" name="email" placeholder="Email của bạn" aria-label="Email đăng ký bản tin" />
        <button type="submit">Đăng ký</button>
      </form>
    </div>
  </footer>

  <script src="<%= ctx %>/js/main.js"></script>
</body>
</html>
