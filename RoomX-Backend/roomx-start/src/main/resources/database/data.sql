-- drop table if exists schema_roomx.account_roles cascade;
--
-- drop table if exists schema_roomx.accounts cascade;
--
-- drop table if exists schema_roomx.role_permissions cascade;
--
-- drop table if exists schema_roomx.permissions cascade;
--
-- drop table if exists schema_roomx.roles cascade;


CREATE SCHEMA IF NOT EXISTS schema_roomx;
SET
search_path TO schema_roomx;

-- TABLE: ROLES --
INSERT INTO schema_roomx.roles (role_id, description)
VALUES ('ADMIN', 'Quản trị viên hệ thống'),
       ('SUPPORT', 'Người hỗ trợ sự cố khắc phục hệ thống'),
       ('NAMESPACE_ADMIN', 'Người dùng với vai trò là quản trị viên một namespace'),
       ('NAMESPACE_ADMIN_APPROVE', 'Người dùng với vai trò là kiểm duyệt viên'),
       ('NAMESPACE_ADMIN_SUPPORT', 'Người dùng với vai trò là nhân viên hỗ trợ kĩ thuật, vệ sinh'),
       ('NAMESPACE_EMPLOYEE', 'Người dùng với vai trò là nhân viên');


-- TABLE: PERMISSIONS --
INSERT INTO schema_roomx.permissions (permission_id, description)
VALUES ('CREATE', 'Quản trị hệ thống'),
       ('UPDATE', 'Quản trị hệ thống'),
       ('DELETE', 'Quản trị hệ thống'),
       ('READ', 'Quản trị hệ thống'),
       ('NAMESPACE_CREATE', 'Quản trị namespace'),
       ('NAMESPACE_UPDATE', 'Quản trị namespace'),
       ('NAMESPACE_DELETE', 'Quản trị namespace'),
       ('NAMESPACE_READ', 'Quản trị namespace');


-- TABLE: ACCOUNTS --

