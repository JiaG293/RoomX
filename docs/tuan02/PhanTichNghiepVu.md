# Mục lục
> [1. Master data<br>](#master-data)
[2. Transaction<br>](#transaction)
[3. Chức năng nhóm theo master data<br>](#chức-năng-nhóm-theo-master-data)











# Master data
[Quay về mục lục](#mục-lục)

#### Nhân viên

- Mã nhân viên
- Họ và tên
- Email
- Vai trò

#### Phòng ban

- Mã phòng ban
- Tên
- Trưởng phòng
- Vị trí
- Số lượng
- Ngân sách
- Số điện thoại

#### Phòng họp

- Mã phòng họp
- Sức chứa
- Vị trí
- Thiết bị
- Trạng thái

#### Chi nhánh

- Mã chi nhánh
- Tên
- Đia chỉ
- Số điện thoại
- Email
- Số lượng nhân viên

#### Thiết bị

- Mã thiết bị
- Mã sản phẩm
- Tên
- Loại
- Vị trí
- Chi phí
- Thời hạn bảo hành
- Ngày lắp đặt
- Ngày bảo trì
- Tình trạng
- Trạng thái

#### Dịch vụ

- Mã dịch vụ
- Tên
- Chi phí
- Số lượng

---

# Transaction
[Quay về mục lục](#mục-lục)

#### Đặt phòng họp  

- Mã phòng
- Mã nhân viên đặt
- Ngày giờ
- Thời lượng
- Thành viên [ ]
- Trạng thái: Pending, Approved, Available, Repairing, Denied, Unavailable
- Mô tả
- Dịch vụ []
- Cơ sở vật chất []

#### Hủy phòng họp
-   Mã phòng
-   Mã nhân viên thực hiện hủy
-   Lý do hủy
-   Ngày giờ thực hiện hủy
-   Trạng thái cập nhật: Canceled

#### Phê duyệt phòng
- Mã phòng
- Trạng thái cập nhật

#### Thay đổi thông tin phòng họp
- Mã phòng
- Thông tin phòng họp

#### Tạo phòng
- Sức chứa
- Vị trí
- Thiết bị
- Trạng thái

#### Cập nhật thiết bị phòng
- Mã thiết bị

#### Đặt dịch vụ
- Mã dịch vụ

#### Tạo nhóm phòng họp
- Mã nhân viên

#### Đặt lịch định kỳ
- Mã lịch đặt
- Loại lặp
- Số lần lặp
- 

#### Thông báo lịch họp
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

---

# Chức năng nhóm theo master data
[Quay về mục lục](#mục-lục)

#### Nhân viên

- Thêm, sửa, xóa thông tin nhân viên
- Quản lý vai trò và quyền truy cập của nhân viên (ví dụ: quyền đặt phòng, xem phòng họp, quyền quản lý phòng họp, v.v.)
- Cập nhật thông tin liên hệ của nhân viên
- Chuyển chi nhánh nhân viên
- Theo dõi lịch sử hoạt động của nhân viên trong hệ thống
- Báo cáo sự cố phòng họp
- Thay đổi trạng thái phòng họp
- Phê duyệt đặt phòng
-

#### Phòng ban

- Thay đổi thành viên
- Cập nhật ngân sách
- Cập nhật thông tin liên hệ
- Điều chỉnh số lượng thành viên
- Cập nhật trưởng phòng

#### Phòng họp

- Thêm, sửa, xóa thông tin phòng họp (tên phòng, sức chứa, thiết bị)
- Quản lý tình trạng phòng họp (có sẵn, đã đặt, bảo trì)
- Đặt lịch phòng họp, xem lịch sử đặt phòng
- Cập nhật thiết bị trong phòng họp (ví dụ: máy chiếu, bảng trắng)
- Cập nhật lịch bảo trì thiết bị

#### Chi nhánh

- Thêm, sửa, xóa chi nhánh
- Quản lý thông tin chi nhánh (địa chỉ, số điện thoại, email)
- Quản lý số lượng nhân viên tại chi nhánh
- Cập nhật các phòng họp và thiết bị của chi nhánh

#### Thiết bị

- Quản lý thiết bị (thêm, sửa, xóa thiết bị)
- Cập nhật tình trạng thiết bị (sử dụng, bảo trì, thay thế)
- Theo dõi chi phí thiết bị và bảo trì
- Quản lý thông tin thiết bị (mã sản phẩm, loại, thời hạn bảo hành)

#### Dịch vụ

- Quản lý dịch vụ cung cấp trong phòng họp
- Cập nhật thông tin dịch vụ (tên, chi phí, số lượng)
- Đặt dịch vụ cho phòng họp khi cần
- Tạo dịch vụ theo yêu cầu
