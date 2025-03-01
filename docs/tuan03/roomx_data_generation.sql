CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Bảng loai_chu_ky
INSERT INTO public.loai_chu_ky (loai_chu_ky_id, ten, mo_ta)
VALUES ('daily', 'Hàng ngày', 'Lặp lại hàng ngày'),
       ('weekly', 'Hàng tuần', 'Lặp lại hàng tuần vào cùng một ngày'),
       ('monthly', 'Hàng tháng', 'Lặp lại hàng tháng vào cùng một ngày');

-- Bảng loai_vi_tri
INSERT INTO public.loai_vi_tri (loai_vi_tri_id, ten)
VALUES ('meeting_room', 'Phòng họp'),
       ('office', 'Văn phòng'),
       ('other', 'Khác');

-- Bảng quyen
INSERT INTO public.quyen (quyen_id, mo_ta)
VALUES ('create', 'Tạo mới'),
       ('read', 'Đọc'),
       ('update', 'Cập nhật'),
       ('delete', 'Xóa');

-- Bảng vai_tro
INSERT INTO public.vai_tro (vai_tro_id, mo_ta)
VALUES ('admin', 'Quản trị viên'),
       ('user', 'Người dùng'),
       ('staff', 'Nhân viên');

-- Bảng trang_thai_phong
INSERT INTO public.trang_thai_phong (trang_thai_phong_id, ten)
VALUES ('available', 'Sẵn sàng'),
       ('booked', 'Đã đặt'),
       ('unavailable', 'Không khả dụng');

-- Bảng suc_chua (5 mức sức chứa)
INSERT INTO public.suc_chua (suc_chua_id, gia_phong)
VALUES (5, 500000),
       (10, 1000000),
       (15, 1500000),
       (20, 2000000),
       (25, 2500000);

-- Bảng chi_nhanh (5 chi nhánh)
INSERT INTO public.chi_nhanh (chi_nhanh_id, ten, so_dien_thoai, email, dia_chi)
VALUES (uuid_generate_v4(), 'Chi nhánh Hà Nội', '02437955555', 'hanoi@example.com',
        'Tòa nhà A, Đường Nguyễn Trãi, Quận Thanh Xuân, Hà Nội'),
       (uuid_generate_v4(), 'Chi nhánh TP. Hồ Chí Minh', '02838246868', 'hcm@example.com',
        'Tòa nhà B, Đường Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh'),
       (uuid_generate_v4(), 'Chi nhánh Đà Nẵng', '02363655655', 'danang@example.com',
        'Tòa nhà C, Đường Trần Phú, Quận Hải Châu, Đà Nẵng'),
       (uuid_generate_v4(), 'Chi nhánh Cần Thơ', '02923735735', 'cantho@example.com',
        'Tòa nhà D, Đường 30 Tháng 4, Quận Ninh Kiều, Cần Thơ'),
       (uuid_generate_v4(), 'Chi nhánh Hải Phòng', '02253852852', 'haiphong@example.com',
        'Tòa nhà E, Đường Lê Hồng Phong, Quận Ngô Quyền, Hải Phòng');

-- Bảng phong_ban (10 phòng ban, mỗi phòng ban thuộc 1 chi nhánh ngẫu nhiên)
INSERT INTO public.phong_ban (phong_ban_id, ten, truong_phong, vi_tri, so_luong, so_dien_thoai, chi_nhanh_id)
SELECT uuid_generate_v4(),
       'Phòng ' || (CASE (gs.i % 5)
                        WHEN 0 THEN 'Kế Toán'
                        WHEN 1 THEN 'Nhân Sự'
                        WHEN 2 THEN 'Kinh Doanh'
                        WHEN 3 THEN 'Kỹ Thuật'
                        ELSE 'Marketing' END) || ' ' || (gs.i / 5 + 1),
       'Trưởng phòng ' || chr(65 + gs.i),
       'Tầng ' || (gs.i % 5 + 1),
       (random() * 10 + 5)::smallint,
       '0' || (987654321 + gs.i)::varchar,
       (SELECT chi_nhanh_id FROM public.chi_nhanh ORDER BY random() LIMIT 1)
