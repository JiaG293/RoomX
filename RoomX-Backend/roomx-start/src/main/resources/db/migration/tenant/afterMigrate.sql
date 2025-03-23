SET search_path TO ${schema}, "$user", public;

-- ROLE
INSERT INTO "role" (role_id, description, level)
VALUES ('USER', 'Default role for employees', 1),
       ('OWNER', 'Role for owners with full access', 4),
       ('ADMIN', 'Administrator role with system-wide privileges', 3),
       ('SUPPORTER', 'Support personnel role with limited access', 2),
       ('APPROVER', 'Role for approvers with approval permissions', 2)
ON CONFLICT (role_id) DO NOTHING;

-- USER
INSERT INTO "user" (first_name, last_name, phone_number, email, gender, avatar_image, user_type, user_code, user_id,
                    created_at, updated_at, enable)
VALUES ('nguyen van', 'admin', '1111111111', 'asgy2002@gmail.com', true, null, 'EMPLOYEE', '20053331',
        '374e33ed-1d51-4298-a3be-b51b4d7529a3', '2025-03-10 11:24:31.000000', '2025-03-10 11:24:33.000000', true),
       ('tran van', 'manager', '1111111112', '821377326.jiag@gmail.com', true, null, 'EMPLOYEE', '20053332',
        'f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', '2025-03-10 11:24:48.000000', '2025-03-10 11:24:47.000000', true),
       ('huynh van', 'user', '1111111113', 'user001.roomx@gmail.com', true, null, 'EMPLOYEE', '20053333',
        'ea4e9c4c-a317-4064-8a5b-da2b338e4180', '2025-03-10 11:26:25.000000', '2025-03-10 11:26:26.000000', true),
       ('Dương Yến', 'Nhi', '1111111114', 'user002.roomx@gmail.com', false, null, 'EMPLOYEE', '20053334',
        'ea4e9c4c-a317-4064-8a5b-da2b339e4380', '2025-03-10 11:26:25.000000', '2025-03-10 11:26:26.000000', true),
       ('Mộc Văn', 'Nghĩa', '1111111115', 'user004.roomx@gmail.com', true, null, 'EMPLOYEE', '20053335',
        'ea4e9c4c-a317-4064-8a5b-da2b339e4580', '2025-03-10 11:26:25.000000', '2025-03-10 11:26:26.000000', true),
       ('Trần Tấn', 'Nghĩa', '1111111116', 'user005.roomx@gmail.com', true, null, 'EMPLOYEE', '20053336',
        'ea4e9c4c-a317-4064-8a5b-da2b339e4182', '2025-03-10 11:26:25.000000', '2025-03-10 11:26:26.000000', true),
       ('Nguyễn', 'Huy', '1111111117', 'user006.roomx@gmail.com', true, null, 'EMPLOYEE', '20053337',
        'ea4e9c4c-a317-4064-8a5b-da2b339e4181', '2025-03-10 11:26:25.000000', '2025-03-10 11:26:26.000000', true)
ON CONFLICT (user_id) DO NOTHING;

INSERT INTO user_role(user_id, role_id)
VALUES ('374e33ed-1d51-4298-a3be-b51b4d7529a3', 'ADMIN'),
       ('374e33ed-1d51-4298-a3be-b51b4d7529a3', 'APPROVER'),
       ('374e33ed-1d51-4298-a3be-b51b4d7529a3', 'SUPPORTER'),
       ('374e33ed-1d51-4298-a3be-b51b4d7529a3', 'USER'),
       ('f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', 'APPROVER'),
       ('f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', 'USER'),
       ('ea4e9c4c-a317-4064-8a5b-da2b338e4180', 'USER'),
       ('ea4e9c4c-a317-4064-8a5b-da2b339e4380', 'USER'),
       ('ea4e9c4c-a317-4064-8a5b-da2b339e4580', 'USER'),
       ('ea4e9c4c-a317-4064-8a5b-da2b339e4182', 'USER'),
       ('ea4e9c4c-a317-4064-8a5b-da2b339e4181', 'USER')
ON CONFLICT (user_id, role_id) DO NOTHING;


