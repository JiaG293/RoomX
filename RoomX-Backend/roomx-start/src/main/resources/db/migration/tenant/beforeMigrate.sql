INSERT INTO role (role_id, description, level)
VALUES ('USER', 'Default role for employees', 1),
       ('OWNER', 'Role for owners with full access', 4),
       ('ADMIN', 'Administrator role with system-wide privileges', 3),
       ('SUPPORTER', 'Support personnel role with limited access', 2),
       ('APPROVER', 'Role for approvers with approval permissions', 2)
ON CONFLICT (role_id) DO NOTHING;

-- INSERT INTO permisson (permission_id, description)
-- VALUES ('VIEW-USER', 'Allows viewing user information'),
--        ('MANAGER-USER', 'Allows managing user accounts'),
--        ('UPDATE-USER', 'Allows updating user profiles'),
--        ('CREATE-USER', 'Allows creating new user accounts')
-- ON CONFLICT (permission_id) DO NOTHING;


-- INSERT INTO role_permission (role_id, permission_id)
-- VALUES ('ADMIN', 'VIEW-USER'),
--        ('ADMIN', 'MANAGER-USER'),
--        ('ADMIN', 'UPDATE-USER'),
--        ('ADMIN', 'CREATE-USER')
-- ON CONFLICT (role_id, permission_id) DO NOTHING;

-- DATA
insert into "user" (first_name, last_name, phone_number, email, gender, avatar_image, user_type, user_code, user_id, created_at, updated_at)
values  ('nguyen van', 'admin', '1111111111', 'asgy2002@gmail.com', true, null, 'EMPLOYEE', '20053331', '374e33ed-1d51-4298-a3be-b51b4d7529a3', '2025-03-10 11:24:31.000000', '2025-03-10 11:24:33.000000'),
        ('tran van', 'manager', '1111111112', '821377326.jiag@gmail.com', true, null, 'EMPLOYEE', '20053332', 'f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', '2025-03-10 11:24:48.000000', '2025-03-10 11:24:47.000000'),
        ('huynh van', 'user', '1111111113', 'user001.roomx@gmail.com', true, null, 'EMPLOYEE', '20053333', 'ea4e9c4c-a317-4064-8a5b-da2b338e4180', '2025-03-10 11:26:25.000000', '2025-03-10 11:26:26.000000')
ON CONFLICT (user_id) DO NOTHING;

insert into user_role(user_id, role_id)
values ('374e33ed-1d51-4298-a3be-b51b4d7529a3', 'ADMIN'),
       ('374e33ed-1d51-4298-a3be-b51b4d7529a3', 'APPROVER'),
       ('374e33ed-1d51-4298-a3be-b51b4d7529a3', 'SUPPORTER'),
       ('374e33ed-1d51-4298-a3be-b51b4d7529a3', 'USER'),
       ('f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', 'APPROVER'),
       ('f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', 'USER'),
       ('ea4e9c4c-a317-4064-8a5b-da2b338e4180', 'USER')
ON CONFLICT (user_id, role_id) DO NOTHING;