FROM generate_series(0, 9) AS gs(i);

-- Bảng nhan_vien (20 nhân viên, mỗi nhân viên thuộc 1 phòng ban ngẫu nhiên)
INSERT INTO public.nhan_vien (nhan_vien_id, phong_ban_id, ho, ten, so_dien_thoai, email, gioi_tinh, chuc_vu_id,
                              anh_dai_dien)
SELECT 'nv' || lpad(gs.i::text, 3, '0'),
       (SELECT phong_ban_id FROM public.phong_ban ORDER BY random() LIMIT 1),
       (CASE (gs.i % 4)
            WHEN 0 THEN 'Nguyễn'
            WHEN 1 THEN 'Trần'
            WHEN 2 THEN 'Lê'
            ELSE 'Phạm' END),
       chr(65 + gs.i),
       '0' || (123456789 + gs.i)::varchar,
       'nv' || lpad(gs.i::text, 3, '0') || '@example.com',
       (gs.i % 2 = 0),
       (CASE (gs.i % 3)
            WHEN 0 THEN 'staff'
            WHEN 1 THEN 'user'
            ELSE 'admin'
           END),
       'avatar' || (gs.i % 5 + 1) || '.jpg'
FROM generate_series(0, 19) AS gs(i);

-- Bảng tai_khoan (20 tài khoản, tương ứng với 20 nhân viên)
INSERT INTO public.tai_khoan (tai_khoan_id, email, "password", trang_thai, google_account_id, google_secret_key)
SELECT nhan_vien_id,
       email,
       'password' || lpad(gs.i::text, 3, '0'),
       (CASE WHEN gs.i % 2 = 0 THEN 'active' ELSE 'inactive' END),
       'google_id_' || lpad(gs.i::text, 3, '0'),
       'google_secret_' || lpad(gs.i::text, 3, '0')
FROM public.nhan_vien,
     generate_series(0, 19) AS gs(i)
WHERE nhan_vien_id = 'nv' || lpad(gs.i::text, 3, '0');

-- Bảng tai_khoan_vai_tro (gán vai trò cho tài khoản, mỗi tài khoản có 1 vai trò)
INSERT INTO public.tai_khoan_vai_tro (tai_khoan_id, vai_tro_id)
SELECT nhan_vien_id,
       chuc_vu_id
FROM public.nhan_vien;

-- Bảng vai_tro_quyen (gán quyền cho vai trò)
INSERT INTO public.vai_tro_quyen (vai_tro_id, quyen_id)
VALUES ('admin', 'create'),
       ('admin', 'read'),
       ('admin', 'update'),
       ('admin', 'delete'),
       ('user', 'create'),
       ('user', 'read'),
       ('user', 'update'),
       ('staff', 'read');

-- Bảng nhom (5 nhóm, mỗi nhóm thuộc 1 phòng ban, trưởng nhóm là nhân viên ngẫu nhiên trong phòng ban)
INSERT INTO public.nhom (nhom_id, ten, loai, nhan_vien_id)
SELECT uuid_generate_v4(),
       'Nhóm ' || (ROW_NUMBER() OVER (PARTITION BY phong_ban_id ORDER BY random())) || ' - ' || pb.ten,
       'phòng ban',
       (SELECT nhan_vien_id FROM public.nhan_vien WHERE phong_ban_id = pb.phong_ban_id ORDER BY random() LIMIT 1)
FROM public.phong_ban pb
LIMIT 5;

-- Bảng thanh_vien_nhom (thêm nhân viên vào nhóm, mỗi nhóm 3-5 thành viên)
INSERT INTO public.thanh_vien_nhom (thanh_vien_nhom_id, nhan_vien_id, nhom_id)
SELECT uuid_generate_v4(),
       nv.nhan_vien_id,
       n.nhom_id