-- BRANCH
INSERT INTO branch (branch_id, name, phone_number, email, address, branch_code, status)
VALUES ('e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Chi nhánh A', '0901234567', 'branch1@example.com',
        '123 Đường ABC, Quận 1', 'BN001', 'ACTIVE'),
       ('d8d06462-63ff-4264-bf34-e40cb77fd263', 'Chi nhánh B', '0912345679', 'branch2@example.com',
        '456 Đường MNK, Quận 2', 'BN002', 'ACTIVE'),
       ('985a287b-a742-4c1e-84fd-d4429ada7efa', 'Chi nhánh C', '0912345678', 'branch3@example.com',
        '789 Đường XYZ, Quận 3', 'BN003', 'INACTIVE')
ON CONFLICT (branch_id) DO NOTHING;


-- PLACE
INSERT INTO place (place_id, branch_id, name, layout, place_type, parent_id, code, status)
VALUES ('65f1d8a0-6885-4b51-a691-f843b279dd8b', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Chi nhánh A', 'URL', 'BRANCH',
        null, 'BN001', 'ACTIVE'),
       ('0b83c73a-afe0-4bc5-ade7-f5c8e816c741', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Chi nhánh B', 'URL', 'BRANCH',
        null, 'BN002', 'ACTIVE'),
       ('f1d12862-8782-4cea-b0da-22e0b2579c46', '985a287b-a742-4c1e-84fd-d4429ada7efa', 'Chi nhánh C', 'URL', 'BRANCH',
        null, 'BN003', 'ACTIVE'),
       ('bbadcf08-52de-4914-9c1e-9abea4f66520', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tòa A25', 'lmao', 'BUILDING',
        '65f1d8a0-6885-4b51-a691-f843b279dd8b', 'A25', 'ACTIVE'),
       ('a84e8c68-7005-4889-be77-039b722e25e7', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tầng 1', 'url sơ đồ 1',
        'FLOOR', 'bbadcf08-52de-4914-9c1e-9abea4f66520', '1', 'ACTIVE'),
       ('0170373d-c356-4ab3-87eb-8b2c85283d14', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tầng 3', 'url 3', 'FLOOR',
        'bbadcf08-52de-4914-9c1e-9abea4f66520', '3', 'ACTIVE'),
       ('2f790170-d3b3-4182-8906-278bccdff2f1', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tầng 4', null, 'FLOOR',
        'bbadcf08-52de-4914-9c1e-9abea4f66520', '4', 'ACTIVE'),
       ('52f181c4-d56b-4d3a-8a37-63ac892be3f2', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tòa A26', 'lmao', 'BUILDING',
        '65f1d8a0-6885-4b51-a691-f843b279dd8b', 'A26', 'ACTIVE'),
       ('c74a844e-36ec-4b46-9926-03214929fe45', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tầng 1', 'url sơ đồ 1',
        'FLOOR', '52f181c4-d56b-4d3a-8a37-63ac892be3f2', '1', 'ACTIVE'),
       ('1e2c0d33-20c5-4c13-974b-2248fc529189', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tầng 3', 'url 3', 'FLOOR',
        '52f181c4-d56b-4d3a-8a37-63ac892be3f2', '3', 'ACTIVE'),
       ('adc6feab-8c23-497f-b19f-77341b545aac', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tầng 4', null, 'FLOOR',
        '52f181c4-d56b-4d3a-8a37-63ac892be3f2', '4', 'ACTIVE'),
       ('ef88da1f-cc5f-4cae-a2a1-4a7e2f2160da', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tòa C22', 'lmao', 'BUILDING',
        '0b83c73a-afe0-4bc5-ade7-f5c8e816c741', 'C22', 'ACTIVE'),
       ('f32615d8-dd19-4946-ba77-f489e4e49b62', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tầng 1', 'url sơ đồ 1',
        'FLOOR', 'ef88da1f-cc5f-4cae-a2a1-4a7e2f2160da', '1', 'ACTIVE'),
       ('8bb83093-79f3-402f-83a2-0408f0c08f59', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tầng 3', 'url 3', 'FLOOR',
        'ef88da1f-cc5f-4cae-a2a1-4a7e2f2160da', '3', 'ACTIVE'),
       ('10e6e9a2-400a-4636-b139-e609f2d69bce', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tầng 4', null, 'FLOOR',
        'ef88da1f-cc5f-4cae-a2a1-4a7e2f2160da', '4', 'ACTIVE'),
       ('01a4f3c2-76d0-4e79-9685-621ed3077beb', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tòa C29', 'lmao', 'BUILDING',
        '0b83c73a-afe0-4bc5-ade7-f5c8e816c741', 'C29', 'ACTIVE'),
       ('ef00a2a3-9438-46f1-87a3-318de786de5b', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tầng 1', 'url sơ đồ 1',
        'FLOOR', '01a4f3c2-76d0-4e79-9685-621ed3077beb', '1', 'ACTIVE'),
       ('dce5ecf4-05dc-49a6-8f7d-5a144653c6d1', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tầng 3', 'url 3', 'FLOOR',
        '01a4f3c2-76d0-4e79-9685-621ed3077beb', '3', 'ACTIVE'),
       ('b154ec93-baef-4663-b2bf-65f2edb7c111', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tầng 4', null, 'FLOOR',
        '01a4f3c2-76d0-4e79-9685-621ed3077beb', '4', 'ACTIVE')
