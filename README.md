# Racoon Market
RacoonMarket là dự án hổ trợ kinh doanh tạp hóa mô hình nhỏ đến lớn. hổ trợ quản lí tất cả các hạng mục cần có trong kinh doanh tạp hóa.
- Để quản lý được một cửa hàng tạp hóa, dự án sẽ chia đối tượng sử dụng thành hai đối tượng chính đó là Admin-Chủ cửa hàng và User-Nhân viên bán hàng. Cả hai đều cần tài khoản để đăng nhập vào hệ thống. Admin sẽ có toàn quyền quản lý tất cả các chức năng có trong phạm vi dự án, User sẽ được sử dụng những chức năng dựa theo phân quyền của Admin. Các chức năng lớn và quan trọng của dự án này đó là: Quản lý Nhân Viên, Quản Lý và Phân Quyền Tài Khoản, Quản Lý Khách Hàng, Quản Lý Nhà Cung Cấp, Quản Lý Sản Phẩm, Quản Lý Kho (Nhập kho và xuất kho), Bán Hàng, Báo Cáo. Mọi hoạt động ảnh hưởng đến kho và báo cáo đều sẽ được lưu lại lịch sửa chứa đầy đủ thông tin về thời gian, nội dung, người thực hiện, đối tượng hướng đến, giá trị ảnh hưởng,… Ngoài ra Dự án lần này còn chú tâm hơn đến khía cạnh UI-UX nhằm nâng cao trải nghiệm của người dùng trong hoạt động kinh doanh thực tế, thúc đẩy tính ứng dụng của Dự án.
- Dự án được sử dụng công nghệ chính là JAVA Swing và MySQL
- Tập trung hoàn chỉnh các chức năng chính, giúp người dùng dễ dàng sử dụng kể cả chưa biết nhiều về công nghệ thông tin nhờ vào UX/UI tối ưu.
Các hình ảnh mô tả về dự án:
 - Trang chủ:
 ![image](https://user-images.githubusercontent.com/95233436/223147354-d5c30b2c-d8d6-4ab4-a726-3d6dd3162cb2.png)
 - Chức năng tạo hóa đơn bán hàng, có tích hợp máy quét mã vạch:
 ![image](https://user-images.githubusercontent.com/95233436/223147542-c6bef04f-23a2-4385-9a0f-fc9124aa7a51.png)
 - Xem danh sách các hóa đơn đã bán:
 ![image](https://user-images.githubusercontent.com/95233436/223147691-9a675af0-c622-4ee0-aef6-1e1dfd4d1ffe.png)
 - Báo cáo theo nhiều hạng mục:
 ![image](https://user-images.githubusercontent.com/95233436/223148002-43213d5d-80cf-4c49-be69-1ec4812846f1.png)
 - Quản lý các sản phẩm dể dàng:
 ![image](https://user-images.githubusercontent.com/95233436/223148357-5a660f85-bf66-4eb3-b580-17f631d14e6e.png)
 - Hổ trợ nhiều chủ đề màu sắc:
 ![image](https://user-images.githubusercontent.com/95233436/223148626-6d579fea-d702-4faa-87c3-6ee4a4d4a881.png)

Đây là dự án đầu tiên của tôi khi đang học tại trường cao đẳng FPT Polytechnic Tây Nguyên, dự án thành công và giúp tôi đạt được danh hiệu Ong Vàng học kỳ Fall 2022.

link review youtube: https://youtu.be/ewHDAaDPxHw

# Hướng dẫn cài đặt:
Hướng dẫn cài đặt phần mềm Racoon Market 
Tải phần mềm Xampp.
Tạo 1 database mới đặt tên là “racoonmarket” với “UTF8_general_ci”.

Nhập cơ sở dữ liệu có sẵn từ file “racoonmarket.sql” vào database vừa tạo.

Chạy file “RacoonMarket_setup.exe”.

	Username:    admin
	
	Password:    123
