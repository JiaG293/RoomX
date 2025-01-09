
# 1. Masterdata
## Phòng ban:

id
Tên phòng ban
Trưởng phòng(Nhân viên)
Tầng
Toà nhà

## Phòng:

- Tên phòng
- Mã phòng
- Tòa nhà
- Tầng
- Trạng thái (Pending, Approved, Available, Repairing, Denied, Unavailable)
- Mô tả

## Nhân viên:

- Mã nhân viên
- Tên nhân viên
- Email
- Vai trò (role)
- Phòng ban

## Công ty:

- Mã công ty
- Tên công ty
- Địa chỉ

## Cơ sở vật chất:

- id
- Tên thiết bị
- Loại thiết bị
- Vị trí sử dụng
- Trạng thái (Sử dụng, Bảo trì, Hỏng)
- Thời hạn bảo hành
- Chi phí 1 giờ

## Người dùng:

- id 
- Tên người dùng
- Email
- Vai trò
- Quyền (permission)

## Dịch vụ

- id
- Tên dịch vụ
- Chi phí

# Chi nhánh:

- id
- Tên chi nhánh
- Địa chỉ

# 2. Transaction
a) Đặt phòng:

	- Mã phòng
	- Mã nhân viên đặt
	- Ngày giờ
	- Thời lượng
	- Thành viên [ ]
	- Trạng thái: Pending, Approved, Available, Repairing, Denied, Unavailable
	- Mô tả
	- Dịch vụ []
	- Cơ sở vật chất []

b) Mời cuộc họp:

	- Mã phòng
	- Thành viên [ ]
	- Ngày giờ
	- Thời lượng

c) Duyệt đặt/huỷ phòng:

	- Mã phòng
	- Trạng thái cập nhật

d) Huỷ phòng
	
	-   Mã phòng
	-   Mã nhân viên thực hiện hủy
	-   Lý do hủy
	-   Ngày giờ thực hiện hủy
	-   Trạng thái cập nhật: Canceled
	
e) Yêu cầu dịch vụ
	
	-   Mã phòng
	-   Mã nhân viên thực hiện yêu cầu
	-   Dịch vụ yêu cầu [ ]
	-	Ngày giờ yêu cầu
	-   Trạng thái: Pending, In Progress, Completed, Denied
	-   Ghi chú 

f) Nhận thông báo cuộc họp

	-   Mã cuộc họp
	-   Mã nhân viên nhận thông báo
	-   Nội dung thông báo
	-   Loại thông báo (Tạo mới, Cập nhật, Hủy, Nhắc nhở)
	-   Ngày giờ gửi thông báo
	-   Trạng thái thông báo (Đã đọc, Chưa đọc)
	-   Kênh thông báo (Email, Ứng dụng, SMS)

Ghi nhận lịch sử thay đổi trạng thái hoặc các thao tác trên phòng, thiết bị, hoặc tài khoản.
Ghi chú trạng thái đặt phòng:
Pending: Chưa được duyệt.
Approved: Được phê duyệt.
Available: Phòng sẵn sàng.
Repairing: Đang sửa chữa.
Denied: Từ chối yêu cầu.
Unavailable: Không khả dụng (gặp sự cố hoặc đã có lịch).

# 3. Chức năng chính
## Nhân viên (mobile app):

- Đặt lịch họp.
- Xem lịch phòng họp.
- Quản lý tài khoản cá nhân.
- Cập nhật hồ sơ
- Mời cuộc họp
- Chấp nhận cuộc họp
- Nhận thông báo về cuộc họp
- Xem các thống kê cá nhân

# Admin công ty:
- Quản lý tài nguyên
	- Thêm dịch vụ mới
	- Cập nhật thông tin dịch vụ
	- Tra cứu các dịch vụ
	- Thêm cơ sở thiết bị
	- Cập nhật thông tin cơ sở thiết bị
	- Tra cứu cơ sở thiết bị
	- Cập nhật trạng thái cơ sở thiết bị
- Quản lý phòng
	- Tạo phòng mới
	- Cập nhật thông tin phòng
	- Bảo trì phòng(liên quan những cái đến trạng thái)
- Quản lý cuộc họp
	- Phê duyệt phòng
	- Yêu cầu huỷ phòng
- Quản lý tài khoản nhân viên
	- Vô hiệu hoá tài khoản
	- Thêm tài khoản
- Gửi yêu cầu cập nhật thông tin công ty
- Quản lý chi nhánh (CRUD)

# CMS ADMIN HỆ THỐNG

- Quản lý toàn bộ tài khoản
- Thêm, vô hiệu hoá người dùng.
- Gán vai trò cho từng người dùng.
- Theo dõi và quản lý audit logs.
- Cập nhật thông tin công ty, chi nhánh.
- Quản lý phòng họp, thiết bị, và dịch vụ đi kèm.

