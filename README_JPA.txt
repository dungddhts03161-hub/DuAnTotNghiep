CELINE CLOSET - BẢN JPA/HIBERNATE

1. Bản này đã bỏ cách kết nối bằng DBConnection/JDBC trong DAO.
2. DAO hiện dùng JPA qua file:
   src/main/java/util/JPAUtil.java

3. File cấu hình database:
   src/main/resources/META-INF/persistence.xml

4. Chỉnh thông tin SQL Server tại persistence.xml:
   jakarta.persistence.jdbc.url
   jakarta.persistence.jdbc.user
   jakarta.persistence.jdbc.password

Ví dụ:
   jdbc:sqlserver://localhost:1433;databaseName=QL_CuaHangQuanAoOnline;encrypt=true;trustServerCertificate=true
   user: sa
   password: 123456

5. Chạy database:
   Mở SQL Server Management Studio
   Chạy file Database_JPA.sql hoặc Database.sql

6. Import Eclipse:
   File > Import > Maven > Existing Maven Projects
   Chọn thư mục Celine Closet
   Finish
   Chuột phải project > Maven > Update Project > Force Update
   Project > Clean
   Run As > Run on Server

7. Tài khoản test:
   Email: demo@celinecloset.vn
   Mật khẩu: 123456

8. Các package chính:
   controller: Servlet
   dao: DAO dùng JPA
   model: Entity JPA
   util: JPAUtil
   store: dữ liệu demo fallback khi chưa kết nối database

9. Lưu ý:
   Bảng DON_HANG trong Database_JPA.sql dùng ngayDat DATETIME2 để khớp LocalDateTime.
   Các trường size, màu, hình ảnh đang để tạm bằng @Transient vì ERD hiện chưa có bảng/column riêng cho size, màu và ảnh.

==============================
PHÂN TRANG BLOG / LOOKBOOK
==============================
Đã thêm 2 trang mới:
- /blog
- /lookbook

Mỗi trang đang hiển thị tối đa 6 bài/lookbook. Khi dữ liệu nhiều hơn 6 mục, giao diện sẽ tự tách sang trang 2, trang 3... bằng tham số:
- /blog?page=2
- /lookbook?page=2

Muốn đổi số lượng bài trên mỗi trang:
1. Mở src/main/java/controller/BlogServlet.java
2. Sửa dòng: private static final int PAGE_SIZE = 6;
3. Mở src/main/java/controller/LookbookServlet.java
4. Sửa dòng: private static final int PAGE_SIZE = 6;

Muốn thêm bài blog:
- Mở BlogServlet.java
- Thêm một dòng list.add(new PostItem(...)) trong hàm createBlogPosts().

Muốn thêm lookbook:
- Mở LookbookServlet.java
- Thêm một dòng list.add(new PostItem(...)) trong hàm createLookbooks().
