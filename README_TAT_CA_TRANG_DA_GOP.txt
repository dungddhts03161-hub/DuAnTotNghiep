CELINE CLOSET - BẢN ĐÃ GỘP ĐỦ TRANG

Các trang không còn là project riêng. Tất cả nằm chung trong một project Maven/JPA:

- Trang chủ: src/main/webapp/index.jsp
- Đăng nhập / Đăng ký: src/main/webapp/auth.jsp
- Blog có phân trang: src/main/webapp/blog.jsp + BlogServlet.java
- Lookbook có phân trang: src/main/webapp/lookbook.jsp + LookbookServlet.java
- Sản phẩm / giỏ hàng / đặt hàng: các servlet products, cart, checkout

Lưu ý: Web chuẩn thường tách mỗi màn hình thành một file JSP/Servlet riêng để dễ sửa, nhưng vẫn là cùng một website và cùng một project.

Menu trên các trang đã được nối chung:
Trang chủ -> Blog -> Lookbook -> Tài khoản -> Shop Online -> Giỏ hàng.

Import Eclipse:
File > Import > Maven > Existing Maven Projects > chọn thư mục Celine Closet.
Sau đó Maven > Update Project > Force Update, rồi Project > Clean.
