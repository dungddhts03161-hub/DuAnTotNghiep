BẢN ĐÃ GỘP BLOG + LOOKBOOK VÀO PROJECT CELINE CLOSET JPA

Đây là bản full project, không phải file patch rời.
Các phần Blog và Lookbook đã nằm chung trong project Celine Closet đang dùng JPA/Hibernate.

Các link đã gộp vào menu:
- Blog:     http://localhost:8080/celine-closet/blog
- Lookbook: http://localhost:8080/celine-closet/lookbook

Các file chính đã thêm/gộp:
- src/main/java/controller/BlogServlet.java
- src/main/java/controller/LookbookServlet.java
- src/main/java/model/PostItem.java
- src/main/webapp/blog.jsp
- src/main/webapp/lookbook.jsp
- src/main/webapp/css/style.css  (đã thêm CSS cho trang listing + phân trang)
- src/main/webapp/index.jsp       (menu đã trỏ sang Blog và Lookbook)

Cách đổi số lượng bài hiện trên mỗi trang:
1. Mở BlogServlet.java hoặc LookbookServlet.java
2. Sửa dòng:
   private static final int PAGE_SIZE = 6;

Ví dụ:
- PAGE_SIZE = 3;  -> mỗi trang hiện 3 bài
- PAGE_SIZE = 6;  -> mỗi trang hiện 6 bài
- PAGE_SIZE = 9;  -> mỗi trang hiện 9 bài

Cách thêm bài Blog:
- Mở BlogServlet.java
- Tìm hàm createBlogPosts()
- Copy thêm dòng dạng:
  list.add(new PostItem("blog-13", "Tiêu đề", "Tóm tắt", "Danh mục", "link ảnh", "#"));

Cách thêm Lookbook:
- Mở LookbookServlet.java
- Tìm hàm createLookbooks()
- Copy thêm dòng dạng:
  list.add(new PostItem("look-13", "Tên lookbook", "Mô tả", "Danh mục", "link ảnh", "#"));

Lưu ý:
- Project vẫn dùng JPA, không dùng DBConnection.
- Cấu hình database nằm ở:
  src/main/resources/META-INF/persistence.xml