FROM public.nhan_vien nv
         JOIN public.phong_ban pb ON nv.phong_ban_id = pb.phong_ban_id
         JOIN public.nhom n ON pb.phong_ban_id = (SELECT pb2.phong_ban_id
                                                  FROM public.phong_ban pb2
                                                           JOIN public.nhom n2 ON pb2.phong_ban_id =
                                                                                  (SELECT pb3.phong_ban_id
                                                                                   from public.phong_ban pb3
                                                                                   where pb3.phong_ban_id = n2.nhom_id
                                                                                   limit 1)
                                                  WHERE n2.nhom_id = n.nhom_id)
WHERE n.nhom_id IN (SELECT nhom_id FROM public.nhom)
ORDER BY n.nhom_id, random()
LIMIT (SELECT count(*) FROM public.nhom) * 4;
-- Giả sử mỗi nhóm có trung bình 4 thành viên

-- Bảng loai_thiet_bi (5 loại thiết bị)
INSERT INTO public.loai_thiet_bi (loai_thiet_bi_id, ten, gia)
VALUES (uuid_generate_v4(), 'Máy chiếu', 1000000),
       (uuid_generate_v4(), 'Màn hình', 500000),
       (uuid_generate_v4(), 'Bảng trắng', 200000),
       (uuid_generate_v4(), 'Microphone', 300000),
       (uuid_generate_v4(), 'Loa', 700000);

-- Bảng vi_tri (10 vị trí, mỗi vị trí thuộc 1 chi nhánh)
INSERT INTO public.vi_tri (vi_tri_id, tang, toa_nha, chi_nhanh_id, phong, so_do, slug, loai_vi_tri_id)
SELECT uuid_generate_v4(),
       (gs.i % 5 + 1),
       (gs.i % 2 + 1),
       (SELECT chi_nhanh_id FROM public.chi_nhanh ORDER BY random() LIMIT 1),
       (gs.i + 101),
       ARRAY ['so_do_' || (gs.i % 3 + 1) || '.png'],
       'vi-tri-' || (gs.i + 1),
       (SELECT loai_vi_tri_id FROM public.loai_vi_tri ORDER BY random() LIMIT 1)
FROM generate_series(0, 9) AS gs(i);

-- Bảng phong_hop (5 phòng họp, mỗi phòng họp thuộc 1 vị trí)
INSERT INTO public.phong_hop (phong_hop_id, trang_thai, mo_ta, suc_chua_id, vi_tri_id)
SELECT uuid_generate_v4(),
       'available',
       'Phòng họp ' || (gs.i + 1),
       (SELECT suc_chua_id FROM public.suc_chua ORDER BY random() LIMIT 1),
       (SELECT vi_tri_id FROM public.vi_tri ORDER BY random() LIMIT 1)
FROM generate_series(0, 4) AS gs(i);

-- Bảng thiet_bi (10 thiết bị, mỗi thiết bị thuộc 1 loại thiết bị, có thể thuộc phòng họp hoặc không)
INSERT INTO public.thiet_bi (thiet_bi_id, ten, trang_thai, phong_hop_id, loai_thiet_bi_id, thuong_hieu, hinh_anh_mo_ta)
SELECT uuid_generate_v4(),
       'Thiết bị ' || (gs.i + 1),
       'available',
       (CASE
            WHEN gs.i % 2 = 0 THEN (SELECT phong_hop_id FROM public.phong_hop ORDER BY random() LIMIT 1)
            ELSE NULL END),
       (SELECT loai_thiet_bi_id FROM public.loai_thiet_bi ORDER BY random() LIMIT 1),
       'Thương hiệu ' || chr(65 + gs.i % 3),
       ARRAY ['hinh_anh_' || (gs.i % 2 + 1) || '.png']
FROM generate_series(0, 9) AS gs(i);

