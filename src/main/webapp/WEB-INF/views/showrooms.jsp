<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Hệ thống showroom | Celine Closet" scope="request" />
<%@ include file="common/header.jsp" %>
<main class="brand-subpage showroom-redesign-page">
    <nav class="subpage-breadcrumb"><a href="${ctx}/home">Trang chủ</a><span>/</span><a href="${ctx}/about">Giới thiệu</a><span>/</span><strong>Hệ thống showroom</strong></nav>
    <header class="showroom-page-heading"><p class="subpage-kicker">Visit C&amp;C</p><h1>HỆ THỐNG SHOWROOM</h1><p>Chọn showroom gần bạn để thử sản phẩm, nhận tư vấn size và trải nghiệm chất liệu trực tiếp.</p></header>

    <section class="showroom-visual-layout">
        <figure class="showroom-main-photo"><img src="${ctx}/assets/images/fashion/story.jpg" alt="Không gian showroom C&C"><figcaption>Không gian thử đồ nhẹ nhàng, riêng tư và được hỗ trợ bởi đội ngũ tư vấn C&amp;C.</figcaption></figure>
        <div class="showroom-map-panel">
            <div id="showroomMap" class="showroom-map" data-showroom-map></div>
            <p><i class="fa-solid fa-location-dot"></i> Chọn một showroom bên dưới để xem vị trí trên bản đồ.</p>
        </div>
    </section>

    <section class="showroom-location-list" data-showroom-list>
        <button class="showroom-location-card active" type="button" data-showroom-lat="10.77355" data-showroom-lng="106.66742" data-showroom-name="C&C Tô Hiến Thành">
            <span>01</span><div><h2>TP. HỒ CHÍ MINH · TÔ HIẾN THÀNH</h2><p>268 Tô Hiến Thành, Phường 15, Quận 10, TP. Hồ Chí Minh</p><small>09:00 – 21:00 · 028 3862 5791</small></div><b>XEM BẢN ĐỒ →</b>
        </button>
        <button class="showroom-location-card" type="button" data-showroom-lat="10.79875" data-showroom-lng="106.67841" data-showroom-name="C&C Lê Văn Sỹ">
            <span>02</span><div><h2>TP. HỒ CHÍ MINH · LÊ VĂN SỸ</h2><p>40 Lê Văn Sỹ, Phường 11, Quận Phú Nhuận, TP. Hồ Chí Minh</p><small>09:00 – 21:00 · 028 6253 7393</small></div><b>XEM BẢN ĐỒ →</b>
        </button>
        <button class="showroom-location-card" type="button" data-showroom-lat="10.80208" data-showroom-lng="106.68391" data-showroom-name="C&C Phan Đình Phùng">
            <span>03</span><div><h2>TP. HỒ CHÍ MINH · PHAN ĐÌNH PHÙNG</h2><p>248B Phan Đình Phùng, Phường 1, Quận Phú Nhuận, TP. Hồ Chí Minh</p><small>09:00 – 21:00 · 028 6253 8787</small></div><b>XEM BẢN ĐỒ →</b>
        </button>
        <button class="showroom-location-card" type="button" data-showroom-lat="10.92890" data-showroom-lng="108.10210" data-showroom-name="C&C Phan Thiết">
            <span>04</span><div><h2>PHAN THIẾT · TRUNG TÂM</h2><p>${shopAddress}</p><small>09:00 – 21:00 · ${shopHotline}</small></div><b>XEM BẢN ĐỒ →</b>
        </button>
    </section>

    <section class="showroom-service-strip"><article><i class="fa-solid fa-ruler"></i><h3>Tư vấn size</h3><p>Đo và chọn phom phù hợp trực tiếp.</p></article><article><i class="fa-solid fa-shirt"></i><h3>Phối đồ cá nhân</h3><p>Gợi ý theo dịp sử dụng và phong cách.</p></article><article><i class="fa-solid fa-box"></i><h3>Nhận hàng tại cửa hàng</h3><p>Đặt online và nhận sau khi được xác nhận.</p></article></section>
    <nav class="subpage-next-links"><a href="${ctx}/brand-values"><span>←</span> Giá trị thương hiệu</a><a href="${ctx}/contact">Liên hệ <span>→</span></a></nav>
</main>
<%@ include file="common/footer.jsp" %>
