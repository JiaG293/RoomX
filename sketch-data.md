
## 1. Masterdata: 

- Phòng ban ()

- Phòng (id, tên phòng, toà nhà, tầng, chi phí, chi nhánh)

- Nhân viên(id, ...)

- Công ty(idid, tên cty, đchi)

- Vật tư (id, tên thiết bị, trạng thái, chi phí,  )

- Người dùng(id,...,role)

- chi nhánh(id, tên chi nhánh, địa chỉ )



## 2. Transaction


- Đặt phòng (mã phòng, mã nhân viên, ngày giờ, thời lượng, thành viên [], trạng thái ) 
- Mời cuộc họp (mã phòng, thành viên [], ngày giờ, thời lượng) 
- Duyệt phòng(mã phòng)
- audit logs

## 3. Trạng thái đặt phòng

- pending - chưa được duyệt,
- approved - đc duyet,
- avaiable - phong trong
- repairing - sua chua
- denied - tu choi
- unavailable -khong kha dung (phong loi su co)
- cleaning - dang don dep


---

đt: đặt lịch, xem lịch, tk

admin cms cho thêm permission , cms công ty, nhân viên


note nâng cao: role lao công, dọn dẹp, bảo trì phòng -> chức năng riêng

----------------------


# CMS ADMIN HỆ THỐNG

- Quản lý người dùng
- Quản lý công ty
- Quản lý tài nguyên
- Duyệt 

# Người dùng

**Nhân viên**
- Đặt lịch
- Xem lịch 
- Xem lịch sử đặt phòng
- Cập nhật hồ sơ
- Báo lỗi thiết bị, phòng
- Yêu cầu thêm dịch vụ
- Hủy lịch
- Mời thành viên
- Chấp nhận/ hủy lời mời
- 
**Quản trị viên**
- Quản lý người dùng(cấu hình permission, role)
- Duyệt phòng
- Duyệt yêu cầu hủy
- Tạo phòng
- Cập nhật phòng
- Tạo tài nguyên vật tư
- Cấu hình CMS (thời gian chờ,)
- Hoãn phòng
- Chuyển phòng
- Hủy phòng
- Thống kê(time sử dụng, chi phí, phòng ban, vật tư hư hỏng,) 
- Xuất báo cáo
- gợi ý phòng
- 