-- Bảng doi_tac (5 đối tác)
INSERT INTO public.doi_tac (email, created_at, updated_at, doi_tac_id)
VALUES ('doitac1@example.com', NOW(), NOW(), uuid_generate_v4()),
       ('doitac2@example.com', NOW(), NOW(), uuid_generate_v4()),
       ('doitac3@example.com', NOW(), NOW(), uuid_generate_v4()),
       ('doitac4@example.com', NOW(), NOW(), uuid_generate_v4()),
       ('doitac5@example.com', NOW(), NOW(), uuid_generate_v4());

-- Bảng dich_vu (5 dịch vụ)
INSERT INTO public.dich_vu (dich_vu_id, ten, mo_ta, ghi_chu, so_luong)
VALUES (uuid_generate_v4(), 'Teabreak', 'Teabreak giữa giờ', '', 100),
       (uuid_generate_v4(), 'Ăn trưa', 'Ăn trưa tại nhà hàng', '', 50),
       (uuid_generate_v4(), 'In ấn', 'In ấn tài liệu', 'Giấy A4', 200),
       (uuid_generate_v4(), 'Quay phim', 'Quay phim sự kiện', '', 10),
       (uuid_generate_v4(), 'Chụp ảnh', 'Chụp ảnh sự kiện', '', 15);

-- Bảng dich_vu_thiet_bi (gán thiết bị cho dịch vụ)
INSERT INTO public.dich_vu_thiet_bi (dich_vu_id, thiet_bi_id, so_luong)
VALUES ((SELECT dich_vu_id FROM public.dich_vu WHERE ten = 'Teabreak' LIMIT 1),
        (SELECT thiet_bi_id FROM public.thiet_bi WHERE ten = 'Thiết bị 4' LIMIT 1), 2),
       ((SELECT dich_vu_id FROM public.dich_vu WHERE ten = 'Ăn trưa' LIMIT 1),
        (SELECT thiet_bi_id FROM public.thiet_bi WHERE ten = 'Thiết bị 5' LIMIT 1), 1),
       ((SELECT dich_vu_id FROM public.dich_vu WHERE ten = 'In ấn' LIMIT 1),
        (SELECT thiet_bi_id FROM public.thiet_bi WHERE ten = 'Thiết bị 1' LIMIT 1), 5);

-- Bảng ngay_ngoai_le (3 ngày ngoại lệ)
INSERT INTO public.ngay_ngoai_le (ngay_ngoai_le_id, ngay, thoi_gian_cho_phep)
VALUES (uuid_generate_v4(), '2024-05-01', '[2024-05-01 08:00:00, 2024-05-01 18:00:00]'),
       (uuid_generate_v4(), '2024-09-02', '[2024-09-02 08:00:00, 2024-09-02 18:00:00]'),
       (uuid_generate_v4(), '2024-04-30', '[2024-04-30 08:00:00, 2024-04-30 18:00:00]');

-- Bảng ngan_sach (5 bản ghi ngân sách, cho các phòng ban khác nhau, các tháng và năm khác nhau)
INSERT INTO public.ngan_sach (ngan_sach_id, chi_phi, thang, trang_thai, phong_ban_id, nam)
SELECT uuid_generate_v4(),
       (random() * 9 + 1) * 1000000,
       (gs.i % 12 + 1),
       (CASE WHEN gs.i % 2 = 0 THEN 'approved' ELSE 'pending' END),
       (SELECT phong_ban_id FROM public.phong_ban ORDER BY random() LIMIT 1),
       2024
FROM generate_series(0, 4) AS gs(i);