ON CONFLICT (place_id) DO NOTHING;


--EQUIPMENT
insert into giau.equipment (equipment_id, name, brand, description, created_at, updated_at, equipment_code, status)
values  ('0a3b8f09-eab1-45c9-a955-1cb484ead7c7', 'Máy chấm công vân tay', 'Sennheiser', 'Desc_G44H02I7Z98', '2025-03-23 12:07:25.986213', '2025-03-23 12:07:25.986213', 'EQUIPMENT_F4XC67', 'ACTIVE'),
        ('fa34d21b-3451-4d62-9bf4-a4a61237b786', 'Camera hội nghị không dây', 'SHARP', 'Desc_R117MRC35V', '2025-03-23 12:07:26.485715', '2025-03-23 12:07:26.485715', 'EQUIPMENT_IFWTNZ', 'ACTIVE'),
        ('52102dde-95f7-4d6c-b1c0-5b5f7803e035', 'Loa âm trần', 'Shure', 'Desc_HS2546A3T9Q', '2025-03-23 12:07:27.013208', '2025-03-23 12:07:27.013208', 'EQUIPMENT_G114SU', 'ACTIVE'),
        ('8856f132-0cb2-437a-b001-beb5f8628185', 'Bộ thu phát Micro', 'Bose', 'Desc_E44RIMSWNMW', '2025-03-23 12:07:27.541779', '2025-03-23 12:07:27.541779', 'EQUIPMENT_R32Y62', 'ACTIVE'),
        ('6376ac5c-82f9-40cb-a8ef-fc4ebcee1092', 'Máy in đa chức năng', 'Yealink', 'Desc_Z2VPSLUC7L', '2025-03-23 12:07:18.376517', '2025-03-23 12:07:18.376517', 'EQUIPMENT_OC2GD6', 'ACTIVE'),
        ('5053eaf7-652b-4169-9456-9c10ac003797', 'Bảng trắng di động 1.5m x 2m', 'Xuân Hòa', 'Desc_0T3RW6GVWW2', '2025-03-23 12:07:18.982332', '2025-03-23 12:07:18.982332', 'EQUIPMENT_FKDVEI', 'ACTIVE'),
        ('b95c3e11-bc06-4ad8-aaa5-aec25d871aba', 'Giá đựng tài liệu văn phòng', 'Jabra', 'Desc_8DA4BN285W', '2025-03-23 12:07:19.600418', '2025-03-23 12:07:19.600418', 'EQUIPMENT_NW8P25', 'ACTIVE'),
        ('ee070200-33d8-4b70-8290-22b9d1265d7c', 'Tủ lạnh mini văn phòng', 'Bose', 'Desc_ABU2R2WTMNF', '2025-03-23 12:07:20.183622', '2025-03-23 12:07:20.183622', 'EQUIPMENT_TC188Y', 'ACTIVE'),
        ('a10c635a-e251-4097-94b6-638509fd889e', 'Micro cài áo không dây', 'Epson', 'Desc_FH0FIBPMYI', '2025-03-23 12:07:20.769586', '2025-03-23 12:07:20.769586', 'EQUIPMENT_N09VXS', 'ACTIVE'),
        ('76471b1d-bdac-432f-b979-dce9153de2ea', 'Máy chấm công vân tay', 'Panasonic', 'Desc_NOJBVACD7UO', '2025-03-23 12:07:21.351954', '2025-03-23 12:07:21.351954', 'EQUIPMENT_K6F8YQ', 'ACTIVE'),
        ('47ba920d-7472-4c75-9136-9c786cf919bc', 'Hệ thống chiếu sáng thông minh', 'Epson', 'Desc_3ZHBMIS7LQZ', '2025-03-23 12:07:21.835968', '2025-03-23 12:07:21.835968', 'EQUIPMENT_6VQZL6', 'ACTIVE'),
        ('61853674-7ca6-4689-bfb3-a00965642c3c', 'Switch mạng 24 cổng', 'SONY', 'Desc_O78VCVBITA', '2025-03-23 12:07:22.350217', '2025-03-23 12:07:22.350217', 'EQUIPMENT_QQ0UWH', 'ACTIVE'),
        ('c811089c-2a92-4833-87c5-2f813739b520', 'Máy in đa chức năng', 'Epson', 'Desc_HH0FJK1KXCC', '2025-03-23 12:07:22.865169', '2025-03-23 12:07:22.865169', 'EQUIPMENT_V3YVCQ', 'ACTIVE'),
        ('bb50e82a-63dd-4fd0-8bfd-be5355bd8acc', 'Micro hội nghị', 'Shure', 'Desc_O8VOET6YKK', '2025-03-23 12:07:23.819676', '2025-03-23 12:07:23.819676', 'EQUIPMENT_LZCZSH', 'ACTIVE'),
        ('905d2f8a-6adc-42de-812d-4fcab61af3c3', 'Bộ phát sóng WiFi di động', 'LG', 'Desc_5WSEE2OQJ9T', '2025-03-23 12:07:24.418061', '2025-03-23 12:07:24.418061', 'EQUIPMENT_C3MRC6', 'ACTIVE'),
        ('2080fffa-c3c7-4354-b4c5-0f85fe60f045', 'Bàn họp 5m gỗ MDF', 'SONY', 'Desc_HU6WQWZH01', '2025-03-23 12:07:25.006574', '2025-03-23 12:07:25.006574', 'EQUIPMENT_SJIIIQ', 'ACTIVE'),
        ('0f915b72-0cf7-437c-a8ee-4aa0e629ce02', 'Loa ngoài hội nghị', 'Jabra', 'Desc_KY79HHQG6WC', '2025-03-23 12:07:25.486122', '2025-03-23 12:07:25.486122', 'EQUIPMENT_ZI8HDZ', 'ACTIVE'),
        ('8a768eee-20bd-428a-b692-0b0c7a998430', 'Thùng rác văn phòng', 'Xuân Hòa', 'Desc_0I3LGGWV5W1K', '2025-03-23 12:07:28.070503', '2025-03-23 12:07:28.070503', 'EQUIPMENT_9HGGCS', 'ACTIVE'),
        ('aabea344-53f8-41b5-8312-e0b52d28e67c', 'Hệ thống loa phòng họp', 'Jabra', 'Desc_EQPX52VBSFC', '2025-03-23 12:07:28.582277', '2025-03-23 12:07:28.582277', 'EQUIPMENT_KG3FXY', 'ACTIVE'),
        ('90e53101-0afe-4411-8e46-313d80787a0e', 'Bộ chia HDMI 8 cổng', 'Xuân Hòa', 'Desc_FY7SZORNSHF', '2025-03-23 12:07:29.093243', '2025-03-23 12:07:29.093243', 'EQUIPMENT_MPYLJW', 'ACTIVE'),
        ('187815ee-d470-4ddc-8c6a-89a87c357e10', 'Máy điều hòa không khí', 'Panasonic', 'Desc_DKXE91CXISD', '2025-03-23 12:07:29.653378', '2025-03-23 12:07:29.653378', 'EQUIPMENT_SFQ3XL', 'ACTIVE'),
        ('676f57d7-4d44-4dd6-9428-12bd5d306b0e', 'Tay điều khiển từ xa', 'SHARP', 'Desc_FSYREBFZBQG', '2025-03-23 12:07:30.600339', '2025-03-23 12:07:30.600339', 'EQUIPMENT_9HWJ8X', 'ACTIVE'),
        ('ffdb4322-f424-46ae-95d5-d26f11ff6ce1', 'Ghế họp lưới', 'Fami', 'Desc_EKXB0WXIDPB', '2025-03-23 12:07:31.303252', '2025-03-23 12:07:31.303252', 'EQUIPMENT_KM0VH3', 'ACTIVE')
