
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
INSERT INTO "user" (first_name, last_name, phone_number, email, gender, avatar_image, user_type, user_code, user_id, created_at, updated_at)
VALUES  ('nguyen van', 'admin', '1111111111', 'asgy2002@gmail.com', true, null, 'EMPLOYEE', '20053331', '374e33ed-1d51-4298-a3be-b51b4d7529a3', '2025-03-10 11:24:31.000000', '2025-03-10 11:24:33.000000'),
        ('tran van', 'manager', '1111111112', '821377326.jiag@gmail.com', true, null, 'EMPLOYEE', '20053332', 'f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', '2025-03-10 11:24:48.000000', '2025-03-10 11:24:47.000000'),
        ('huynh van', 'user', '1111111113', 'user001.roomx@gmail.com', true, null, 'EMPLOYEE', '20053333', 'ea4e9c4c-a317-4064-8a5b-da2b338e4180', '2025-03-10 11:26:25.000000', '2025-03-10 11:26:26.000000')
ON CONFLICT (user_id) DO NOTHING;

INSERT INTO  user_role(user_id, role_id)
VALUES ('374e33ed-1d51-4298-a3be-b51b4d7529a3', 'ADMIN'),
       ('374e33ed-1d51-4298-a3be-b51b4d7529a3', 'APPROVER'),
       ('374e33ed-1d51-4298-a3be-b51b4d7529a3', 'SUPPORTER'),
       ('374e33ed-1d51-4298-a3be-b51b4d7529a3', 'USER'),
       ('f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', 'APPROVER'),
       ('f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', 'USER'),
       ('ea4e9c4c-a317-4064-8a5b-da2b338e4180', 'USER')
ON CONFLICT (user_id, role_id) DO NOTHING;


-- BRANCH
INSERT INTO branch (branch_id, name, phone_number, email, address, branch_code, status)
VALUES
    ('e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Chi nhánh A', '0901234567', 'branch1@example.com', '123 Đường ABC, Quận 1', 'BN001', 'ACTIVE'),
    ('d8d06462-63ff-4264-bf34-e40cb77fd263', 'Chi nhánh B', '0912345679', 'branch2@example.com', '456 Đường MNK, Quận 2', 'BN002', 'ACTIVE'),
    ('985a287b-a742-4c1e-84fd-d4429ada7efa', 'Chi nhánh C', '0912345678', 'branch3@example.com', '789 Đường XYZ, Quận 3', 'BN003', 'INACTIVE')
ON CONFLICT (branch_id) DO NOTHING;


-- PLACE
INSERT INTO place (place_id, branch_id, name, layout, place_type, parent_id, code, status)
VALUES  ('65f1d8a0-6885-4b51-a691-f843b279dd8b', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Chi nhánh A', 'URL', 'BRANCH', null, 'BN001', 'ACTIVE'),
        ('0b83c73a-afe0-4bc5-ade7-f5c8e816c741', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Chi nhánh B', 'URL', 'BRANCH', null, 'BN002', 'ACTIVE'),
        ('f1d12862-8782-4cea-b0da-22e0b2579c46', '985a287b-a742-4c1e-84fd-d4429ada7efa', 'Chi nhánh C', 'URL', 'BRANCH', null, 'BN003', 'ACTIVE'),
        ('bbadcf08-52de-4914-9c1e-9abea4f66520', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tòa A25', 'lmao', 'BUILDING', '65f1d8a0-6885-4b51-a691-f843b279dd8b', 'A25', 'ACTIVE'),
        ('a84e8c68-7005-4889-be77-039b722e25e7', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tầng 1', 'url sơ đồ 1', 'FLOOR', 'bbadcf08-52de-4914-9c1e-9abea4f66520', '1', 'ACTIVE'),
        ('0170373d-c356-4ab3-87eb-8b2c85283d14', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tầng 3', 'url 3', 'FLOOR', 'bbadcf08-52de-4914-9c1e-9abea4f66520', '3', 'ACTIVE'),
        ('2f790170-d3b3-4182-8906-278bccdff2f1', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tầng 4', null, 'FLOOR', 'bbadcf08-52de-4914-9c1e-9abea4f66520', '4', 'ACTIVE'),
        ('52f181c4-d56b-4d3a-8a37-63ac892be3f2', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tòa A26', 'lmao', 'BUILDING', '65f1d8a0-6885-4b51-a691-f843b279dd8b', 'A26', 'ACTIVE'),
        ('c74a844e-36ec-4b46-9926-03214929fe45', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tầng 1', 'url sơ đồ 1', 'FLOOR', '52f181c4-d56b-4d3a-8a37-63ac892be3f2', '1', 'ACTIVE'),
        ('1e2c0d33-20c5-4c13-974b-2248fc529189', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tầng 3', 'url 3', 'FLOOR', '52f181c4-d56b-4d3a-8a37-63ac892be3f2', '3', 'ACTIVE'),
        ('adc6feab-8c23-497f-b19f-77341b545aac', 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89', 'Tầng 4', null, 'FLOOR', '52f181c4-d56b-4d3a-8a37-63ac892be3f2', '4', 'ACTIVE'),
        ('ef88da1f-cc5f-4cae-a2a1-4a7e2f2160da', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tòa C22', 'lmao', 'BUILDING', '0b83c73a-afe0-4bc5-ade7-f5c8e816c741', 'C22', 'ACTIVE'),
        ('f32615d8-dd19-4946-ba77-f489e4e49b62', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tầng 1', 'url sơ đồ 1', 'FLOOR', 'ef88da1f-cc5f-4cae-a2a1-4a7e2f2160da', '1', 'ACTIVE'),
        ('8bb83093-79f3-402f-83a2-0408f0c08f59', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tầng 3', 'url 3', 'FLOOR', 'ef88da1f-cc5f-4cae-a2a1-4a7e2f2160da', '3', 'ACTIVE'),
        ('10e6e9a2-400a-4636-b139-e609f2d69bce', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tầng 4', null, 'FLOOR', 'ef88da1f-cc5f-4cae-a2a1-4a7e2f2160da', '4', 'ACTIVE'),
        ('01a4f3c2-76d0-4e79-9685-621ed3077beb', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tòa C29', 'lmao', 'BUILDING', '0b83c73a-afe0-4bc5-ade7-f5c8e816c741', 'C29', 'ACTIVE'),
        ('ef00a2a3-9438-46f1-87a3-318de786de5b', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tầng 1', 'url sơ đồ 1', 'FLOOR', '01a4f3c2-76d0-4e79-9685-621ed3077beb', '1', 'ACTIVE'),
        ('dce5ecf4-05dc-49a6-8f7d-5a144653c6d1', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tầng 3', 'url 3', 'FLOOR', '01a4f3c2-76d0-4e79-9685-621ed3077beb', '3', 'ACTIVE'),
        ('b154ec93-baef-4663-b2bf-65f2edb7c111', 'd8d06462-63ff-4264-bf34-e40cb77fd263', 'Tầng 4', null, 'FLOOR', '01a4f3c2-76d0-4e79-9685-621ed3077beb', '4', 'ACTIVE')
ON CONFLICT (place_id) DO NOTHING;















