package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PostItem;

@WebServlet("/blog")
public class BlogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 6;
    private static final List<PostItem> BLOG_POSTS = createBlogPosts();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int totalItems = BLOG_POSTS.size();
        int totalPages = (int) Math.ceil(totalItems * 1.0 / PAGE_SIZE);
        int currentPage = parsePage(request.getParameter("page"), totalPages);

        int fromIndex = (currentPage - 1) * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, totalItems);
        List<PostItem> pageItems = BLOG_POSTS.subList(fromIndex, toIndex);

        request.setAttribute("posts", pageItems);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalItems", totalItems);
        request.setAttribute("pageSize", PAGE_SIZE);
        request.getRequestDispatcher("/blog.jsp").forward(request, response);
    }

    private int parsePage(String value, int totalPages) {
        int page = 1;
        try {
            if (value != null && !value.trim().isEmpty()) {
                page = Integer.parseInt(value.trim());
            }
        } catch (NumberFormatException ignored) {
            page = 1;
        }

        if (page < 1) {
            return 1;
        }
        if (totalPages > 0 && page > totalPages) {
            return totalPages;
        }
        return page;
    }

    private static List<PostItem> createBlogPosts() {
        List<PostItem> list = new ArrayList<>();
        list.add(new PostItem("blog-01", "Chọn đầm đẹp sang trọng không khó - Đây là 5 gợi ý nàng không nên bỏ lỡ", "Khám phá phong cách thời trang phù hợp với mẫu đầm đẹp sang trọng: nữ tính, thanh lịch và dễ ứng dụng.", "Style guide", "https://images.unsplash.com/photo-1554412933-514a83d2f3c8?auto=format&fit=crop&w=1000&q=80", "#"));
        list.add(new PostItem("blog-02", "Bí quyết chọn váy dài chuẩn dáng: Nàng nào cũng mặc đẹp", "Gợi ý chọn váy dài theo từng dáng người, từ thấp bé đến cao gầy để nàng tự tin hơn.", "Tips", "https://images.unsplash.com/photo-1485968579580-b6d095142e6e?auto=format&fit=crop&w=1000&q=80", "#"));
        list.add(new PostItem("blog-03", "Tủ đồ công sở tối giản: 7 món nên có trong tuần mới", "Tạo tủ đồ gọn, sang và dễ phối với các gam màu trung tính cho môi trường học tập, làm việc.", "Office wear", "https://images.unsplash.com/photo-1487412912498-0447578fcca8?auto=format&fit=crop&w=1000&q=80", "#"));
        list.add(new PostItem("blog-04", "Màu kem, nâu và đen: bảng màu an toàn mà vẫn sang", "Cách phối ba gam màu cơ bản để tạo cảm giác thanh lịch, sạch và hiện đại.", "Color mood", "https://images.unsplash.com/photo-1529139574466-a303027c1d8b?auto=format&fit=crop&w=1000&q=80", "#"));
        list.add(new PostItem("blog-05", "Cách giữ váy áo luôn phẳng và thơm trong mùa mưa", "Một vài mẹo nhỏ khi phơi, ủi, xếp và bảo quản quần áo để trang phục luôn chỉn chu.", "Closet care", "https://images.unsplash.com/photo-1516762689617-e1cffcef479d?auto=format&fit=crop&w=1000&q=80", "#"));
        list.add(new PostItem("blog-06", "Đi cafe cuối tuần nên mặc gì để lên ảnh đẹp?", "Gợi ý các set váy, áo sơ mi, chân váy và phụ kiện nhẹ nhàng cho buổi hẹn cuối tuần.", "Inspiration", "https://images.unsplash.com/photo-1539008835657-9e8e9680c956?auto=format&fit=crop&w=1000&q=80", "#"));
        list.add(new PostItem("blog-07", "Chọn chân váy midi theo chiều cao", "Chiều dài váy, độ xòe và kiểu giày đi kèm có thể làm tổng thể cân đối hơn.", "Style guide", "https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?auto=format&fit=crop&w=1000&q=80", "#"));
        list.add(new PostItem("blog-08", "Áo sơ mi trắng: mặc sao để không bị nhàm chán", "Thay đổi phụ kiện, chất liệu và cách sơ vin để áo sơ mi trắng luôn mới.", "Mix & match", "https://images.unsplash.com/photo-1496747611176-843222e1e57c?auto=format&fit=crop&w=1000&q=80", "#"));
        list.add(new PostItem("blog-09", "Gợi ý outfit đi tiệc nhẹ cho nàng thích sự tinh tế", "Các lựa chọn đầm suông, đầm nhấn eo và set tối giản cho những buổi tiệc nhẹ.", "Event wear", "https://images.unsplash.com/photo-1503342217505-b0a15ec3261c?auto=format&fit=crop&w=1000&q=80", "#"));
        list.add(new PostItem("blog-10", "Cách chọn size online hạn chế đổi trả", "Đo số đo cơ thể, đọc bảng size và kiểm tra chất liệu giúp đặt hàng chính xác hơn.", "Shopping guide", "https://images.unsplash.com/photo-1525507119028-ed4c629a60a3?auto=format&fit=crop&w=1000&q=80", "#"));
        list.add(new PostItem("blog-11", "Phong cách nữ tính nhẹ nhàng cho ngày nắng", "Các thiết kế linen, cotton và pastel tạo cảm giác mềm mại, dễ mặc hằng ngày.", "Daily wear", "https://images.unsplash.com/photo-1509631179647-0177331693ae?auto=format&fit=crop&w=1000&q=80", "#"));
        list.add(new PostItem("blog-12", "Phối phụ kiện nhỏ để outfit nhìn có điểm nhấn", "Túi, thắt lưng, khuyên tai và giày có thể giúp tổng thể tinh tế hơn mà không quá cầu kỳ.", "Accessories", "https://images.unsplash.com/photo-1483985988355-763728e1935b?auto=format&fit=crop&w=1000&q=80", "#"));
        return Collections.unmodifiableList(list);
    }
}