ON CONFLICT (equipment_id) DO NOTHING;

insert into giau.equipment_price_history (equipment_price_history_id, equipment_id, valid_from, unit_price, valid_end, is_active)
values  ('fe6f7a03-43c3-48aa-899b-76d58ecb4c5b', '6376ac5c-82f9-40cb-a8ef-fc4ebcee1092', '2025-03-23 08:24:42.000000', 61952, '2025-03-29 08:24:42.000000', true),
        ('66a2944d-45c9-4665-84b0-225198772da3', '5053eaf7-652b-4169-9456-9c10ac003797', '2025-03-23 08:24:42.000000', 88818, '2025-03-29 08:24:42.000000', true),
        ('23d2bf06-3f3d-4944-8b09-b530ed387777', 'b95c3e11-bc06-4ad8-aaa5-aec25d871aba', '2025-03-23 08:24:42.000000', 41957, '2025-03-29 08:24:42.000000', true),
        ('88ab982c-d1a1-478e-b8e2-e70e786981dc', 'ee070200-33d8-4b70-8290-22b9d1265d7c', '2025-03-23 08:24:42.000000', 27344, '2025-03-29 08:24:42.000000', true),
        ('b02a75fa-7481-4b3e-b8fa-c1981a37649c', 'a10c635a-e251-4097-94b6-638509fd889e', '2025-03-23 08:24:42.000000', 16133, '2025-03-29 08:24:42.000000', true),
        ('64ef4786-cf3c-4b9e-ac85-8a8e96c82c3b', '76471b1d-bdac-432f-b979-dce9153de2ea', '2025-03-23 08:24:42.000000', 28326, '2025-03-29 08:24:42.000000', true),
        ('f0657731-608f-4d50-a77d-e60185b3e286', '47ba920d-7472-4c75-9136-9c786cf919bc', '2025-03-23 08:24:42.000000', 43459, '2025-03-29 08:24:42.000000', true),
        ('dff0a0c8-a847-489e-8433-45ed846e7c8e', '61853674-7ca6-4689-bfb3-a00965642c3c', '2025-03-23 08:24:42.000000', 51847, '2025-03-29 08:24:42.000000', true),
        ('d95884f5-de7b-4592-99ac-082f80d6786f', 'c811089c-2a92-4833-87c5-2f813739b520', '2025-03-23 08:24:42.000000', 94658, '2025-03-29 08:24:42.000000', true),
        ('4f4f9307-7c3a-4f23-9988-ad078119bbab', 'bb50e82a-63dd-4fd0-8bfd-be5355bd8acc', '2025-03-23 08:24:42.000000', 40253, '2025-03-29 08:24:42.000000', true),
        ('8a830324-615b-4662-830c-af13fd5de6dd', '905d2f8a-6adc-42de-812d-4fcab61af3c3', '2025-03-23 08:24:42.000000', 31283, '2025-03-29 08:24:42.000000', true),
        ('906dc5dc-2079-4ad4-8fbe-7417bec2c023', '2080fffa-c3c7-4354-b4c5-0f85fe60f045', '2025-03-23 08:24:42.000000', 41714, '2025-03-29 08:24:42.000000', true),
        ('b3192f8b-1cee-4129-86cf-d6e45e7da1f5', '0f915b72-0cf7-437c-a8ee-4aa0e629ce02', '2025-03-23 08:24:42.000000', 83464, '2025-03-29 08:24:42.000000', true),
        ('5e5c9713-0831-4153-9a53-d8f4736f3bc7', '0a3b8f09-eab1-45c9-a955-1cb484ead7c7', '2025-03-23 08:24:42.000000', 37617, '2025-03-29 08:24:42.000000', true),
        ('69129b39-081a-41e9-971c-d216473229e3', 'fa34d21b-3451-4d62-9bf4-a4a61237b786', '2025-03-23 08:24:42.000000', 17530, '2025-03-29 08:24:42.000000', true),
        ('1b243ccd-18bd-4849-8da1-c282f53274d5', '52102dde-95f7-4d6c-b1c0-5b5f7803e035', '2025-03-23 08:24:42.000000', 34351, '2025-03-29 08:24:42.000000', true),
        ('fae0484e-f304-4743-ba36-b8a143a2ed3c', '8856f132-0cb2-437a-b001-beb5f8628185', '2025-03-23 08:24:42.000000', 22848, '2025-03-29 08:24:42.000000', true),
        ('b8126131-eb9f-4caa-8e7d-1aba5471d54f', '8a768eee-20bd-428a-b692-0b0c7a998430', '2025-03-23 08:24:42.000000', 66638, '2025-03-29 08:24:42.000000', true),
        ('e4218b52-626f-49d6-984b-644f8acff005', 'aabea344-53f8-41b5-8312-e0b52d28e67c', '2025-03-23 08:24:42.000000', 23463, '2025-03-29 08:24:42.000000', true),
        ('87531586-685f-4a2e-b53e-39da261dcde1', '90e53101-0afe-4411-8e46-313d80787a0e', '2025-03-23 08:24:42.000000', 55575, '2025-03-29 08:24:42.000000', true),
        ('61421b80-8944-495f-b274-e3647936bc89', '187815ee-d470-4ddc-8c6a-89a87c357e10', '2025-03-23 08:24:42.000000', 47038, '2025-03-29 08:24:42.000000', true),
        ('6a9f1b0e-6791-4929-9354-9cc222768c8a', '676f57d7-4d44-4dd6-9428-12bd5d306b0e', '2025-03-23 08:24:42.000000', 47568, '2025-03-29 08:24:42.000000', true),
        ('9d7f9064-5a89-47d3-a63f-27d80efcee9a', 'ffdb4322-f424-46ae-95d5-d26f11ff6ce1', '2025-03-23 08:24:42.000000', 61553, '2025-03-29 08:24:42.000000', true)
