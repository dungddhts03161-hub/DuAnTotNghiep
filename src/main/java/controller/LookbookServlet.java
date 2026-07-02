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

@WebServlet("/lookbook")
public class LookbookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 6;
    private static final List<PostItem> LOOKBOOKS = createLookbooks();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int totalItems = LOOKBOOKS.size();
        int totalPages = (int) Math.ceil(totalItems * 1.0 / PAGE_SIZE);
        int currentPage = parsePage(request.getParameter("page"), totalPages);

        int fromIndex = (currentPage - 1) * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, totalItems);
        List<PostItem> pageItems = LOOKBOOKS.subList(fromIndex, toIndex);

        request.setAttribute("lookbooks", pageItems);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalItems", totalItems);
        request.setAttribute("pageSize", PAGE_SIZE);
        request.getRequestDispatcher("/lookbook.jsp").forward(request, response);
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

    private static List<PostItem> createLookbooks() {
        List<PostItem> list = new ArrayList<>();
        list.add(new PostItem("look-01", "Serene Moments", "Bộ ảnh mang tinh thần dịu nhẹ, thanh lịch và nữ tính cho những ngày đặc biệt.", "Lookbook", "https://images.unsplash.com/photo-1509631179647-0177331693ae?auto=format&fit=crop&w=1100&q=85", "#"));
        list.add(new PostItem("look-02", "Garden Linen", "Gam màu xanh, kem và be tạo cảm giác trong trẻo cho mùa nắng.", "Collection", "https://images.unsplash.com/photo-1496747611176-843222e1e57c?auto=format&fit=crop&w=1100&q=85", "#"));
        list.add(new PostItem("look-03", "Soft Office", "Những thiết kế công sở mềm mại, ít chi tiết nhưng vẫn đủ nổi bật.", "Office", "https://images.unsplash.com/photo-1483985988355-763728e1935b?auto=format&fit=crop&w=1100&q=85", "#"));
        list.add(new PostItem("look-04", "Cafe Walk", "Cảm hứng dạo phố cuối tuần với váy midi, áo kiểu và túi nhỏ.", "Street style", "https://images.unsplash.com/photo-1529139574466-a303027c1d8b?auto=format&fit=crop&w=1100&q=85", "#"));
        list.add(new PostItem("look-05", "Evening Muse", "Một chút sang trọng cho buổi tối: đen, satin và phom dáng gọn.", "Evening", "https://images.unsplash.com/photo-1503342217505-b0a15ec3261c?auto=format&fit=crop&w=1100&q=85", "#"));
        list.add(new PostItem("look-06", "Minimal Brown", "Các bản phối nâu, trắng và đen theo phong cách tối giản hiện đại.", "Minimal", "https://images.unsplash.com/photo-1554412933-514a83d2f3c8?auto=format&fit=crop&w=1100&q=85", "#"));
        list.add(new PostItem("look-07", "White Closet", "Sự nhẹ nhàng từ váy trắng, áo sơ mi và các chất liệu cotton mịn.", "White mood", "https://images.unsplash.com/photo-1487412912498-0447578fcca8?auto=format&fit=crop&w=1100&q=85", "#"));
        list.add(new PostItem("look-08", "Daily Muse", "Outfit dễ mặc cho ngày thường nhưng vẫn có tinh thần boutique.", "Daily", "https://images.unsplash.com/photo-1539008835657-9e8e9680c956?auto=format&fit=crop&w=1100&q=85", "#"));
        list.add(new PostItem("look-09", "Paris Soft", "Cảm hứng từ phong cách thanh lịch kiểu Pháp với sắc be và đen.", "Elegant", "https://images.unsplash.com/photo-1516762689617-e1cffcef479d?auto=format&fit=crop&w=1100&q=85", "#"));
        list.add(new PostItem("look-10", "Modern Weekend", "Set đồ năng động hơn cho cuối tuần nhưng vẫn giữ sự nữ tính.", "Weekend", "https://images.unsplash.com/photo-1525507119028-ed4c629a60a3?auto=format&fit=crop&w=1100&q=85", "#"));
        list.add(new PostItem("look-11", "Black Dress Edit", "Những mẫu đầm đen cơ bản, tôn dáng và dùng được trong nhiều dịp.", "Black edit", "https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?auto=format&fit=crop&w=1100&q=85", "#"));
        list.add(new PostItem("look-12", "Light Summer", "Tinh thần mùa hè nhẹ nhàng với váy xòe, linen và phụ kiện nhỏ.", "Summer", "https://images.unsplash.com/photo-1485968579580-b6d095142e6e?auto=format&fit=crop&w=1100&q=85", "#"));
        return Collections.unmodifiableList(list);
    }
}