-- role: ADMIN, SUPPORT
-- password: admin
INSERT INTO schema_roomx.accounts (account_id, email, password, is_active, created_at, updated_at)
VALUES ('00000000', 'asgy2002@gmail.com', '$2a$12$DWh.yec/1To4yifNQ6aTk.sVuun9T1p9EMYFiGMnfEP9qLCiZ.Eye', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('00000001', 'test@gmail.com', '$2a$12$DWh.yec/1To4yifNQ6aTk.sVuun9T1p9EMYFiGMnfEP9qLCiZ.Eye', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO schema_roomx.account_roles(role_id, account_id)
VALUES ('ADMIN', '00000000'),
    ('SUPPORT', '00000001');



-- role: NAMESPACE_ADMIN
-- password: 123
INSERT INTO schema_roomx.accounts (account_id, email, password, is_active, created_at, updated_at)
VALUES ('10000000', 'namespace.admin@gmail.com', '$2a$12$etWL69IwBH78b0hEn/ZU1u8E8ucPbmQ3EghEOAF5r4jtbJdY97FUu', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO schema_roomx.account_roles(role_id, account_id)
VALUES ('NAMESPACE_ADMIN', '10000000');

-- role: NAMESPACE_ADMIN_APPROVE
-- password: 123
INSERT INTO schema_roomx.accounts (account_id, email, password, is_active, created_at, updated_at)
VALUES ('11000000', 'namespace.admin.approve.0@gmail.com', '$2a$12$etWL69IwBH78b0hEn/ZU1u8E8ucPbmQ3EghEOAF5r4jtbJdY97FUu', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO schema_roomx.account_roles(role_id, account_id)
VALUES ('NAMESPACE_ADMIN_APPROVE', '11000000');


-- role: NAMESPACE_ADMIN_SUPPORT
-- password: 123
INSERT INTO schema_roomx.accounts (account_id, email, password, is_active, created_at, updated_at)
VALUES ('12000000', 'namespace.admin.support.0@gmail.com', '$2a$12$etWL69IwBH78b0hEn/ZU1u8E8ucPbmQ3EghEOAF5r4jtbJdY97FUu', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('12000001', 'namespace.admin.support.1@gmail.com', '$2a$12$etWL69IwBH78b0hEn/ZU1u8E8ucPbmQ3EghEOAF5r4jtbJdY97FUu', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('12000002', 'namespace.admin.support.2@gmail.com', '$2a$12$etWL69IwBH78b0hEn/ZU1u8E8ucPbmQ3EghEOAF5r4jtbJdY97FUu', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('12000003', 'namespace.admin.support.3@gmail.com', '$2a$12$etWL69IwBH78b0hEn/ZU1u8E8ucPbmQ3EghEOAF5r4jtbJdY97FUu', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('12000004', 'namespace.admin.support.4@gmail.com', '$2a$12$etWL69IwBH78b0hEn/ZU1u8E8ucPbmQ3EghEOAF5r4jtbJdY97FUu', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO schema_roomx.account_roles(role_id, account_id)
VALUES ('NAMESPACE_ADMIN_SUPPORT', '12000000'),
       ('NAMESPACE_ADMIN_SUPPORT', '12000001'),
       ('NAMESPACE_ADMIN_SUPPORT', '12000002'),
       ('NAMESPACE_ADMIN_SUPPORT', '12000003'),
       ('NAMESPACE_ADMIN_SUPPORT', '12000004');

-- role: NAMESPACE_EMPLOYEE
-- password: 123
INSERT INTO schema_roomx.accounts (account_id, email, password, is_active, created_at, updated_at)
VALUES ('13000000', 'namespace.employee.0@gmail.com', '$2a$12$etWL69IwBH78b0hEn/ZU1u8E8ucPbmQ3EghEOAF5r4jtbJdY97FUu', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('13000001', 'namespace.employee.1@gmail.com', '$2a$12$etWL69IwBH78b0hEn/ZU1u8E8ucPbmQ3EghEOAF5r4jtbJdY97FUu', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('13000002', 'namespace.employee.2@gmail.com', '$2a$12$etWL69IwBH78b0hEn/ZU1u8E8ucPbmQ3EghEOAF5r4jtbJdY97FUu', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('13000003', 'namespace.employee.3@gmail.com', '$2a$12$etWL69IwBH78b0hEn/ZU1u8E8ucPbmQ3EghEOAF5r4jtbJdY97FUu', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('13000004', 'namespace.employee.4@gmail.com', '$2a$12$etWL69IwBH78b0hEn/ZU1u8E8ucPbmQ3EghEOAF5r4jtbJdY97FUu', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('13000005', 'namespace.employee.5@gmail.com', '$2a$12$etWL69IwBH78b0hEn/ZU1u8E8ucPbmQ3EghEOAF5r4jtbJdY97FUu', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO schema_roomx.account_roles(role_id, account_id)
VALUES ('NAMESPACE_EMPLOYEE', '13000000'),
       ('NAMESPACE_EMPLOYEE', '13000001'),
       ('NAMESPACE_EMPLOYEE', '13000002'),
       ('NAMESPACE_EMPLOYEE', '13000003'),
       ('NAMESPACE_EMPLOYEE', '13000004'),
       ('NAMESPACE_EMPLOYEE', '13000005');


INSERT INTO schema_roomx.role_permissions (role_id, permission_id)
VALUES ('ADMIN', 'CREATE'),
       ('ADMIN', 'UPDATE'),
       ('ADMIN', 'DELETE'),
       ('ADMIN', 'READ'),
       ('SUPPORT', 'CREATE'),
       ('SUPPORT', 'UPDATE'),
       ('SUPPORT', 'DELETE'),
       ('SUPPORT', 'READ'),
       ('NAMESPACE_ADMIN', 'NAMESPACE_CREATE'),
       ('NAMESPACE_ADMIN', 'NAMESPACE_UPDATE'),
       ('NAMESPACE_ADMIN', 'NAMESPACE_DELETE'),
       ('NAMESPACE_ADMIN', 'NAMESPACE_READ'),
       ('NAMESPACE_ADMIN_APPROVE', 'NAMESPACE_CREATE'),
       ('NAMESPACE_ADMIN_APPROVE', 'NAMESPACE_UPDATE'),
       ('NAMESPACE_ADMIN_APPROVE', 'NAMESPACE_DELETE'),
       ('NAMESPACE_ADMIN_APPROVE', 'NAMESPACE_READ'),
       ('NAMESPACE_ADMIN_SUPPORT', 'NAMESPACE_CREATE'),
       ('NAMESPACE_ADMIN_SUPPORT', 'NAMESPACE_UPDATE'),
       ('NAMESPACE_ADMIN_SUPPORT', 'NAMESPACE_DELETE'),
       ('NAMESPACE_ADMIN_SUPPORT', 'NAMESPACE_READ'),
       ('NAMESPACE_EMPLOYEE', 'NAMESPACE_CREATE'),
       ('NAMESPACE_EMPLOYEE', 'NAMESPACE_UPDATE'),
       ('NAMESPACE_EMPLOYEE', 'NAMESPACE_READ');