ON CONFLICT (equipment_price_history_id) DO NOTHING;

insert into giau.image_url (entity_id, image_order, entity_type, url)
values  ('6376ac5c-82f9-40cb-a8ef-fc4ebcee1092', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_00.03.56_[2022.02.04_18.58.01].png'),
        ('6376ac5c-82f9-40cb-a8ef-fc4ebcee1092', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.50.32_[2022.02.04_19.02.50].png'),
        ('6376ac5c-82f9-40cb-a8ef-fc4ebcee1092', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.45.05_[2022.02.04_19.02.11].png'),
        ('5053eaf7-652b-4169-9456-9c10ac003797', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.50.32_[2022.02.04_19.02.50].png'),
        ('5053eaf7-652b-4169-9456-9c10ac003797', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.33_[2022.02.04_19.00.07].png'),
        ('5053eaf7-652b-4169-9456-9c10ac003797', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.26_[2022.02.04_18.59.51].png'),
        ('b95c3e11-bc06-4ad8-aaa5-aec25d871aba', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.45.05_[2022.02.04_19.02.11].png'),
        ('b95c3e11-bc06-4ad8-aaa5-aec25d871aba', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.26_[2022.02.04_18.59.51].png'),
        ('b95c3e11-bc06-4ad8-aaa5-aec25d871aba', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.14.36_[2022.02.04_19.00.34].png'),
        ('ee070200-33d8-4b70-8290-22b9d1265d7c', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.26_[2022.02.04_18.59.51].png'),
        ('ee070200-33d8-4b70-8290-22b9d1265d7c', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.50.32_[2022.02.04_19.02.50].png'),
        ('ee070200-33d8-4b70-8290-22b9d1265d7c', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_00.03.56_[2022.02.04_18.58.01].png'),
        ('a10c635a-e251-4097-94b6-638509fd889e', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.33_[2022.02.04_19.00.07].png'),
        ('a10c635a-e251-4097-94b6-638509fd889e', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.26_[2022.02.04_18.59.51].png'),
        ('a10c635a-e251-4097-94b6-638509fd889e', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_00.03.56_[2022.02.04_18.58.01].png'),
        ('76471b1d-bdac-432f-b979-dce9153de2ea', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.33_[2022.02.04_19.00.07].png'),
        ('76471b1d-bdac-432f-b979-dce9153de2ea', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.50.32_[2022.02.04_19.02.50].png'),
        ('76471b1d-bdac-432f-b979-dce9153de2ea', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.14.36_[2022.02.04_19.00.34].png'),
        ('47ba920d-7472-4c75-9136-9c786cf919bc', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.45.05_[2022.02.04_19.02.11].png'),
        ('47ba920d-7472-4c75-9136-9c786cf919bc', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.14.36_[2022.02.04_19.00.34].png'),
        ('47ba920d-7472-4c75-9136-9c786cf919bc', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.33_[2022.02.04_19.00.07].png'),
        ('61853674-7ca6-4689-bfb3-a00965642c3c', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.33_[2022.02.04_19.00.07].png'),
        ('61853674-7ca6-4689-bfb3-a00965642c3c', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.14.36_[2022.02.04_19.00.34].png'),
        ('61853674-7ca6-4689-bfb3-a00965642c3c', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.50.32_[2022.02.04_19.02.50].png'),
        ('c811089c-2a92-4833-87c5-2f813739b520', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.26_[2022.02.04_18.59.51].png'),
        ('c811089c-2a92-4833-87c5-2f813739b520', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.45.05_[2022.02.04_19.02.11].png'),
        ('c811089c-2a92-4833-87c5-2f813739b520', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.14.36_[2022.02.04_19.00.34].png'),
        ('bb50e82a-63dd-4fd0-8bfd-be5355bd8acc', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.45.05_[2022.02.04_19.02.11].png'),
        ('bb50e82a-63dd-4fd0-8bfd-be5355bd8acc', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.14.36_[2022.02.04_19.00.34].png'),
        ('bb50e82a-63dd-4fd0-8bfd-be5355bd8acc', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.33_[2022.02.04_19.00.07].png'),
        ('905d2f8a-6adc-42de-812d-4fcab61af3c3', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.33_[2022.02.04_19.00.07].png'),
        ('905d2f8a-6adc-42de-812d-4fcab61af3c3', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.14.36_[2022.02.04_19.00.34].png'),
        ('905d2f8a-6adc-42de-812d-4fcab61af3c3', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.45.05_[2022.02.04_19.02.11].png'),
        ('2080fffa-c3c7-4354-b4c5-0f85fe60f045', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.14.36_[2022.02.04_19.00.34].png'),
        ('2080fffa-c3c7-4354-b4c5-0f85fe60f045', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.45.05_[2022.02.04_19.02.11].png'),
        ('2080fffa-c3c7-4354-b4c5-0f85fe60f045', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.33_[2022.02.04_19.00.07].png'),
        ('0f915b72-0cf7-437c-a8ee-4aa0e629ce02', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.50.32_[2022.02.04_19.02.50].png'),
        ('0f915b72-0cf7-437c-a8ee-4aa0e629ce02', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.45.05_[2022.02.04_19.02.11].png'),
        ('0f915b72-0cf7-437c-a8ee-4aa0e629ce02', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.14.36_[2022.02.04_19.00.34].png'),
        ('0a3b8f09-eab1-45c9-a955-1cb484ead7c7', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.26_[2022.02.04_18.59.51].png'),
        ('0a3b8f09-eab1-45c9-a955-1cb484ead7c7', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.50.32_[2022.02.04_19.02.50].png'),
        ('0a3b8f09-eab1-45c9-a955-1cb484ead7c7', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.33_[2022.02.04_19.00.07].png'),
        ('fa34d21b-3451-4d62-9bf4-a4a61237b786', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.50.32_[2022.02.04_19.02.50].png'),
        ('fa34d21b-3451-4d62-9bf4-a4a61237b786', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.14.36_[2022.02.04_19.00.34].png'),
        ('fa34d21b-3451-4d62-9bf4-a4a61237b786', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_00.03.56_[2022.02.04_18.58.01].png'),
        ('52102dde-95f7-4d6c-b1c0-5b5f7803e035', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_00.03.56_[2022.02.04_18.58.01].png'),
        ('52102dde-95f7-4d6c-b1c0-5b5f7803e035', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.33_[2022.02.04_19.00.07].png'),
        ('52102dde-95f7-4d6c-b1c0-5b5f7803e035', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.50.32_[2022.02.04_19.02.50].png'),
        ('8856f132-0cb2-437a-b001-beb5f8628185', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_00.03.56_[2022.02.04_18.58.01].png'),
        ('8856f132-0cb2-437a-b001-beb5f8628185', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.26_[2022.02.04_18.59.51].png'),
        ('8856f132-0cb2-437a-b001-beb5f8628185', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.50.32_[2022.02.04_19.02.50].png'),
        ('8a768eee-20bd-428a-b692-0b0c7a998430', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.14.36_[2022.02.04_19.00.34].png'),
        ('8a768eee-20bd-428a-b692-0b0c7a998430', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.33_[2022.02.04_19.00.07].png'),
        ('8a768eee-20bd-428a-b692-0b0c7a998430', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.50.32_[2022.02.04_19.02.50].png'),
        ('aabea344-53f8-41b5-8312-e0b52d28e67c', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_00.03.56_[2022.02.04_18.58.01].png'),
        ('aabea344-53f8-41b5-8312-e0b52d28e67c', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.26_[2022.02.04_18.59.51].png'),
        ('aabea344-53f8-41b5-8312-e0b52d28e67c', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.33_[2022.02.04_19.00.07].png'),
        ('90e53101-0afe-4411-8e46-313d80787a0e', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.50.32_[2022.02.04_19.02.50].png'),
        ('90e53101-0afe-4411-8e46-313d80787a0e', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_00.03.56_[2022.02.04_18.58.01].png'),
        ('90e53101-0afe-4411-8e46-313d80787a0e', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.33_[2022.02.04_19.00.07].png'),
        ('187815ee-d470-4ddc-8c6a-89a87c357e10', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.14.36_[2022.02.04_19.00.34].png'),
        ('187815ee-d470-4ddc-8c6a-89a87c357e10', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_00.03.56_[2022.02.04_18.58.01].png'),
        ('187815ee-d470-4ddc-8c6a-89a87c357e10', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.50.32_[2022.02.04_19.02.50].png'),
        ('676f57d7-4d44-4dd6-9428-12bd5d306b0e', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.45.05_[2022.02.04_19.02.11].png'),
        ('676f57d7-4d44-4dd6-9428-12bd5d306b0e', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.50.32_[2022.02.04_19.02.50].png'),
        ('676f57d7-4d44-4dd6-9428-12bd5d306b0e', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.14.36_[2022.02.04_19.00.34].png'),
        ('ffdb4322-f424-46ae-95d5-d26f11ff6ce1', 0, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.06.26_[2022.02.04_18.59.51].png'),
        ('ffdb4322-f424-46ae-95d5-d26f11ff6ce1', 1, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_00.03.56_[2022.02.04_18.58.01].png'),
        ('ffdb4322-f424-46ae-95d5-d26f11ff6ce1', 2, null, 'http://192.168.1.251:9000/roomx/giau/equipment/Ao-Natsu.Kimi.Ni.Koi.Shita.30-Nichi.2018.1080p.BluRay.x264.AAC5.1-[YTS.MX].mp4_snapshot_01.14.36_[2022.02.04_19.00.34].png')
ON CONFLICT (entity_id, image_order) DO NOTHING;









