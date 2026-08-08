# Celine Closet Web

Website thương mại điện tử thời trang sử dụng Java 17, Jakarta Servlet 6.1,
JSP/JSTL, Hibernate/JPA, SQL Server, Maven WAR và Tomcat 11.

## Cách chạy

1. Chạy duy nhất file `database/CELINE_CLOSET_DATABASE.sql` trong SQL Server Management Studio.
2. Kiểm tra kết nối trong `src/main/resources/META-INF/persistence.xml`.
3. Điền các khóa cần dùng trong `src/main/resources/app.properties`.
4. Chạy `mvn clean package`.
5. Deploy `target/celine-closset-web.war` lên Tomcat 11.

> File database tạo lại toàn bộ `CelineClossetDB`. Hãy sao lưu dữ liệu thật trước khi chạy.

## Chat hỗ trợ

- Khách trò chuyện với AI OpenRouter; khi cần người thật, hệ thống phân công STAFF có ít cuộc chat chưa đóng nhất.
- STAFF nhận badge, thông báo nổi, âm báo và số tin trên tiêu đề tab khi có khách mới.
- Chat không có hoạt động trong 48 giờ được dọn tự động theo cấu hình trong `app.properties`.

## Giao hàng không thành công

- DELIVERY ghi minh chứng cho ba ngày giao khác nhau, mỗi ngày có số lần gọi và ảnh lịch sử cuộc gọi.
- Khi đủ ba ngày và ít nhất ba lần gọi, shipper gửi hồ sơ để ADMIN duyệt.
- ADMIN duyệt thì đơn chuyển sang `Đã hủy`, hàng và voucher được hoàn lại, khách nhận thông báo không giao được hàng.
- Đơn đã thanh toán được giữ thông tin thanh toán để STAFF/ADMIN đối soát hoàn tiền.

## Luồng trạng thái đơn

`Chờ xác nhận → Đã xác nhận → Đang chuẩn bị → Đang giao → Hoàn thành`

Giao diện chỉ hiện bước tiếp theo hợp lệ; backend cũng chặn cập nhật lùi hoặc bỏ qua bước.

## Thanh toán TPBank / SePay

- Mã chuyển khoản có dạng `DH00001`, `DH00045`.
- QR VietQR tự điền số tiền và nội dung chuyển khoản.
- SePay gọi `/webhook/sepay` để tự xác nhận tiền vào.
- Đơn chuyển khoản chưa nhận được tiền sau thời gian cấu hình sẽ được xử lý theo luồng thanh toán hiện tại của dự án.

## Bảo mật

Không đưa API key, mật khẩu SMTP, thông tin ngân hàng hoặc client secret thật lên GitHub công khai.

## Tài khoản demo

- ADMIN: `admin@celineclosset.vn` / `Admin@123`
- STAFF chính: `staff@celineclosset.vn` / `Staff@123`
- STAFF phụ: `staff0@celineclosset.vn` / `Staff@123`
- DELIVERY chính – Nhân viên giao hàng 04 – Lan: `staff2@celineclosset.vn` / `Delivery@123`
- DELIVERY phụ: `staff3@celineclosset.vn` / `Delivery@123`
- CUSTOMER: `customer@demo.vn` / `Customer@123`

## Trả hàng và hoàn tiền

- Nút trả hàng chỉ xuất hiện khi đơn đã hoàn thành và còn trong 7 ngày.
- Khách nhập lý do, ngân hàng, số tài khoản, tên chủ tài khoản và tối đa ba ảnh sản phẩm.
- Thông tin nhận tiền chỉ sửa được trong 2 ngày đầu.
- Luồng bốn mốc: shipper nhận hàng → hàng đến bưu điện → hoàn hàng thành công → trả tiền.
- Khi bắt đầu hoàn tiền, khách được thông báo thời gian xử lý khoảng 3–4 ngày làm việc.
- ADMIN chỉ đánh dấu “đã trả tiền” sau khi thực sự chuyển khoản vào tài khoản khách.
- Hàng chỉ được cộng lại kho một lần khi ADMIN xác nhận hoàn hàng thành công.
- Mỗi sản phẩm được đánh giá riêng theo từng đơn đã hoàn thành; một đơn không thể gửi đánh giá trùng cho cùng sản phẩm.

## Bản đồ giao hàng và GPS điện thoại

- Điểm cửa hàng được lấy từ `shop.address` trong `app.properties`:
  `118/90/1 Liên khu 5-6, Khu phố 33, Phường Bình Tân, TP.HCM`.
- Bản đồ hiển thị đồng thời cửa hàng, GPS gần nhất của shipper và địa chỉ khách.
- DELIVERY có nút gửi GPS cho toàn bộ đơn được phân công đang chuẩn bị/đang giao và nút bật tự động mỗi 10 phút.
- Gửi GPS không tự đổi trạng thái đơn; trạng thái chỉ thay đổi bằng nút chuyển bước hợp lệ.
- Trình duyệt điện thoại phải được cấp quyền vị trí và trang theo dõi cần được giữ mở; hệ điều hành có thể hạn chế JavaScript khi khóa màn hình.

## Lịch sử nhập kho

Trang kho có nút **Xem lịch sử nhập hàng** ở góc trên bên phải để cuộn thẳng đến danh sách biên lai, không cần kéo qua toàn bộ bảng sản phẩm.


## Cập nhật nghiệp vụ đơn hàng và kho (06/08/2026)

- Đơn COD tự chuyển thanh toán sang `PAID` khi giao thành công; dữ liệu COD hoàn thành cũ được đồng bộ một lần khi ứng dụng đọc đơn.
- Yêu cầu trả hàng chỉ tạo được khi đơn đã `Hoàn thành` và thanh toán `PAID`; khi đã có yêu cầu trả hàng thì trạng thái thanh toán bị khóa để hai quy trình không đè lên nhau.
- STAFF/DELIVERY chỉ được đi trạng thái về phía trước. ADMIN có công cụ sửa lùi kèm lý do khi nhân viên bấm nhầm.
- Khi chuyển sang `Đang giao`, hệ thống chọn ngẫu nhiên một DELIVERY trong nhóm ít đơn hoạt động nhất và gửi thông báo nổi.
- Lịch sử nhập kho nằm tại `/admin/inventory-history`, có lọc theo từ khóa, nhân viên và khoảng ngày; trang `/admin/inventory` chỉ giữ tồn kho và biểu mẫu nhập hàng.

## Tuyến đường giao hàng

- Backend ưu tiên OpenRouteService khi đã cấu hình `openrouteservice.apiKey`.
- Nếu ORS chưa có key hoặc tạm lỗi, hệ thống dùng `osrm.routeUrl` để lấy tuyến ô tô theo OpenStreetMap.
- Giao diện không còn nối thẳng hai tọa độ khi dịch vụ định tuyến lỗi; chỉ giữ marker và báo cần tải lại.
- Dữ liệu GPS mẫu cũ tạo bằng trung điểm toán học được tự sửa khi Tomcat khởi động. GPS thật do shipper gửi không bị ghi đè.