-- Bảng yeu_cau_dat_phong (10 yêu cầu đặt phòng, với các trạng thái, ngày giờ khác nhau)
INSERT INTO public.yeu_cau_dat_phong (yeu_cau_dat_phong_id, chu_ky_id, trang_thai_duyet, created_at, updated_at, lap_lai, nhan_vien_dat_id, nhan_vien_duyet_id, phong_hop_id, thanh_vien_id, dich_vu_id)
SELECT uuid_generate_v4(),
       uuid_generate_v4(), -- Giả sử tạo mới chu_ky_id
       (CASE WHEN gs.i % 3 = 0 THEN 'approved' WHEN gs.i % 3 = 1 THEN 'pending' ELSE 'rejected' END),
       NOW() - (gs.i * INTERVAL '1 day'),
       NOW() - (gs.i * INTERVAL '1 day'),
       (gs.i % 2 = 0),
       (SELECT nhan_vien_id FROM public.nhan_vien ORDER BY random() LIMIT 1),
       (CASE WHEN gs.i % 2 = 0 THEN (SELECT nhan_vien_id FROM public.nhan_vien WHERE chuc_vu_id = 'admin' ORDER BY random() LIMIT 1) ELSE NULL END),
       (SELECT phong_hop_id FROM public.phong_hop ORDER BY random() LIMIT 1),
       NULL, -- Giả sử không có thanh_vien
       (SELECT dich_vu_id FROM public.dich_vu ORDER BY random() LIMIT 1)
FROM generate_series(0, 9) AS gs(i);

-- Bảng chu_ky (10 chu kỳ, tương ứng với 10 yêu cầu đặt phòng, với các loại chu kỳ khác nhau)
INSERT INTO public.chu_ky (loai_chu_ky_id, ngay_trong_tuan, chu_ky_id, ngay_cua_thang, so_lan_lap, thang_trong_nam, yeu_cau_dat_phong_id, trang_thai_id, trang_thai_phong_id)
SELECT (CASE WHEN gs.i % 3 = 0 THEN 'daily' WHEN gs.i % 3 = 1 THEN 'weekly' ELSE 'monthly' END),
       (gs.i % 7 + 1),
       uuid_generate_v4(), -- Sửa đổi ở đây: Sinh ra chu_ky_id mới
       (gs.i % 28 + 1),
       (gs.i % 5 + 1),
       (gs.i % 12 + 1),
       y.yeu_cau_dat_phong_id,
       'active',
       'available'
FROM public.yeu_cau_dat_phong y, generate_series(0,9) as gs(i)
WHERE y.yeu_cau_dat_phong_id IN (SELECT yeu_cau_dat_phong_id from public.yeu_cau_dat_phong)
  AND (SELECT COUNT(*) FROM public.chu_ky WHERE yeu_cau_dat_phong_id = y.yeu_cau_dat_phong_id) = 0;

-- Bảng thanh_vien (5 bản ghi, mỗi bản ghi liên kết 1 yêu cầu đặt phòng với 1 nhóm hoặc 1 đối tác)
INSERT INTO public.thanh_vien (thanh_vien_id, nhom_nhan_vien_id, loai, nhom_doi_tac_id, don_yeu_cau_id)
SELECT uuid_generate_v4(),
       (CASE WHEN gs.i % 2 = 0 THEN (SELECT nhom_id FROM public.nhom ORDER BY random() LIMIT 1) ELSE NULL END),
       (CASE WHEN gs.i % 2 = 0 THEN 'nhom' ELSE 'doitac' END),
       (CASE WHEN gs.i % 2 = 1 THEN (SELECT doi_tac_id FROM public.doi_tac ORDER BY random() LIMIT 1) ELSE NULL END),
       (SELECT yeu_cau_dat_phong_id FROM public.yeu_cau_dat_phong ORDER BY random() LIMIT 1)
FROM generate_series(0, 4) AS gs(i);

-- Bảng nhom_don_yeu_cau (5 bản ghi, mỗi bản ghi liên kết 1 yêu cầu đặt phòng với 1 nhóm)
INSERT INTO public.nhom_don_yeu_cau (thanh_vien_id, nhom_id)
SELECT (SELECT thanh_vien_id FROM public.thanh_vien WHERE loai = 'nhom' ORDER BY random() LIMIT 1),
       (SELECT nhom_id FROM public.nhom ORDER BY random() LIMIT 1)
FROM generate_series(0, 4) as gs(i)
ON CONFLICT (thanh_vien_id, nhom_id) DO NOTHING;