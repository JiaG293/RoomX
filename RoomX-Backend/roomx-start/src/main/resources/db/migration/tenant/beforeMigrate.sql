INSERT INTO role (role_id, description)
VALUES ('EMPLOYEE', 'Default role for employees'),
       ('OWNER', 'Role for owners with full access'),
       ('ADMIN', 'Administrator role with system-wide privileges'),
       ('SUPPORTER', 'Support personnel role with limited access'),
       ('APPROVER', 'Role for approvers with approval permissions')
ON CONFLICT (role_id) DO NOTHING;

INSERT INTO permisson (permission_id, description)
VALUES ('VIEW-USER', 'Allows viewing user information'),
       ('MANAGER-USER', 'Allows managing user accounts'),
       ('UPDATE-USER', 'Allows updating user profiles'),
       ('CREATE-USER', 'Allows creating new user accounts')
ON CONFLICT (permission_id) DO NOTHING;


INSERT INTO role_permission (role_id, permission_id)
VALUES ('ADMIN', 'VIEW-USER'),
       ('ADMIN', 'MANAGER-USER'),
       ('ADMIN', 'UPDATE-USER'),
       ('ADMIN', 'CREATE-USER')
ON CONFLICT (role_id, permission_id) DO NOTHING;
