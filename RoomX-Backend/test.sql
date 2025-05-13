-- BOOKING REQUEST
INSERT INTO booking_request (booking_request_id, requester, priority, created_at, updated_at, end_date_approval,
                             days_of_week, end_time, start_time, end_date, start_date,
                             recurrence_interval, recurrence_type, capacity, branch_id, room_id)
VALUES
    -- BOOKING REQUEST NEW
    -- DAILY
    ('11111111-1111-1111-1111-111111111111', '374e33ed-1d51-4298-a3be-b51b4d7529a3', 0, NOW(), NOW(),
     NOW() + INTERVAL '1 day',
     'MO,TU,WE,TH,FR', '09:00', '08:00', CURRENT_DATE + INTERVAL '30 day', CURRENT_DATE, NULL, 'DAILY', 10, NULL, NULL),
    ('11111111-1111-1111-1111-111111111112', '374e33ed-1d51-4298-a3be-b51b4d7529a3', 0, NOW(), NOW(),
     NOW() + INTERVAL '1 day',
     'MO,TU,WE,TH,FR', '16:00', '17:00', CURRENT_DATE + INTERVAL '30 day', CURRENT_DATE, NULL, 'DAILY', 12, NULL, NULL),
    -- WEEKLY
    ('11111111-1111-1111-1111-111111111113', '374e33ed-1d51-4298-a3be-b51b4d7529a3', 0, NOW(), NOW(),
     NOW() + INTERVAL '1 day',
     'MO,TU,WE,TH,FR', '15:00', '14:00', CURRENT_DATE + INTERVAL '90 day', CURRENT_DATE, NULL, 'WEEKLY', 8, NULL, NULL),
    ('11111111-1111-1111-1111-111111111114', '374e33ed-1d51-4298-a3be-b51b4d7529a3', 0, NOW(), NOW(),
     NOW() + INTERVAL '1 day',
     'MO,TU,WE,TH,FR', '16:00', '15:00', CURRENT_DATE + INTERVAL '120 day', CURRENT_DATE + INTERVAL '5 day', NULL,
     'WEEKLY', 6, NULL, NULL),
    -- MONTHLY
    ('11111111-1111-1111-1111-111111111115', '374e33ed-1d51-4298-a3be-b51b4d7529a3', 0, NOW(), NOW(),
     NOW() + INTERVAL '1 day',
     'SA', '13:00', '14:00', CURRENT_DATE + INTERVAL '120 day', CURRENT_DATE + INTERVAL '1 day', NULL, 'MONTHLY', 56,
     NULL, NULL),
    ('11111111-1111-1111-1111-111111111116', '374e33ed-1d51-4298-a3be-b51b4d7529a3', 0, NOW(), NOW(),
     NOW() + INTERVAL '1 day',
     'SA', '07:00', '11:00', CURRENT_DATE + INTERVAL '200 day', CURRENT_DATE + INTERVAL '2 day', NULL, 'MONTHLY', 20,
     NULL, NULL),
    -- CUSTOM
    ('11111111-1111-1111-1111-111111111117', '374e33ed-1d51-4298-a3be-b51b4d7529a3', 0, NOW(), NOW(),
     NOW() + INTERVAL '1 day',
     'SA', '20:00', '21:00', CURRENT_DATE + INTERVAL '100 day', CURRENT_DATE + INTERVAL '2 day', 14, 'CUSTOM', 25, NULL,
     NULL),

    -- BOOKING REQUEST OLD
    ('11111111-1111-1111-1111-111111111110', '374e33ed-1d51-4298-a3be-b51b4d7529a3', 0, NOW() - INTERVAL '100 day',
     NOW() - INTERVAL '100 day', NOW() - INTERVAL '99 day',
     'MO,TU,WE,TH,FR,SA,SU', '08:00', '09:00', CURRENT_DATE - INTERVAL '10 day', NOW() - INTERVAL '20 day', 1, 'CUSTOM',
     45, NULL, NULL);

-- BOOKING REQUEST PARTICIPANT
INSERT INTO booking_request_participant (booking_request_id, participants)
VALUES ('11111111-1111-1111-1111-111111111110', 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'),
       ('11111111-1111-1111-1111-111111111110', '374e33ed-1d51-4298-a3be-b51b4d7529a3'),
       ('11111111-1111-1111-1111-111111111110', 'ea4e9c4c-a317-4064-8a5b-da2b339e4182');

-- APPROVAL FORM
INSERT INTO approval_form (approval_form_id, booking_request_id, approver, status, created_at, updated_at)
VALUES ('55555555-5555-5555-5555-555555555550', '11111111-1111-1111-1111-111111111111',
        'f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', 'PENDING', NOW(), NOW()),
       ('55555555-5555-5555-5555-555555555551', '11111111-1111-1111-1111-111111111112',
        'f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', 'PENDING', NOW(), NOW()),
       ('55555555-5555-5555-5555-555555555552', '11111111-1111-1111-1111-111111111113',
        'f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', 'PENDING', NOW(), NOW()),
       ('55555555-5555-5555-5555-555555555553', '11111111-1111-1111-1111-111111111114',
        'f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', 'PENDING', NOW(), NOW()),
       ('55555555-5555-5555-5555-555555555554', '11111111-1111-1111-1111-111111111115',
        'f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', 'PENDING', NOW(), NOW()),
       ('55555555-5555-5555-5555-555555555555', '11111111-1111-1111-1111-111111111116',
        'f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', 'PENDING', NOW(), NOW()),

       ('55555555-5555-5555-5555-555555555556', '11111111-1111-1111-1111-111111111117',
        'f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', 'PENDING', NOW(), NOW()),
       ('55555555-5555-5555-5555-555555555557', '11111111-1111-1111-1111-111111111117',
        'f4a6a4ba-ffb4-4ce5-8125-97be4fa7cd91', 'APPROVED', NOW(), NOW());


--BOOKING
INSERT INTO booking (booking_id, booking_code, booking_request_id, room_id, meeting_start, meeting_end,
                     "count", total_price, status, created_at, updated_at, previous_room_id, meeting_date)
VALUES ('22222222-2222-2222-2222-222222222229', 'BK_20241217_00001', '11111111-1111-1111-1111-111111111110',
        'ffe4ba6a-d0a2-41ab-b4f9-fef8a021e193',
        '08:00', '09:00',
        1, 100.00, 'COMPLETED', NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day', NULL,
        CURRENT_DATE + INTERVAL '1 day'),
       ('22222222-2222-2222-2222-222222222228', 'BK_20241216_00001', '11111111-1111-1111-1111-111111111110',
        'ffe4ba6a-d0a2-41ab-b4f9-fef8a021e193',
        '08:00', '09:00',
        2, 120.00, 'COMPLETED', NOW() - INTERVAL '2 day', NOW() - INTERVAL '2 day', NULL,
        CURRENT_DATE + INTERVAL '2 day'),
       ('22222222-2222-2222-2222-222222222227', 'BK_20241225_00001', '11111111-1111-1111-1111-111111111110',
        'ffe4ba6a-d0a2-41ab-b4f9-fef8a021e193',
        '08:00', '09:00',
        3, 120.00, 'COMPLETED', NOW() - INTERVAL '2 day', NOW() - INTERVAL '3 day', NULL,
        CURRENT_DATE + INTERVAL '3 day'),
       ('22222222-2222-2222-2222-222222222226', 'BK_20241224_00001', '11111111-1111-1111-1111-111111111110',
        'ffe4ba6a-d0a2-41ab-b4f9-fef8a021e193',
        '08:00', '09:00',
        4, 120.00, 'COMPLETED', NOW() - INTERVAL '2 day', NOW() - INTERVAL '4', NULL,
        CURRENT_DATE + INTERVAL '4 day'),
       ('22222222-2222-2222-2222-222222222225', 'BK_20241223_00001', '11111111-1111-1111-1111-111111111110',
        'ffe4ba6a-d0a2-41ab-b4f9-fef8a021e193',
        '08:00', '09:00',
        5, 120.00, 'COMPLETED', NOW() - INTERVAL '2 day', NOW() - INTERVAL '5 day', NULL,
        CURRENT_DATE + INTERVAL '5 day'),
       ('22222222-2222-2222-2222-222222222224', 'BK_20241222_00001', '11111111-1111-1111-1111-111111111110',
        'ffe4ba6a-d0a2-41ab-b4f9-fef8a021e193',
        '08:00', '09:00',
        6, 120.00, 'COMPLETED', NOW() - INTERVAL '2 day', NOW() - INTERVAL '6 day', NULL,
        CURRENT_DATE + INTERVAL '6 day'),
       ('22222222-2222-2222-2222-222222222223', 'BK_20241221_00001', '11111111-1111-1111-1111-111111111110',
        'ffe4ba6a-d0a2-41ab-b4f9-fef8a021e193',
        '08:00', '09:00',
        7, 120.00, 'COMPLETED', NOW() - INTERVAL '2 day', NOW() - INTERVAL '7 day', NULL,
        CURRENT_DATE + INTERVAL '7 day'),
       ('22222222-2222-2222-2222-222222222222', 'BK_20241220_00001', '11111111-1111-1111-1111-111111111110',
        'ffe4ba6a-d0a2-41ab-b4f9-fef8a021e193',
        '08:00', '09:00',
        8, 120.00, 'COMPLETED', NOW() - INTERVAL '2 day', NOW() - INTERVAL '8 day', NULL,
        CURRENT_DATE + INTERVAL '8 day'),
       ('22222222-2222-2222-2222-222222222221', 'BK_20241219_00001', '11111111-1111-1111-1111-111111111110',
        'ffe4ba6a-d0a2-41ab-b4f9-fef8a021e193',
        '08:00', '09:00',
        9, 120.00, 'COMPLETED', NOW() - INTERVAL '2 day', NOW() - INTERVAL '9 day', NULL,
        CURRENT_DATE + INTERVAL '9 day'),
       ('22222222-2222-2222-2222-222222222220', 'BK_20241218_00001', '11111111-1111-1111-1111-111111111110',
        'ffe4ba6a-d0a2-41ab-b4f9-fef8a021e193',
        '08:00', '09:00',
        10, 120.00, 'COMPLETED', NOW() - INTERVAL '2 day', NOW() - INTERVAL '10 day', NULL,
        CURRENT_DATE + INTERVAL '10 day');

-- BOOKING PARTICIPANT
INSERT INTO booking_participant (booking_id, user_id)
VALUES ('22222222-2222-2222-2222-222222222221', 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'),
       ('22222222-2222-2222-2222-222222222222', 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'),
       ('22222222-2222-2222-2222-222222222223', 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'),
       ('22222222-2222-2222-2222-222222222224', 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'),
       ('22222222-2222-2222-2222-222222222225', 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'),
       ('22222222-2222-2222-2222-222222222226', 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'),
       ('22222222-2222-2222-2222-222222222227', 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'),
       ('22222222-2222-2222-2222-222222222228', 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'),
       ('22222222-2222-2222-2222-222222222229', 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'),
       ('22222222-2222-2222-2222-222222222220', '374e33ed-1d51-4298-a3be-b51b4d7529a3'),
       ('22222222-2222-2222-2222-222222222221', '374e33ed-1d51-4298-a3be-b51b4d7529a3'),
       ('22222222-2222-2222-2222-222222222222', '374e33ed-1d51-4298-a3be-b51b4d7529a3'),
       ('22222222-2222-2222-2222-222222222223', '374e33ed-1d51-4298-a3be-b51b4d7529a3'),
       ('22222222-2222-2222-2222-222222222224', '374e33ed-1d51-4298-a3be-b51b4d7529a3'),
       ('22222222-2222-2222-2222-222222222225', '374e33ed-1d51-4298-a3be-b51b4d7529a3'),
       ('22222222-2222-2222-2222-222222222226', '374e33ed-1d51-4298-a3be-b51b4d7529a3'),
       ('22222222-2222-2222-2222-222222222227', '374e33ed-1d51-4298-a3be-b51b4d7529a3'),
       ('22222222-2222-2222-2222-222222222228', '374e33ed-1d51-4298-a3be-b51b4d7529a3'),
       ('22222222-2222-2222-2222-222222222229', '374e33ed-1d51-4298-a3be-b51b4d7529a3'),
       ('22222222-2222-2222-2222-222222222220', 'ea4e9c4c-a317-4064-8a5b-da2b339e4182'),
       ('22222222-2222-2222-2222-222222222221', 'ea4e9c4c-a317-4064-8a5b-da2b339e4182'),
       ('22222222-2222-2222-2222-222222222222', 'ea4e9c4c-a317-4064-8a5b-da2b339e4182'),
       ('22222222-2222-2222-2222-222222222223', 'ea4e9c4c-a317-4064-8a5b-da2b339e4182'),
       ('22222222-2222-2222-2222-222222222224', 'ea4e9c4c-a317-4064-8a5b-da2b339e4182'),
       ('22222222-2222-2222-2222-222222222225', 'ea4e9c4c-a317-4064-8a5b-da2b339e4182'),
       ('22222222-2222-2222-2222-222222222226', 'ea4e9c4c-a317-4064-8a5b-da2b339e4182'),
       ('22222222-2222-2222-2222-222222222227', 'ea4e9c4c-a317-4064-8a5b-da2b339e4182'),
       ('22222222-2222-2222-2222-222222222228', 'ea4e9c4c-a317-4064-8a5b-da2b339e4182'),
       ('22222222-2222-2222-2222-222222222229', 'ea4e9c4c-a317-4064-8a5b-da2b339e4182');



SELECT *
FROM booking_request br
         LEFT JOIN approval_form af ON br.booking_request_id = af.booking_request_id
WHERE br.booking_request_id = '3f861a36-da75-4960-a742-6d4d9daa1f35'
  AND af.status = 'PENDING'
ORDER BY af.updated_at DESC
LIMIT 1

SELECT br, af
FROM booking_request br
         JOIN approval_form af ON br.booking_request_id = af.booking_request_id
WHERE br.requester = '374e33ed-1d51-4298-a3be-b51b4d7529a3'
  AND af.status = 'PENDING'
ORDER BY af.updated_at DESC


SELECT u.user_id
FROM "user" u
WHERE u.email = 'user004.roomx@gmail.com'

SELECT r
FROM room r
         LEFT JOIN
     r.place
WHERE r.status = :status
  AND r.place.branch.id = :branchId */ select
                                            re1_0.room_id,
                                            re1_0.description,
                                            re1_0.place_id,
                                            p1_0.place_id,
                                            p1_0.branch_id,
                                            p1_0.code,
                                            p1_0.layout,
                                            p1_0.name,
                                            p1_0.parent_id,
                                            p1_0.place_type,
                                            p1_0.status,
                                            re1_0.room_class_id,
                                            re1_0.room_code,
                                            re1_0.status
from
    room re1_0
    left join
    place p1_0
on p1_0.place_id=re1_0.place_id
where
    re1_0.status=?
  and p1_0.branch_id=?


SELECT r
FROM room r
WHERE r.status = 'AVAILABLE'
  AND r.place_id = 'e2f404d9-29c3-44f9-9f4c-cdbf9c859a89'


SELECT *
FROM room
WHERE room_id = '063f9c0e-ab1b-4504-b162-c363421f6ac5';
SELECT *
FROM booking_request
WHERE booking_request_id = 'e91435a3-5c2b-44c5-8f4b-cda9a23868e6';

select null,
       re1_0.description,
       re1_0.place_id,
       re1_0.room_class_id,
       re1_0.room_code,
       re1_0.status
from room re1_0
where re1_0.room_id = '063f9c0e-ab1b-4504-b162-c363421f6ac5'


SELECT a.total_price
FROM room_class_price_history a
WHERE a.room_class_id = '78ea8005-45a2-498f-87e3-701b24fdbc6c'
  AND '2025-03-30'::timestamp >= a.valid_from
  AND ('2025-03-30'::timestamp <= a.valid_end OR a.valid_end IS NULL)
ORDER BY a.valid_from DESC
LIMIT 1;

SELECT a.total_price
FROM room_class_price_history a
         JOIN room r ON r.room_class_id = a.room_class_id
WHERE r.room_id = 'b0879eac-4830-470f-83bc-15d03f7876b1'
  AND '2025-03-30' >= a.valid_from
  AND ('2025-03-30' <= a.valid_end OR a.valid_end IS NULL)
ORDER BY a.valid_from DESC
LIMIT 1

CREATE EXTENSION IF NOT EXISTS unaccent;

SELECT s.*
FROM service s
WHERE unaccent(CONCAT(s.name, ' ', s.status, ' ', s.service_id)) ILIKE unaccent('%bao%');

SELECT b.*
FROM branch b
WHERE unaccent(CONCAT(b.name, ' ',
                      b.branch_code, ' ',
                      b.email, ' ',
                      b.address, ' '))
    ILIKE unaccent(CONCAT('%', 'nhanh', '%'))
  AND b.status = 'ACTIVE';



EXPLAIN ANALYZE
select count(*)
from equipment


select afe1_0.approval_form_id,
       afe1_0.approver,
       afe1_0.booking_request_id,
       afe1_0.created_at,
       afe1_0.note,
       afe1_0.status,
       afe1_0.updated_at
from approval_form afe1_0
         join
     booking_request br1_0
     on br1_0.booking_request_id = afe1_0.booking_request_id
where br1_0.booking_request_id = '5870c428-78cf-456e-a412-37e92aea48d3'
  and afe1_0.status = 'PENDING'
order by afe1_0.updated_at desc

select afe1_0.approval_form_id,
       afe1_0.approver,
       afe1_0.booking_request_id,
       afe1_0.created_at,
       afe1_0.note,
       afe1_0.status,
       afe1_0.updated_at
from approval_form afe1_0
         join
     booking_request br1_0
     on br1_0.booking_request_id = afe1_0.booking_request_id
where br1_0.booking_request_id = 'a2a7bde0-ff45-4939-9b34-350d3c739a99'
order by afe1_0.updated_at desc
LIMIT 1

SELECT br.booking_request_id,
       br.title,
       br.description,
       br.recurrence_type,
       br.start_date,
       br.end_date,
       br.start_time,
       br.end_time,
       br.days_of_week,
       br.recurrence_interval,
       br.capacity,
       br.priority,
       af.status                            AS approval_status,
       af.created_at,
       af.updated_at,

       r.room_id,
       CONCAT(bd.code, f.code, r.room_code) AS room_name,
       r.room_code                          as room_code,

       br.branch_id, -- Tham chiếu từ bảng booking_request
       b.name                               as branch_name,
       b.code                               as branch_code,

       bd.place_id,
       bd.name                              as building_name,
       bd.code                              as building_code,

       f.place_id,
       f.name                               as floor_name,
       f.code                               as floor_code

FROM approval_form af
         JOIN booking_request br ON br.booking_request_id = af.booking_request_id
         JOIN room r ON r.room_id = br.room_id
         LEFT JOIN place b ON b.place_id = br.branch_id AND b.place_type = 'BRANCH'
         LEFT JOIN place bd ON bd.place_id = r.place_id AND bd.place_type = 'BUILDING' AND
                               bd.parent_id = b.place_id -- Phân biệt với parent_id
         LEFT JOIN place f ON f.place_id = r.place_id AND f.place_type = 'FLOOR' AND
                              f.parent_id = bd.place_id -- Phân biệt với parent_id

WHERE af.status IN ('PENDING', 'CONFLICT')
  AND af.updated_at BETWEEN '2025-02-01 16:00:49.903806' AND '2025-09-01 16:00:49.903806'
ORDER BY af.updated_at DESC


SELECT br.booking_request_id,
       br.title,
       br.description,
       br.recurrence_type,
       br.start_date,
       br.end_date,
       br.start_time,
       br.end_time,
       br.days_of_week,
       br.recurrence_interval,
       br.capacity,
       br.priority,
       af.status                            AS approval_status,
       af.created_at,
       af.updated_at,

       r.room_id,
       CONCAT(bd.code, f.code, r.room_code) AS room_name,
       r.room_code                          as room_code,

       br.branch_id,
       b.name                               as branch_name,
       b.code                               as branch_code,

       bd.place_id,
       bd.name                              as building_name,
       bd.code                              as building_code,

       f.place_id,
       f.name                               as floor_name,
       f.code                               as floor_code

FROM approval_form af
         JOIN booking_request br ON br.booking_request_id = af.booking_request_id
         JOIN room r ON r.room_id = br.room_id
         LEFT JOIN place b ON b.place_id = br.branch_id AND b.place_type = 'BRANCH'
         LEFT JOIN place bd ON bd.place_id = r.place_id AND bd.place_type = 'BUILDING' AND bd.parent_id = b.place_id
         LEFT JOIN place f ON f.place_id = r.place_id AND f.place_type = 'FLOOR' AND f.parent_id = bd.place_id;



SELECT br.booking_request_id,
       br.title,
       br.description,
       br.recurrence_type,
       br.start_date,
       br.end_date,
       br.start_time,
       br.end_time,
       br.days_of_week,
       br.recurrence_interval,
       br.capacity,
       br.priority,
       af.status AS approval_status,
       af.created_at,
       af.updated_at

--     r.room_id
--     CONCAT(bd.code, f.code, r.room_code) AS room_name,
--     r.room_code as room_code,
--
--     br.branch_id,  -- Tham chiếu từ bảng booking_request
--     b.name as branch_name,
--     b.code as branch_code,
--
--     bd.place_id,
--     bd.name as building_name,
--     bd.code as building_code,
--
--     f.place_id,
--     f.name as floor_name,
--     f.code as floor_code

FROM approval_form af
         JOIN booking_request br ON br.booking_request_id = af.booking_request_id
--          JOIN room r ON r.room_id = br.room_id
--          LEFT JOIN place b ON b.place_id = br.branch_id AND b.place_type = 'BRANCH'
--          LEFT JOIN place bd ON bd.place_id = r.place_id AND bd.place_type = 'BUILDING' AND bd.parent_id = b.place_id  -- Phân biệt với parent_id
--          LEFT JOIN place f ON f.place_id = r.place_id AND f.place_type = 'FLOOR' AND f.parent_id = bd.place_id  -- Phân biệt với parent_id

WHERE af.status IN ('PENDING', 'CONFLICT')
  AND af.updated_at BETWEEN '2025-02-01 16:00:49.903806' AND '2025-09-01 16:00:49.903806'
\


SELECT b.*
FROM booking b
         JOIN booking_participant p ON b.booking_id = p.booking_id
         JOIN "user" u ON p.user_id = u.user_id
WHERE b.meeting_date

SELECT b.*
FROM booking b
         JOIN booking_participant p ON b.booking_id = p.booking_id
         JOIN "user" u ON p.user_id = u.user_id
WHERE b.meeting_date BETWEEN '2025-02-01 16:00:49.903806' AND '2025-09-01 16:00:49.903806'
  AND b.status IN ('SCHEDULED', 'IN_PROGRESS')
--   AND u.user_id = 'ea4e9c4c-a317-4064-8a5b-da2b339e4380';

SELECT p.user_id
FROM booking_participant p
         LEFT JOIN "user" u ON p.user_id = u.user_id
WHERE u.user_id IS NULL;

SELECT p.user_id
FROM booking_participant p
         LEFT JOIN "user" u ON p.user_id = u.user_id
WHERE u.user_id IS NULL;

SELECT *
FROM booking
WHERE meeting_date BETWEEN '2025-02-01 16:00:49.903806' AND '2025-09-01 16:00:49.903806';

SELECT *
FROM booking
WHERE status IN ('SCHEDULED', 'IN_PROGRESS');

SELECT b.booking_id, p.user_id, u.user_id
FROM booking_participant p
         JOIN "user" u ON p.user_id = u.user_id
         JOIN booking b ON p.booking_id = b.booking_id
WHERE b.meeting_date BETWEEN '2025-02-01 16:00:49.903806' AND '2025-09-01 16:00:49.903806';

SELECT b.*
FROM booking b
         JOIN booking_participant p ON b.booking_id = p.booking_id
         LEFT JOIN "user" u ON p.user_id = u.user_id
WHERE b.meeting_date BETWEEN '2025-02-01 16:00:49.903806' AND '2025-09-01 16:00:49.903806'
  AND b.status IN ('SCHEDULED', 'IN_PROGRESS');

SELECT b.*
FROM booking b
         JOIN booking_participant p ON b.booking_id = p.booking_id
         JOIN "user" u ON p.user_id = u.user_id
WHERE b.meeting_date BETWEEN '2025-02-01 16:00:49.903806' AND '2025-09-01 16:00:49.903806'
  AND u.user_id = 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'

SELECT b.booking_id    AS id,
       b.title,
       b.description,
       b.booking_code,
       r.room_id       AS room_id,
       p.place_id      AS branch,
       p.place_id      AS building,
       p.place_id      AS floor,
       pr.room_id      AS previous_room_id,
       b.meeting_start AS meeting_start,
       b.meeting_end   AS meeting_end,
       b.meeting_date  AS meeting_date,
       b.count,
       b.status,
       b.created_at,
       b.updated_at
FROM booking b
         JOIN
     booking_participant bp ON b.booking_id = bp.booking_id
         JOIN
     "user" u ON bp.user_id = u.user_id
         JOIN
     room r ON b.room_id = r.room_id
         JOIN
     place p ON r.place_id = p.place_id
         LEFT JOIN
     room pr ON b.previous_room_id = pr.room_id
WHERE b.meeting_date BETWEEN '2025-02-01 16:00:49.903806' AND '2025-09-01 16:00:49.903806'
  AND u.user_id = 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'



SELECT b.booking_id      AS id,
       b.title,
       b.description,
       b.booking_code,
       r.room_id         AS room_id,
       branch.place_id   AS branch_id,
       branch.name       AS branch_name,
       building.place_id AS building_id,
       building.name     AS building_name,
       floor.place_id    AS floor_id,
       floor.name        AS floor_name,
       pr.room_id        AS previous_room_id,
       b.meeting_start,
       b.meeting_end,
       b.meeting_date,
       b.count,
       b.status,
       b.created_at,
       b.updated_at
FROM booking b
         JOIN booking_participant bp ON b.booking_id = bp.booking_id
         JOIN "user" u ON bp.user_id = u.user_id
         JOIN room r ON b.room_id = r.room_id
         JOIN place branch ON r.place_id = branch.place_id AND branch.place_type = 'BRANCH'
         JOIN place building ON branch.parent_id = building.place_id AND building.place_type = 'BUILDING'
         JOIN place floor ON building.parent_id = floor.place_id AND floor.place_type = 'FLOOR'
         LEFT JOIN room pr ON b.previous_room_id = pr.room_id
WHERE b.meeting_date BETWEEN '2025-02-01' AND '2025-09-01'
--   AND u.user_id = 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'


SELECT b.booking_id    AS id,
       b.title,
       b.description,
       b.booking_code,
       r.room_id       AS room_id,
       br.place_id     AS branch,
       bl.place_id     AS building,
       fl.place_id     AS floor,
       pr.room_id      AS previous_room_id,
       b.meeting_start AS meeting_start,
       b.meeting_end   AS meeting_end,
       b.meeting_date  AS meeting_date,
       b.count,
       b.status,
       b.created_at,
       b.updated_at
FROM booking b
         JOIN
     booking_participant bp ON b.booking_id = bp.booking_id
         JOIN
     "user" u ON bp.user_id = u.user_id
         JOIN
     room r ON b.room_id = r.room_id
         JOIN
     place p ON r.place_id = p.place_id
         LEFT JOIN
     room pr ON b.previous_room_id = pr.room_id
         LEFT JOIN
     place br ON p.parent_id = br.place_id AND br.place_type = 'BRANCH'
         LEFT JOIN
     place bl ON p.parent_id = bl.place_id AND bl.place_type = 'BUILDING'
         LEFT JOIN
     place fl ON p.parent_id = fl.place_id AND fl.place_type = 'FLOOR'
WHERE b.meeting_date BETWEEN '2025-02-01 16:00:49.903806' AND '2025-09-01 16:00:49.903806'
  AND u.user_id = 'ea4e9c4c-a317-4064-8a5b-da2b339e4380';


SELECT b.booking_id    AS id,
       b.title,
       b.description,
       b.booking_code,
       r.room_id       AS room_id,
       br.place_id     AS branch,
       bl.place_id     AS building,
       p.place_id      AS floor,
       pr.room_id      AS previous_room_id,
       b.meeting_start AS meeting_start,
       b.meeting_end   AS meeting_end,
       b.meeting_date  AS meeting_date,
       b.count,
       b.status,
       b.created_at,
       b.updated_at
FROM booking b
         JOIN
     booking_participant bp ON b.booking_id = bp.booking_id
         JOIN
     "user" u ON bp.user_id = u.user_id
         JOIN
     room r ON b.room_id = r.room_id
         JOIN
     place p ON r.place_id = p.place_id -- Đây là place_id của floor
         LEFT JOIN
     room pr ON b.previous_room_id = pr.room_id
         -- Join để lấy thông tin branch và building từ parent_id của place
         LEFT JOIN
     place br ON p.parent_id = br.place_id AND br.place_type = 'BRANCH'
         LEFT JOIN
     place bl ON p.parent_id = bl.place_id AND bl.place_type = 'BUILDING'
WHERE b.meeting_date BETWEEN '2025-02-01 16:00:49.903806' AND '2025-09-01 16:00:49.903806'
  AND u.user_id = 'ea4e9c4c-a317-4064-8a5b-da2b339e4380';


SELECT b.booking_id    AS id,
       b.title,
       b.description,
       b.booking_code,
       r.room_id       AS room_id,
       p.place_id      AS floor,
       bl.place_id     AS building,
       br.place_id     AS branch,
       pr.room_id      AS previous_room_id,
       b.meeting_start AS meeting_start,
       b.meeting_end   AS meeting_end,
       b.meeting_date  AS meeting_date,
       b.count,
       b.status,
       b.created_at,
       b.updated_at
FROM booking b
         JOIN
     booking_participant bp ON b.booking_id = bp.booking_id
         JOIN
     "user" u ON bp.user_id = u.user_id
         JOIN
     room r ON b.room_id = r.room_id
         JOIN
     place p ON r.place_id = p.place_id -- Đây là place_id của floor
         LEFT JOIN
     room pr ON b.previous_room_id = pr.room_id
         -- Join để tìm Building từ Floor thông qua parent_id
         LEFT JOIN
     place bl ON p.parent_id = bl.place_id AND bl.place_type = 'BUILDING'
         -- Join để tìm Branch từ Building thông qua parent_id
         LEFT JOIN
     place br ON bl.parent_id = br.place_id AND br.place_type = 'BRANCH'
WHERE b.meeting_date BETWEEN '2025-02-01 16:00:49.903806' AND '2025-09-01 16:00:49.903806'
  AND u.user_id = 'ea4e9c4c-a317-4064-8a5b-da2b339e4380';



SELECT b.booking_id    AS id,
       b.title,
       b.description,
       b.booking_code,
       r.room_id       AS room_id,
       p.place_id      AS floor_id,
       p.name          AS floor_name,
       p.code          AS floor_code,
       bl.place_id     AS building_id,
       bl.name         AS building_name,
       bl.code         AS building_code,
       br.place_id     AS branch_id,
       br.name         AS branch_name,
       br.code         AS branch_code,
       pr.room_id      AS previous_room_id,
       b.meeting_start AS meeting_start,
       b.meeting_end   AS meeting_end,
       b.meeting_date  AS meeting_date,
       b.count,
       b.status,
       b.created_at,
       b.updated_at
FROM booking b
         JOIN
     booking_participant bp ON b.booking_id = bp.booking_id
         JOIN
     "user" u ON bp.user_id = u.user_id
         JOIN
     room r ON b.room_id = r.room_id
         JOIN
     place p ON r.place_id = p.place_id -- Đây là place_id của floor
         LEFT JOIN
     room pr ON b.previous_room_id = pr.room_id
         -- Join để tìm Building từ Floor thông qua parent_id
         LEFT JOIN
     place bl ON p.parent_id = bl.place_id AND bl.place_type = 'BUILDING'
         -- Join để tìm Branch từ Building thông qua parent_id
         LEFT JOIN
     place br ON bl.parent_id = br.place_id AND br.place_type = 'BRANCH'
WHERE b.meeting_date BETWEEN '2025-02-01' AND '2025-09-01'
  AND u.user_id = 'ea4e9c4c-a317-4064-8a5b-da2b339e4380';


SELECT *
FROM booking as b
         LEFT JOIN booking_participant bp on b.booking_id = bp.booking_id
WHERE bp.user_id = 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'
  AND b.meeting_date BETWEEN '2025-08-01' AND '2025-10-01'


SELECT b.booking_id    AS id,
       b.title,
       b.description,
       b.booking_code,
       r.room_id       AS room_id,
       r.room_code     AS room_code,
       r.room_code     AS room_name,
       p.place_id      AS floor_id,
       p.name          AS floor_name,
       p.code          AS floor_code,
       bl.place_id     AS building_id,
       bl.name         AS building_name,
       bl.code         AS building_code,
       br.place_id     AS branch_id,
       br.name         AS branch_name,
       br.code         AS branch_code,
       pr.room_id      AS room_previous_id,
       b.meeting_start AS meeting_start,
       b.meeting_end   AS meeting_end,
       b.meeting_date  AS meeting_date,
       b.count,
       b.status,
       b.created_at,
       b.updated_at
FROM booking b
         JOIN
     booking_participant bp ON b.booking_id = bp.booking_id
         JOIN
     "user" u ON bp.user_id = u.user_id
         JOIN
     room r ON b.room_id = r.room_id
         JOIN
     place p ON r.place_id = p.place_id -- Đây là place_id của floor
         LEFT JOIN
     room pr ON b.previous_room_id = pr.room_id
         -- Join để tìm Building từ Floor thông qua parent_id
         LEFT JOIN
     place bl ON p.parent_id = bl.place_id AND bl.place_type = 'BUILDING'
         -- Join để tìm Branch từ Building thông qua parent_id
         LEFT JOIN
     place br ON bl.parent_id = br.place_id AND br.place_type = 'BRANCH'
WHERE b.meeting_date BETWEEN :startDate AND :endDate
  AND bp.user_id = :userId;

SELECT b.booking_id                              AS id,
       b.title,
       b.description,
       b.booking_code                            AS bookingCode,

       r.room_id                                 AS roomId,
       r.room_code                               AS roomCode,
       concat(bl.code, '.', p.code, r.room_code) AS roomName,

       br.place_id                               AS branchId,
       br.code                                   AS branchCode,
       br.name                                   AS branchName,

       bl.place_id                               AS buildingId,
       bl.code                                   AS buildingCode,
       bl.name                                   AS buildingName,

       p.place_id                                AS floorId,
       p.code                                    AS floorCode,
       p.name                                    AS floorName,

       pr.room_id                                AS roomPreviousId,

       b.meeting_start                           AS meetingStart,
       b.meeting_end                             AS meetingEnd,
       b.meeting_date                            AS meetingDate,
       b.count,
       b.status,
       b.created_at                              AS createdAt,
       b.updated_at                              AS updatedAt
FROM booking b
         JOIN
     booking_participant bp ON b.booking_id = bp.booking_id
         JOIN
     "user" u ON bp.user_id = u.user_id
         JOIN
     room r ON b.room_id = r.room_id
         JOIN
     place p ON r.place_id = p.place_id -- Đây là place_id của floor
         LEFT JOIN
     room pr ON b.previous_room_id = pr.room_id
         -- Join để lấy thông tin branch và building từ parent_id của place
         LEFT JOIN
     place bl ON p.parent_id = bl.place_id AND bl.place_type = 'BUILDING'
         LEFT JOIN
     place br ON bl.parent_id = br.place_id AND br.place_type = 'BRANCH'
WHERE bp.user_id = 'ea4e9c4c-a317-4064-8a5b-da2b339e4380'
  AND b.meeting_date BETWEEN '2025-01-01' AND '2025-12-01'


select ue1_0.user_id,
       ue1_0.avatar_image,
       ue1_0.created_at,
       ue1_0.email,
       ue1_0.enable,
       ue1_0.first_name,
       ue1_0.gender,
       ue1_0.last_name,
       ue1_0.phone_number,
       r1_0.user_id,
       r1_1.role_id,
       r1_1.description,
       r1_1.level,
       ue1_0.updated_at,
       ue1_0.user_code,
       ue1_0.user_type
from "user" ue1_0
         left join
     user_role r1_0
     on ue1_0.user_id = r1_0.user_id
         left join
     role r1_1
     on r1_1.role_id = r1_0.role_id
where ue1_0.user_id = '374e33ed-1d51-4298-a3be-b51b4d7529a3'
  and ue1_0.enable = true


SELECT *
FROM room r
         LEFT JOIN place pfl ON r.place_id = pfl.place_id
         LEFT JOIN place pbg ON pfl.parent_id = pbg.place_id
-- LEFT JOIN place pbh ON pbh.parent_id = pbg.place_id AND pbh.place_type = 'BRANCH'
-- WHERE r.status = 'ACTIVE'
--   AND r.place_id = '65f1d8a0-6885-4b51-a691-f843b279dd8b'

SELECT *
FROM room r
         LEFT JOIN place pfl
                   ON r.place_id = pfl.place_id
         LEFT JOIN place pbg
                   ON pfl.parent_id = pbg.place_id
WHERE pbg.status = 'ACTIVE'
  AND pbg.parent_id = '65f1d8a0-6885-4b51-a691-f843b279dd8b';


*/
select re1_0.room_id,
       re1_0.description,
       re1_0.place_id,
       re1_0.room_class_id,
       rc1_0.room_class_id,
       rc1_0.capacity,
       rc1_0.created_at,
       rc1_0.room_class_code,
       rc1_0.status,
       rc1_0.updated_at,
       re1_0.room_code,
       re1_0.status,
       pe2_0.parent_id
from room re1_0
         left join
     place pe1_0
     on re1_0.place_id = pe1_0.place_id
         left join
     place pe2_0
     on pe1_0.parent_id = pe2_0.place_id
         join
     room_class rc1_0
     on rc1_0.room_class_id = re1_0.room_class_id
where re1_0.status=?
  and pe2_0.parent_id=?


SELECT *
FROM approval_form a
         JOIN (SELECT booking_request_id, MAX(updated_at) AS latest_updated
               FROM approval_form
               GROUP BY booking_request_id) latest
              ON a.booking_request_id = latest.booking_request_id AND a.updated_at = latest.latest_updated
         JOIN booking_request b ON a.booking_request_id = b.booking_request_id
WHERE
--     a.status IN ('APPROVED', 'PENDING')
--     a.status IN ('APPROVED') AND
    b.requester = '3393e980-0503-454a-94ef-e43258639994'
  AND a.updated_at BETWEEN '2025-04-01' AND '2025-04-30';
--   AND (  IS NULL OR :status = '' OR a.status = :status)


SELECT DISTINCT ON (a.booking_request_id) b.*, a.status, a.created_at, a.updated_at, a.approver
FROM approval_form a
         LEFT JOIN booking_request b ON a.booking_request_id = b.booking_request_id
WHERE
--     status = 'APPROVED' AND
    b.requester = '3393e980-0503-454a-94ef-e43258639994'
  AND a.updated_at BETWEEN '2025-04-01' AND '2025-04-30'
ORDER BY a.booking_request_id, a.updated_at DESC, a.created_at DESC;


SELECT b.*, a.status, a.created_at, a.updated_at, a.approver
FROM (SELECT DISTINCT ON (booking_request_id) *
      FROM approval_form
      WHERE updated_at BETWEEN '2025-06-01' AND '2025-07-30'
      ORDER BY booking_request_id, updated_at DESC) a
         JOIN booking_request b ON a.booking_request_id = b.booking_request_id
WHERE b.requester = '3393e980-0503-454a-94ef-e43258639994'


SELECT a.*,
       b.booking_request_id,
       b.priority,
       b.days_of_week,
       b.start_time,
       b.end_time,
       b.end_date,
       b.start_date,
       b.recurrence_interval,
       b.recurrence_type,
       b.capacity,
       b.requester,
       b.branch_id,
       b.room_id,
       b.title,
       b.description
FROM (SELECT DISTINCT ON (booking_request_id) *
      FROM approval_form
      WHERE updated_at BETWEEN '2025-06-01' AND '2025-07-30'
      ORDER BY booking_request_id, updated_at DESC) a
         JOIN booking_request b ON a.booking_request_id = b.booking_request_id
WHERE b.requester = '3393e980-0503-454a-94ef-e43258639994'
ORDER BY b.created_at DESC



select COUNT(*)
from booking_request


SELECT r.*, rc.*, rp.*
FROM room r
         LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id
         LEFT JOIN LATERAL (
    SELECT *
    FROM room_class_price_history rp
    WHERE rp.room_class_id = rc.room_class_id
      AND rp.valid_from <= NOW()
      AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
    ORDER BY rp.valid_from DESC
    LIMIT 1
    ) rp ON TRUE


SELECT r.*, rc.*, rp.*
FROM room r
         LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id
         LEFT JOIN LATERAL (
    SELECT *
    FROM room_class_price_history rp
    WHERE rp.room_class_id = rc.room_class_id
      AND rp.valid_from <= NOW()
      AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
    ORDER BY rp.valid_from DESC
    LIMIT 1
    ) rp ON TRUE
WHERE (:id IS NULL OR r.room_id = CAST(:id AS UUID))
  AND (:roomCode IS NULL OR r.room_code ILIKE '%:roomCode%')
  AND (:status IS NULL OR r.status = :status)
  AND (
    :branchId IS NULL AND :buildingId IS NULL AND :floorId IS NULL OR
        -- Case 1: Filter by branch ID
    (:branchId IS NOT NULL AND r.place_id = CAST(:branchId AS UUID)) OR
        -- Case 2: Filter by building ID
    (:buildingId IS NOT NULL AND r.place_id = CAST(:buildingId AS UUID)) OR
        -- Case 3: Filter by floor ID
    (:floorId IS NOT NULL AND r.place_id = CAST(:floorId AS UUID))
    )
  AND (:capacity IS NULL OR rc.capacity >= :capacity)
  AND (:startPrice IS NULL OR rp.total_price >= :startPrice)
  AND (:endPrice IS NULL OR rp.total_price <= :endPrice)
  AND (
    :searchBy IS NULL OR :keyword IS NULL OR
    (:searchBy = 'roomCode' AND r.room_code ILIKE '%:keyword%') OR
    (:searchBy = 'status' AND r.status ILIKE '%:keyword%')
    )



SELECT r.*, rc.*, rp.*
FROM room r
         LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id
         LEFT JOIN LATERAL (
    SELECT *
    FROM room_class_price_history rp
    WHERE rp.room_class_id = rc.room_class_id
      AND rp.valid_from <= NOW()
      AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
    ORDER BY rp.valid_from DESC
    LIMIT 1
    ) rp ON TRUE
         LEFT JOIN place p ON r.place_id = p.place_id
WHERE (:id IS NULL OR r.room_id = CAST(:id AS UUID))
  AND (:roomCode IS NULL OR r.room_code ILIKE CONCAT('%', :roomCode, '%'))
  AND (:status IS NULL OR r.status = :status)
  AND (
    -- Case when branchId is provided, get rooms in this branch and its floors
    :branchId IS NOT NULL AND p.place_type = 'BRANCH' AND p.place_id = CAST(:branchId AS UUID)
        OR
        -- Case when buildingId is provided, get rooms in this building and its floors
    :buildingId IS NOT NULL AND p.place_type = 'BUILDING' AND p.place_id = CAST(:buildingId AS UUID)
        OR
        -- Case when floorId is provided, get rooms in this floor
    :floorId IS NOT NULL AND p.place_type = 'FLOOR' AND p.place_id = CAST(:floorId AS UUID)
        OR
        -- If no filters are provided, return all rooms
    :branchId IS NULL AND :buildingId IS NULL AND :floorId IS NULL
    )
  AND (:capacity IS NULL OR rc.capacity >= :capacity)
  AND (:startPrice IS NULL OR rp.total_price >= :startPrice)
  AND (:endPrice IS NULL OR rp.total_price <= :endPrice)
  AND (
    :searchBy IS NULL OR :keyword IS NULL OR
    (:searchBy = 'roomCode' AND r.room_code ILIKE CONCAT('%', :keyword, '%')) OR
    (:searchBy = 'status' AND r.status ILIKE CONCAT('%', :keyword, '%'))
    )

SELECT r.*, rc.*, rp.*
FROM room r
         LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id
         LEFT JOIN LATERAL (
    SELECT *
    FROM room_class_price_history rp
    WHERE rp.room_class_id = rc.room_class_id
      AND rp.valid_from <= NOW()
      AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
    ORDER BY rp.valid_from DESC
    LIMIT 1
    ) rp ON TRUE
WHERE (:id IS NULL OR r.room_id = CAST(:id AS UUID))
  AND (:roomCode IS NULL OR r.room_code ILIKE '%:roomCode%')
  AND (:status IS NULL OR r.status = :status)
  AND (
    :branchId IS NULL AND :buildingId IS NULL AND :floorId IS NULL OR
        -- Lọc theo chi nhánh
    (:branchId IS NOT NULL AND
     r.place_id IN (SELECT place_id FROM place WHERE place_type = 'BRANCH' AND place_id = CAST(:branchId AS UUID))) OR
        -- Lọc theo tòa nhà thuộc chi nhánh
    (:buildingId IS NOT NULL AND r.place_id IN (SELECT place_id
                                                FROM place
                                                WHERE place_type = 'BUILDING'
                                                  AND parent_id = CAST(:branchId AS UUID))) OR
        -- Lọc theo tầng thuộc tòa nhà của chi nhánh
    (:floorId IS NOT NULL AND r.place_id IN (SELECT place_id
                                             FROM place
                                             WHERE place_type = 'FLOOR'
                                               AND parent_id IN (SELECT place_id
                                                                 FROM place
                                                                 WHERE place_type = 'BUILDING'
                                                                   AND parent_id = CAST(:branchId AS UUID))))
    )
  AND (:capacity IS NULL OR rc.capacity >= :capacity)
  AND (:startPrice IS NULL OR rp.total_price >= :startPrice)
  AND (:endPrice IS NULL OR rp.total_price <= :endPrice)
  AND (
    :searchBy IS NULL OR :keyword IS NULL OR
    (:searchBy = 'roomCode' AND r.room_code ILIKE '%:keyword%') OR
    (:searchBy = 'status' AND r.status ILIKE '%:keyword%')
    )



SELECT r.room_id,
       r.room_code,
       r.status,
       r.description,
       rc.room_class_id,
       rc.room_class_code,
       rc.capacity,
       rp.total_price,
       rp.base_price,
       rp.valid_from,
       rp.valid_end,
       pf.place_id   AS floor_place_id,
       pf.parent_id  AS building_place_id,
       pbg.parent_id AS branch_place_id
FROM room r
-- JOIN với bảng place cho loại FLOOR (pf)
         LEFT JOIN place pf ON r.place_id = pf.place_id AND pf.place_type = 'FLOOR'
-- JOIN với bảng place cho loại BUILDING (pbg), sử dụng parent_id của FLOOR
         LEFT JOIN place pbg ON pf.parent_id = pbg.place_id AND pbg.place_type = 'BUILDING'
         LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id
         LEFT JOIN LATERAL (
    SELECT *
    FROM room_class_price_history rp
    WHERE rp.room_class_id = rc.room_class_id
      AND rp.valid_from <= NOW()
      AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
    ORDER BY rp.valid_from DESC
    LIMIT 1
    ) rp ON TRUE
WHERE (:branchId IS NULL OR pbg.parent_id = CAST(:branchId AS UUID))
  AND (:buildingId IS NULL OR pf.parent_id = CAST(:buildingId AS UUID))
  AND (:floorId IS NULL OR pf.place_id = CAST(:floorId AS UUID))
  AND (:capacity IS NULL OR rc.capacity >= :capacity)
  AND (
    :searchBy IS NULL OR :keyword IS NULL OR
    (:searchBy = 'roomCode' AND r.room_code ILIKE '%' || :keyword || '%') OR
    (:searchBy = 'status' AND r.status ILIKE '%' || :keyword || '%') OR
    (:searchBy = 'description' AND r.description ILIKE '%' || :keyword || '%') OR
    (:searchBy = 'roomClassCode' AND rc.room_class_code ILIKE '%' || :keyword || '%') OR
    (:searchBy = 'id' AND r.room_id = :keyword)
    )
  AND (
    :searchBy IS NULL OR :keyword IS NULL OR
    (:searchBy = 'roomCode' AND r.room_code ILIKE '%' || :keyword || '%') OR
    (:searchBy = 'status' AND r.status ILIKE '%' || :keyword || '%')
    )



SELECT r.room_id,
       r.room_code,
       r.status,
       r.description,
       rc.room_class_id,
       rc.room_class_code,
       rc.capacity,
       rp.total_price,
       rp.base_price,
       rp.valid_from,
       rp.valid_end,
       pf.place_id   AS floor_place_id,
       pf.parent_id  AS building_place_id,
       pbg.parent_id AS branch_place_id
FROM room r
-- JOIN với bảng place cho loại FLOOR (pf)
         LEFT JOIN place pf ON r.place_id = pf.place_id AND pf.place_type = 'FLOOR'
-- JOIN với bảng place cho loại BUILDING (pbg), sử dụng parent_id của FLOOR
         LEFT JOIN place pbg ON pf.parent_id = pbg.place_id AND pbg.place_type = 'BUILDING'
         LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id
         LEFT JOIN LATERAL (
    SELECT *
    FROM room_class_price_history rp
    WHERE rp.room_class_id = rc.room_class_id
      AND rp.valid_from <= NOW()
      AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
    ORDER BY rp.valid_from DESC
    LIMIT 1
    ) rp ON TRUE
WHERE (:branchId IS NULL OR pbg.parent_id = CAST(:branchId AS UUID))
  AND (:buildingId IS NULL OR pf.parent_id = CAST(:buildingId AS UUID))
  AND (:floorId IS NULL OR pf.place_id = CAST(:floorId AS UUID))
  AND (:capacity IS NULL OR rc.capacity >= :capacity)
  AND (
    (:startPrice IS NULL AND :endPrice IS NULL) OR
    (rp.total_price BETWEEN COALESCE(:startPrice, 0) AND COALESCE(:endPrice, 1e12))
    )
  AND (
    :searchBy IS NULL OR :keyword IS NULL OR
    (:searchBy = 'roomCode' AND r.room_code ILIKE '%' || :keyword || '%') OR
    (:searchBy = 'status' AND r.status ILIKE '%' || :keyword || '%') OR
    (:searchBy = 'description' AND r.description ILIKE '%' || :keyword || '%') OR
    (:searchBy = 'roomClassCode' AND rc.room_class_code ILIKE '%' || :keyword || '%') OR
    (:searchBy = 'id' AND r.room_id = :keyword)
    )



SELECT r.room_id,
       r.room_code,
       r.status,
       r.description,
       rc.room_class_id,
       rc.room_class_code,
       rc.capacity,
       rp.total_price,
       rp.base_price,
       rp.valid_from,
       rp.valid_end,
       pf.place_id   AS floor_place_id,
       pf.parent_id  AS building_place_id,
       pbg.parent_id AS branch_place_id
FROM room r
         LEFT JOIN place pf ON r.place_id = pf.place_id AND pf.place_type = 'FLOOR'
         LEFT JOIN place pbg ON pf.parent_id = pbg.place_id AND pbg.place_type = 'BUILDING'
         LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id
         LEFT JOIN LATERAL (
    SELECT *
    FROM room_class_price_history rp
    WHERE rp.room_class_id = rc.room_class_id
      AND rp.valid_from <= NOW()
      AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
    ORDER BY rp.valid_from DESC
    LIMIT 1
    ) rp ON TRUE
WHERE (:branchId IS NULL OR pbg.parent_id = CAST(:branchId AS UUID))
  AND (:buildingId IS NULL OR pf.parent_id = CAST(:buildingId AS UUID))
  AND (:floorId IS NULL OR pf.place_id = CAST(:floorId AS UUID))
  AND (:capacity IS NULL OR rc.capacity >= :capacity)
  AND (
    (:startPrice IS NULL AND :endPrice IS NULL)
        OR (rp.total_price BETWEEN COALESCE(:startPrice, 0) AND COALESCE(:endPrice, 1e12))
    )
  AND (
    :keyword IS NULL OR (
        (:searchBy IS NULL AND (
            r.room_code ILIKE '%' || :keyword || '%' OR
            r.status = :keyword OR
            r.description ILIKE '%' || :keyword || '%' OR
            rc.room_class_code ILIKE '%' || :keyword || '%' OR
            CAST(r.room_id AS TEXT) = :keyword
            )) OR
        (:searchBy = 'roomCode' AND r.room_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'status' AND r.status = '%' || :keyword || '%') OR
        (:searchBy = 'description' AND r.description ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'roomClassCode' AND rc.room_class_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'id' AND CAST(r.room_id AS TEXT) = :keyword)
        )
    )



SELECT r.room_id,
       r.room_code,
       r.status,
       r.description,
       rc.room_class_id,
       rc.room_class_code,
       rc.capacity,
       rp.total_price,
       rp.base_price,
       rp.valid_from,
       rp.valid_end,
       pf.place_id   AS floor_place_id,
       pf.parent_id  AS building_place_id,
       pbg.parent_id AS branch_place_id
FROM room r
         LEFT JOIN place pf ON r.place_id = pf.place_id AND pf.place_type = 'FLOOR'
         LEFT JOIN place pbg ON pf.parent_id = pbg.place_id AND pbg.place_type = 'BUILDING'
         LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id
         LEFT JOIN LATERAL (
    SELECT *
    FROM room_class_price_history rp
    WHERE rp.room_class_id = rc.room_class_id
      AND rp.valid_from <= NOW()
      AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
    ORDER BY rp.valid_from DESC
    LIMIT 1
    ) rp ON TRUE
WHERE (:branchId IS NULL OR pbg.parent_id = CAST(:branchId AS UUID))
  AND (:buildingId IS NULL OR pf.parent_id = CAST(:buildingId AS UUID))
  AND (:floorId IS NULL OR pf.place_id = CAST(:floorId AS UUID))
  AND (:capacity IS NULL OR rc.capacity >= :capacity)
  AND (
    (:startPrice IS NULL AND :endPrice IS NULL)
        OR (rp.total_price BETWEEN COALESCE(:startPrice, 0) AND COALESCE(:endPrice, 1e12))
    )
  AND (:status IS NULL OR r.status = :status)
  AND (
    :keyword IS NULL OR (
        (:searchBy IS NULL AND (
            r.room_code ILIKE '%' || :keyword || '%' OR
            r.description ILIKE '%' || :keyword || '%' OR
            rc.room_class_code ILIKE '%' || :keyword || '%' OR
            CAST(r.room_id AS TEXT) = :keyword
            )) OR
        (:searchBy = 'roomCode' AND r.room_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'description' AND r.description ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'roomClassCode' AND rc.room_class_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'id' AND CAST(r.room_id AS TEXT) = :keyword)
        )
    )


SELECT r.room_id,
       r.room_code,
       r.status,
       r.description,
       rc.room_class_id,
       rc.room_class_code,
       rc.capacity,
       rp.total_price,
       rp.base_price,
       rp.valid_from,
       rp.valid_end,
       pf.place_id                                                     AS floor_place_id,
       pf.parent_id                                                    AS building_place_id,
       pbg.parent_id                                                   AS branch_place_id,

       json_agg(DISTINCT jsonb_build_object(
               'equipmentId', eq.equipment_id,
               'equipmentName', eq.name,
               'equipmentCode', eq.equipment_code,
               'description', eq.description,
               'brand', eq.brand,
               'quantity', erc.quantity,
               'price', ep.current_price
                         )) FILTER (WHERE eq.equipment_id IS NOT NULL) AS equipments,

       json_agg(DISTINCT jsonb_build_object(
               'serviceId', sv.service_id,
               'serviceCode', sv.service_code,
               'note', sv.note,
               'serviceName', sv.name,
               'quantity', src.quantity,
               'price', sp.current_price
                         )) FILTER (WHERE sv.service_id IS NOT NULL)   AS services

FROM room r
         LEFT JOIN place pf ON r.place_id = pf.place_id AND pf.place_type = 'FLOOR'
         LEFT JOIN place pbg ON pf.parent_id = pbg.place_id AND pbg.place_type = 'BUILDING'
         LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id

-- Giá room class
         LEFT JOIN LATERAL (
    SELECT *
    FROM room_class_price_history rp
    WHERE rp.room_class_id = rc.room_class_id
      AND rp.valid_from <= NOW()
      AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
    ORDER BY rp.valid_from DESC
    LIMIT 1
    ) rp ON TRUE

-- Thiết bị
         LEFT JOIN equipment_room_class erc ON erc.room_class_id = rc.room_class_id
         LEFT JOIN equipment eq ON eq.equipment_id = erc.equipment_id

-- Giá thiết bị mới nhất
         LEFT JOIN LATERAL (
    SELECT eph.unit_price current_price
    FROM equipment_price_history eph
    WHERE eph.equipment_id = eq.equipment_id
      AND eph.valid_from <= NOW()
      AND (eph.valid_end IS NULL OR eph.valid_end > NOW())
    ORDER BY eph.valid_from DESC
    LIMIT 1
    ) ep ON TRUE

-- Dịch vụ
         LEFT JOIN service_room_class src ON src.room_class_id = rc.room_class_id
         LEFT JOIN service sv ON sv.service_id = src.service_id

-- Giá dịch vụ mới nhất
         LEFT JOIN LATERAL (
    SELECT sph.unit_price AS current_price
    FROM service_price_history sph
    WHERE sph.service_id = sv.service_id
      AND sph.valid_from <= NOW()
      AND (sph.valid_end IS NULL OR sph.valid_end > NOW())
    ORDER BY sph.valid_from DESC
    LIMIT 1
    ) sp ON TRUE

WHERE (:branchId IS NULL OR pbg.parent_id = CAST(:branchId AS UUID))
  AND (:buildingId IS NULL OR pf.parent_id = CAST(:buildingId AS UUID))
  AND (:floorId IS NULL OR pf.place_id = CAST(:floorId AS UUID))
  AND (:capacity IS NULL OR rc.capacity >= :capacity)
  AND (
    (:startPrice IS NULL AND :endPrice IS NULL)
        OR (rp.total_price BETWEEN COALESCE(:startPrice, 0) AND COALESCE(:endPrice, 1e12))
    )
  AND (:status IS NULL OR r.status = :status)
  AND (
    :keyword IS NULL OR (
        (:searchBy IS NULL AND (
            r.room_code ILIKE '%' || :keyword || '%' OR
            r.description ILIKE '%' || :keyword || '%' OR
            rc.room_class_code ILIKE '%' || :keyword || '%' OR
            CAST(r.room_id AS UUID) = :keyword
            )) OR
        (:searchBy = 'roomCode' AND r.room_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'description' AND r.description ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'roomClassCode' AND rc.room_class_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'id' AND CAST(r.room_id AS UUID) = :keyword)
        )
    )

GROUP BY r.room_id, r.room_code, r.status, r.description,
         rc.room_class_id, rc.room_class_code, rc.capacity,
         rp.total_price, rp.base_price, rp.valid_from, rp.valid_end,
         pf.place_id, pf.parent_id,
         pbg.parent_id



SELECT r.room_id,
       r.room_code,
       r.status,
       r.description,
       rc.room_class_id,
       rc.room_class_code,
       rc.capacity,
       rp.total_price,
       rp.base_price,
       rp.valid_from,
       rp.valid_end,
       pf.place_id                                                     AS floor_place_id,
       pf.parent_id                                                    AS building_place_id,
       pbg.parent_id                                                   AS branch_place_id,

       json_agg(DISTINCT jsonb_build_object(
               'equipmentId', eq.equipment_id,
               'equipmentName', eq.name,
               'equipmentCode', eq.equipment_code,
               'description', eq.description,
               'brand', eq.brand,
               'quantity', erc.quantity,
               'price', ep.current_price
                         )) FILTER (WHERE eq.equipment_id IS NOT NULL) AS equipments,

       json_agg(DISTINCT jsonb_build_object(
               'serviceId', sv.service_id,
               'serviceCode', sv.service_code,
               'note', sv.note,
               'serviceName', sv.name,
               'quantity', src.quantity,
               'price', sp.current_price
                         )) FILTER (WHERE sv.service_id IS NOT NULL)   AS services

FROM room r
         LEFT JOIN place pf ON r.place_id = pf.place_id AND pf.place_type = 'FLOOR'
         LEFT JOIN place pbg ON pf.parent_id = pbg.place_id AND pbg.place_type = 'BUILDING'
         LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id

         LEFT JOIN LATERAL (
    SELECT *
    FROM room_class_price_history rp
    WHERE rp.room_class_id = rc.room_class_id
      AND rp.valid_from <= NOW()
      AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
    ORDER BY rp.valid_from DESC
    LIMIT 1
    ) rp ON TRUE

         LEFT JOIN equipment_room_class erc ON erc.room_class_id = rc.room_class_id
         LEFT JOIN equipment eq ON eq.equipment_id = erc.equipment_id

         LEFT JOIN LATERAL (
    SELECT eph.unit_price current_price
    FROM equipment_price_history eph
    WHERE eph.equipment_id = eq.equipment_id
      AND eph.valid_from <= NOW()
      AND (eph.valid_end IS NULL OR eph.valid_end > NOW())
    ORDER BY eph.valid_from DESC
    LIMIT 1
    ) ep ON TRUE

         LEFT JOIN service_room_class src ON src.room_class_id = rc.room_class_id
         LEFT JOIN service sv ON sv.service_id = src.service_id

         LEFT JOIN LATERAL (
    SELECT sph.unit_price AS current_price
    FROM service_price_history sph
    WHERE sph.service_id = sv.service_id
      AND sph.valid_from <= NOW()
      AND (sph.valid_end IS NULL OR sph.valid_end > NOW())
    ORDER BY sph.valid_from DESC
    LIMIT 1
    ) sp ON TRUE

WHERE (:branchId IS NULL OR pbg.parent_id = CAST(:branchId AS UUID))
  AND (:buildingId IS NULL OR pf.parent_id = CAST(:buildingId AS UUID))
  AND (:floorId IS NULL OR pf.place_id = CAST(:floorId AS UUID))
  AND (:capacity IS NULL OR rc.capacity >= :capacity)
  AND (
    (:startPrice IS NULL AND :endPrice IS NULL)
        OR (rp.total_price BETWEEN COALESCE(:startPrice, 0) AND COALESCE(:endPrice, 1e12))
    )
  AND (:status IS NULL OR r.status = :status)
  AND (
    :keyword IS NULL OR (
        (:searchBy IS NULL AND (
            r.room_code ILIKE '%' || :keyword || '%' OR
            r.description ILIKE '%' || :keyword || '%' OR
            rc.room_class_code ILIKE '%' || :keyword || '%' OR
            r.room_id = CAST(:keyword AS UUID)
            )) OR
        (:searchBy = 'roomCode' AND r.room_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'description' AND r.description ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'roomClassCode' AND rc.room_class_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'id' AND r.room_id = CAST(:keyword AS UUID))
        )
    )
GROUP BY r.room_id, r.room_code, r.status, r.description,
         rc.room_class_id, rc.room_class_code, rc.capacity,
         rp.total_price, rp.base_price, rp.valid_from, rp.valid_end,
         pf.place_id, pf.parent_id,
         pbg.parent_id



SELECT r.room_id,
       r.room_code,
       r.status,
       r.description,
       rc.room_class_id,
       rc.room_class_code,
       rc.capacity,
       rp.total_price,
       rp.base_price,
       rp.valid_from,
       rp.valid_end,
       pf.place_id                                                     AS floor_place_id,
       pf.parent_id                                                    AS building_place_id,
       pbg.parent_id                                                   AS branch_place_id,

       json_agg(DISTINCT jsonb_build_object(
               'equipmentId', eq.equipment_id,
               'equipmentName', eq.name,
               'equipmentCode', eq.equipment_code,
               'description', eq.description,
               'brand', eq.brand,
               'quantity', erc.quantity,
               'price', ep.current_price
                         )) FILTER (WHERE eq.equipment_id IS NOT NULL) AS equipments,

       json_agg(DISTINCT jsonb_build_object(
               'serviceId', sv.service_id,
               'serviceCode', sv.service_code,
               'note', sv.note,
               'serviceName', sv.name,
               'quantity', src.quantity,
               'price', sp.current_price
                         )) FILTER (WHERE sv.service_id IS NOT NULL)   AS services,
       (SELECT json_agg(url ORDER BY image_order)
        FROM image_url
        WHERE entity_id = r.room_id)                                   AS
                                                                          image_urls

FROM room r
         LEFT JOIN place pf ON r.place_id = pf.place_id AND pf.place_type = 'FLOOR'
         LEFT JOIN place pbg ON pf.parent_id = pbg.place_id AND pbg.place_type = 'BUILDING'
         LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id

         LEFT JOIN LATERAL (
    SELECT *
    FROM room_class_price_history rp
    WHERE rp.room_class_id = rc.room_class_id
      AND rp.valid_from <= NOW()
      AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
    ORDER BY rp.valid_from DESC
    LIMIT 1
    ) rp ON TRUE

         LEFT JOIN equipment_room_class erc ON erc.room_class_id = rc.room_class_id
         LEFT JOIN equipment eq ON eq.equipment_id = erc.equipment_id

         LEFT JOIN LATERAL (
    SELECT eph.unit_price current_price
    FROM equipment_price_history eph
    WHERE eph.equipment_id = eq.equipment_id
      AND eph.valid_from <= NOW()
      AND (eph.valid_end IS NULL OR eph.valid_end > NOW())
    ORDER BY eph.valid_from DESC
    LIMIT 1
    ) ep ON TRUE

         LEFT JOIN service_room_class src ON src.room_class_id = rc.room_class_id
         LEFT JOIN service sv ON sv.service_id = src.service_id

         LEFT JOIN LATERAL (
    SELECT sph.unit_price AS current_price
    FROM service_price_history sph
    WHERE sph.service_id = sv.service_id
      AND sph.valid_from <= NOW()
      AND (sph.valid_end IS NULL OR sph.valid_end > NOW())
    ORDER BY sph.valid_from DESC
    LIMIT 1
    ) sp ON TRUE

WHERE (:branchId IS NULL OR pbg.parent_id = CAST(:branchId AS UUID))
  AND (:buildingId IS NULL OR pf.parent_id = CAST(:buildingId AS UUID))
  AND (:floorId IS NULL OR pf.place_id = CAST(:floorId AS UUID))
  AND (:capacity IS NULL OR rc.capacity >= :capacity)
  AND (
    (:startPrice IS NULL AND :endPrice IS NULL)
        OR (rp.total_price BETWEEN COALESCE(:startPrice, 0) AND COALESCE(:endPrice, 1e12))
    )
  AND (:status IS NULL OR r.status = :status)
  AND (
    :keyword IS NULL OR (
        (:searchBy IS NULL AND (
            r.room_code ILIKE '%' || :keyword || '%' OR
            r.description ILIKE '%' || :keyword || '%' OR
            rc.room_class_code ILIKE '%' || :keyword || '%' OR
            r.room_id = CAST(:keyword AS UUID)
            )) OR
        (:searchBy = 'roomCode' AND r.room_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'description' AND r.description ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'roomClassCode' AND rc.room_class_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'id' AND r.room_id = CAST(:keyword AS UUID))
        )
    )
GROUP BY r.room_id, r.room_code, r.status, r.description,
         rc.room_class_id, rc.room_class_code, rc.capacity,
         rp.total_price, rp.base_price, rp.valid_from, rp.valid_end,
         pf.place_id, pf.parent_id,
         pbg.parent_id
ORDER BY r.room_code DESC


SELECT r.room_id,
       r.room_code,
       r.status,
       r.description,
       rc.room_class_id,
       rc.room_class_code,
       rc.capacity,
       rp.total_price,
       rp.base_price,
       rp.valid_from,
       rp.valid_end,
       pf.place_id                                                     AS floor_place_id,
       pf.parent_id                                                    AS building_place_id,
       pbg.parent_id                                                   AS branch_place_id,

       json_agg(DISTINCT jsonb_build_object(
               'id', eq.equipment_id,
               'name', eq.name,
               'equipmentCode', eq.equipment_code,
               'description', eq.description,
               'brand', eq.brand,
               'quantity', erc.quantity,
               'price', ep.current_price
                         )) FILTER (WHERE eq.equipment_id IS NOT NULL) AS equipments,

       json_agg(DISTINCT jsonb_build_object(
               'id', sv.service_id,
               'serviceCode', sv.service_code,
               'note', sv.note,
               'description', sv.description,
               'name', sv.name,
               'quantity', src.quantity,
               'price', sp.current_price
                         )) FILTER (WHERE sv.service_id IS NOT NULL)   AS services,
       (SELECT array_agg(url ORDER BY image_order)
        FROM image_url
        WHERE entity_id = r.room_id)                                   AS image_urls

FROM room r
         LEFT JOIN place pf ON r.place_id = pf.place_id AND pf.place_type = 'FLOOR'
         LEFT JOIN place pbg ON pf.parent_id = pbg.place_id AND pbg.place_type = 'BUILDING'
         LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id

         LEFT JOIN LATERAL (
    SELECT *
    FROM room_class_price_history rp
    WHERE rp.room_class_id = rc.room_class_id
      AND rp.valid_from <= NOW()
      AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
    ORDER BY rp.valid_from DESC
    LIMIT 1
    ) rp ON TRUE

         LEFT JOIN equipment_room_class erc ON erc.room_class_id = rc.room_class_id
         LEFT JOIN equipment eq ON eq.equipment_id = erc.equipment_id

         LEFT JOIN LATERAL (
    SELECT eph.unit_price current_price
    FROM equipment_price_history eph
    WHERE eph.equipment_id = eq.equipment_id
      AND eph.valid_from <= NOW()
      AND (eph.valid_end IS NULL OR eph.valid_end > NOW())
    ORDER BY eph.valid_from DESC
    LIMIT 1
    ) ep ON TRUE

         LEFT JOIN service_room_class src ON src.room_class_id = rc.room_class_id
         LEFT JOIN service sv ON sv.service_id = src.service_id

         LEFT JOIN LATERAL (
    SELECT sph.unit_price AS current_price
    FROM service_price_history sph
    WHERE sph.service_id = sv.service_id
      AND sph.valid_from <= NOW()
      AND (sph.valid_end IS NULL OR sph.valid_end > NOW())
    ORDER BY sph.valid_from DESC
    LIMIT 1
    ) sp ON TRUE

WHERE (:branchId IS NULL OR pbg.parent_id = CAST(:branchId AS UUID))
  AND (:buildingId IS NULL OR pf.parent_id = CAST(:buildingId AS UUID))
  AND (:floorId IS NULL OR pf.place_id = CAST(:floorId AS UUID))
  AND (:capacity IS NULL OR rc.capacity >= :capacity)
  AND (
    (:startPrice IS NULL AND :endPrice IS NULL)
        OR (rp.total_price BETWEEN COALESCE(:startPrice, 0) AND COALESCE(:endPrice, 1e12))
    )
  AND (:status IS NULL OR r.status = :status)
  AND (
    :keyword IS NULL OR (
        (:searchBy IS NULL AND (
            r.room_code ILIKE '%' || :keyword || '%' OR
            r.description ILIKE '%' || :keyword || '%' OR
            rc.room_class_code ILIKE '%' || :keyword || '%' OR
            r.room_id = CAST(:keyword AS UUID)
            )) OR
        (:searchBy = 'roomCode' AND r.room_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'description' AND r.description ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'roomClassCode' AND rc.room_class_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'id' AND r.room_id = CAST(:keyword AS UUID))
        )
    )
GROUP BY r.room_id, r.room_code, r.status, r.description,
         rc.room_class_id, rc.room_class_code, rc.capacity,
         rp.total_price, rp.base_price, rp.valid_from, rp.valid_end,
         pf.place_id, pf.parent_id,
         pbg.parent_id
ORDER BY r.room_code DESC


SELECT b.booking_id                              AS id,
       b.title,
       b.description,
       b.booking_code,

       r.room_id,
       r.room_code,
       concat(bl.code, '.', p.code, r.room_code) AS room_name,

       br.place_id                               AS branch_id,
       br.code                                   AS branch_code,
       br.name                                   AS branch_name,

       bl.place_id                               AS building_id,
       bl.code                                   AS building_code,
       bl.name                                   AS building_name,

       p.place_id                                AS floor_id,
       p.code                                    AS floor_code,
       p.name                                    AS floor_name,

       pr.room_id                                AS room_previous_id,

       b.meeting_start,
       b.meeting_end,
       b.meeting_date,
       b.count,
       b.status,
       b.created_at,
       b.updated_at,

       bp.user_id
FROM booking b
         JOIN
     booking_participant bp ON b.booking_id = bp.booking_id
         JOIN
     "user" u ON bp.user_id = u.user_id
         JOIN
     room r ON b.room_id = r.room_id
         JOIN
     place p ON r.place_id = p.place_id -- Đây là place_id của floor
         LEFT JOIN
     room pr ON b.previous_room_id = pr.room_id
         -- Join để lấy thông tin branch và building từ parent_id của place
         LEFT JOIN
     place bl ON p.parent_id = bl.place_id AND bl.place_type = 'BUILDING'
         LEFT JOIN
     place br ON bl.parent_id = br.place_id AND br.place_type = 'BRANCH'
WHERE b.meeting_date BETWEEN '2025-05-01' AND '2025-05-31'
  AND (:status IS NULL OR b.status = :status)
--   AND bp.user_id = :userId

-- ea4e9c4c-a317-4064-8a5b-da2b338e4180
-- ea4e9c4c-a317-4064-8a5b-da2b339e4580


SELECT a.created_at,
       a.updated_at,
       a.status,
       a.approver,
       b.booking_request_id,
       b.priority,
       b.days_of_week,
       b.start_time,
       b.end_time,
       b.end_date,
       b.start_date,
       b.recurrence_interval,
       b.recurrence_type,
       b.capacity,
       b.requester,
       b.branch_id,
       b.room_id,
       b.title,
       b.description
FROM (SELECT DISTINCT ON (booking_request_id) *
      FROM approval_form
      WHERE updated_at BETWEEN :startDate AND :endDate
      ORDER BY booking_request_id, updated_at DESC) a
         JOIN booking_request b ON a.booking_request_id = b.booking_request_id
WHERE (:listStatus IS NULL OR a.status IN :listStatus)
  AND (:requester IS NULL OR b.requester = CAST(:requester AS UUID))
ORDER BY b.created_at DESC

--     3e4d3bc3-1c92-498e-9adc-108ed53a42e5


SELECT r.room_id                                                       AS id,
       r.room_code                                                     AS roomCode,
       r.status,
       r.description,
       rc.room_class_id                                                AS roomClassId,
       rc.room_class_code                                              AS roomClassCode,
       rc.capacity,
       rp.total_price                                                  AS totalPrice,
       rp.base_price                                                   AS basePrice,
       rp.valid_from                                                   AS validFrom,
       rp.valid_end                                                    AS validEnd,
       pf.place_id                                                     AS floorPlaceId,
       pf.parent_id                                                    AS building_place_id,
       pbg.parent_id                                                   AS branchPlaceId,

       json_agg(DISTINCT jsonb_build_object(
               'id', eq.equipment_id,
               'equipmentCode', eq.equipment_code,
               'name', eq.name,
               'description', eq.description,
               'brand', eq.brand,
               'quantity', erc.quantity,
               'price', ep.current_price
                         )) FILTER (WHERE eq.equipment_id IS NOT NULL) AS equipmentsJson,

       json_agg(DISTINCT jsonb_build_object(
               'id', sv.service_id,
               'serviceCode', sv.service_code,
               'name', sv.name,
               'note', sv.note,
               'description', sv.description,
               'quantity', src.quantity,
               'price', sp.current_price
                         )) FILTER (WHERE sv.service_id IS NOT NULL)   AS servicesJson,


       (SELECT array_agg(url ORDER BY image_order)
        FROM image_url
        WHERE entity_id = r.room_id)                                   AS image_urls

FROM room r
         LEFT JOIN place pf ON r.place_id = pf.place_id AND pf.place_type = 'FLOOR'
         LEFT JOIN place pbg ON pf.parent_id = pbg.place_id AND pbg.place_type = 'BUILDING'
         LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id

         LEFT JOIN LATERAL (
    SELECT *
    FROM room_class_price_history rp
    WHERE rp.room_class_id = rc.room_class_id
      AND rp.valid_from <= NOW()
      AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
    ORDER BY rp.valid_from DESC
    LIMIT 1
    ) rp ON TRUE

         LEFT JOIN equipment_room_class erc ON erc.room_class_id = rc.room_class_id
         LEFT JOIN equipment eq ON eq.equipment_id = erc.equipment_id

         LEFT JOIN LATERAL (
    SELECT eph.unit_price current_price
    FROM equipment_price_history eph
    WHERE eph.equipment_id = eq.equipment_id
      AND eph.valid_from <= NOW()
      AND (eph.valid_end IS NULL OR eph.valid_end > NOW())
    ORDER BY eph.valid_from DESC
    LIMIT 1
    ) ep ON TRUE

         LEFT JOIN service_room_class src ON src.room_class_id = rc.room_class_id
         LEFT JOIN service sv ON sv.service_id = src.service_id

         LEFT JOIN LATERAL (
    SELECT sph.unit_price AS current_price
    FROM service_price_history sph
    WHERE sph.service_id = sv.service_id
      AND sph.valid_from <= NOW()
      AND (sph.valid_end IS NULL OR sph.valid_end > NOW())
    ORDER BY sph.valid_from DESC
    LIMIT 1
    ) sp ON TRUE

WHERE (:branchId IS NULL OR pbg.parent_id = CAST(:branchId AS UUID))
  AND (:buildingId IS NULL OR pf.parent_id = CAST(:buildingId AS UUID))
  AND (:floorId IS NULL OR pf.place_id = CAST(:floorId AS UUID))
  AND (:capacity IS NULL OR rc.capacity >= :capacity)
  AND (
    (:startPrice IS NULL AND :endPrice IS NULL)
        OR (rp.total_price BETWEEN COALESCE(:startPrice, 0) AND COALESCE(:endPrice, 1e12))
    )
  AND (:status IS NULL OR r.status = :status)
  AND (
    :keyword IS NULL OR (
        (:searchBy IS NULL AND (
            r.room_code ILIKE '%' || :keyword || '%' OR
            r.description ILIKE '%' || :keyword || '%' OR
            rc.room_class_code ILIKE '%' || :keyword || '%' OR
            r.room_id = CAST(:keyword AS UUID)
            )) OR
        (:searchBy = 'roomCode' AND r.room_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'description' AND r.description ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'roomClassCode' AND rc.room_class_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'id' AND r.room_id = CAST(:keyword AS UUID))
        )
    )
GROUP BY r.room_id, r.room_code, r.status, r.description,
         rc.room_class_id, rc.room_class_code, rc.capacity,
         rp.total_price, rp.base_price, rp.valid_from, rp.valid_end,
         pf.place_id, pf.parent_id,
         pbg.parent_id
ORDER BY r.room_code DESC


SELECT r.room_id                                                       AS id,
       r.room_code                                                     AS roomCode,
       r.status                                                        AS status,
       r.description                                                   AS decription,
       rc.room_class_id                                                AS roomClassId,
       rc.room_class_code                                              AS roomClassCode,
       rc.capacity                                                     AS capacity,
       rp.total_price                                                  AS totalPrice,
       rp.base_price                                                   AS basePrice,
       rp.valid_from                                                   AS validFrom,
       rp.valid_end                                                    AS validEnd,
       pf.place_id                                                     AS floorPlaceId,
       pf.parent_id                                                    AS buildingPlaceId,
       pbg.parent_id                                                   AS branchPlaceId,

       json_agg(DISTINCT jsonb_build_object(
               'id', eq.equipment_id,
               'equipmentCode', eq.equipment_code,
               'name', eq.name,
               'description', eq.description,
               'brand', eq.brand,
               'quantity', erc.quantity,
               'price', ep.current_price
                         )) FILTER (WHERE eq.equipment_id IS NOT NULL) AS equipmentsJson,

       json_agg(DISTINCT jsonb_build_object(
               'id', sv.service_id,
               'serviceCode', sv.service_code,
               'name', sv.name,
               'note', sv.note,
               'description', sv.description,
               'quantity', src.quantity,
               'price', sp.current_price
                         )) FILTER (WHERE sv.service_id IS NOT NULL)   AS servicesJson,


       (SELECT array_agg(url ORDER BY image_order)
        FROM image_url
        WHERE entity_id = r.room_id)                                   AS imageUrls

FROM room r
         LEFT JOIN place pf ON r.place_id = pf.place_id AND pf.place_type = 'FLOOR'
         LEFT JOIN place pbg ON pf.parent_id = pbg.place_id AND pbg.place_type = 'BUILDING'
         LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id

         LEFT JOIN LATERAL (
    SELECT *
    FROM room_class_price_history rp
    WHERE rp.room_class_id = rc.room_class_id
      AND rp.valid_from <= NOW()
      AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
    ORDER BY rp.valid_from DESC
    LIMIT 1
    ) rp ON TRUE

         LEFT JOIN equipment_room_class erc ON erc.room_class_id = rc.room_class_id
         LEFT JOIN equipment eq ON eq.equipment_id = erc.equipment_id

         LEFT JOIN LATERAL (
    SELECT eph.unit_price current_price
    FROM equipment_price_history eph
    WHERE eph.equipment_id = eq.equipment_id
      AND eph.valid_from <= NOW()
      AND (eph.valid_end IS NULL OR eph.valid_end > NOW())
    ORDER BY eph.valid_from DESC
    LIMIT 1
    ) ep ON TRUE

         LEFT JOIN service_room_class src ON src.room_class_id = rc.room_class_id
         LEFT JOIN service sv ON sv.service_id = src.service_id

         LEFT JOIN LATERAL (
    SELECT sph.unit_price AS current_price
    FROM service_price_history sph
    WHERE sph.service_id = sv.service_id
      AND sph.valid_from <= NOW()
      AND (sph.valid_end IS NULL OR sph.valid_end > NOW())
    ORDER BY sph.valid_from DESC
    LIMIT 1
    ) sp ON TRUE

WHERE (:branchId IS NULL OR pbg.parent_id = CAST(:branchId AS UUID))
  AND (:buildingId IS NULL OR pf.parent_id = CAST(:buildingId AS UUID))
  AND (:floorId IS NULL OR pf.place_id = CAST(:floorId AS UUID))
  AND (:capacity IS NULL OR rc.capacity >= :capacity)
  AND (
    (:startPrice IS NULL AND :endPrice IS NULL)
        OR (rp.total_price BETWEEN COALESCE(:startPrice, 0) AND COALESCE(:endPrice, 1e12))
    )
  AND (:status IS NULL OR r.status = :status)
  AND (
    :keyword IS NULL OR (
        (:searchBy IS NULL AND (
            r.room_code ILIKE '%' || :keyword || '%' OR
            r.description ILIKE '%' || :keyword || '%' OR
            rc.room_class_code ILIKE '%' || :keyword || '%' OR
            r.room_id = CAST(:keyword AS UUID)
            )) OR
        (:searchBy = 'roomCode' AND r.room_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'description' AND r.description ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'roomClassCode' AND rc.room_class_code ILIKE '%' || :keyword || '%') OR
        (:searchBy = 'id' AND r.room_id = CAST(:keyword AS UUID))
        )
    )
GROUP BY r.room_id, r.room_code, r.status, r.description,
         rc.room_class_id, rc.room_class_code, rc.capacity,
         rp.total_price, rp.base_price, rp.valid_from, rp.valid_end,
         pf.place_id, pf.parent_id,
         pbg.parent_id


--     findAllByMeetingDateBetweenAndStatus
-- findAllByMeetingDateBetweenAndUserIdAndStatus

SELECT *
FROM "group";



SELECT g.group_id    AS groupId,
       g.name        AS name,
       g.group_type  AS groupType,
       g.branch_id   AS branchId,
       g.user_id     AS createBy,
       g.group_code  AS groupCode,
       g.status      AS status,

       uo.email      AS ownerEmail,
       uo.first_name AS ownerFirstName,
       uo.last_name  AS ownerLastName,

       gm.user_id    AS memberId,
       um.email      AS memberEmail


FROM "group" g
         LEFT JOIN group_member gm ON gm.group_id = g.group_id
         LEFT JOIN "user" uo ON uo.user_id = g.user_id
         JOIN "user" um ON um.user_id = gm.user_id
WHERE group_type = 'DEPARTMENT'
  AND gm.user_id = 'ea4e9c4c-a317-4064-8a5b-da2b339e4181'



SELECT DISTINCT ON (g.group_id) g.group_id    AS groupId,
                                g.name        AS name,
                                g.group_type  AS groupType,
                                g.user_id     AS createdBy,
                                g.group_code  AS groupCode,
                                g.status      AS status,

                                uo.email      AS ownerEmail,
                                uo.first_name AS ownerFirstName,
                                uo.last_name  AS ownerLastName,
                                uo.user_code  AS ownerUserCode,

                                p.name        AS branchName,
                                p.code        AS branchCode,
                                g.branch_id   AS branchId
FROM "group" g
         LEFT JOIN group_member gm ON gm.group_id = g.group_id
         LEFT JOIN "user" uo ON uo.user_id = g.user_id
         LEFT JOIN "user" um ON um.user_id = gm.user_id
         LEFT JOIN place p ON g.branch_id = p.place_id

WHERE (:userId IS NULL OR gm.user_id = CAST(:userId AS UUID))
  AND (:status IS NULL OR g.status = :status)
  AND (:groupType IS NULL OR g.group_type = :groupType)
  AND (:branchId IS NULL OR g.branch_id = CAST(:branchId AS UUID))
  AND (
    :keyword IS NULL OR (
        (
            :searchBy = 'name' AND g.name ILIKE '%' || :keyword || '%'
            ) OR (
            :searchBy = 'id' AND g.group_id = CAST(:keyword AS UUID)
            ) OR (
            :searchBy = 'groupCode' AND g.group_code ILIKE '%' || :keyword || '%'
            ) OR (
            :searchBy IS NULL AND (
                g.name ILIKE '%' || :keyword || '%' OR
                g.group_code ILIKE '%' || :keyword || '%' OR
                uo.email ILIKE '%' || :keyword || '%' OR
                uo.first_name ILIKE '%' || :keyword || '%' OR
                uo.last_name ILIKE '%' || :keyword || '%' OR
                p.name ILIKE '%' || :keyword || '%' OR
                p.code ILIKE '%' || :keyword || '%'
                )
            )
        )
    )



SELECT g.group_id        AS groupId,
       g.name            AS name,
       g.group_type      AS groupType,
       g.user_id         AS createdBy,
       g.group_code      AS groupCode,
       g.status          AS status,

       uo.email          AS ownerEmail,
       uo.first_name     AS ownerFirstName,
       uo.last_name      AS ownerLastName,
       uo.user_code      AS ownerUserCode,

       p.name            AS branchName,
       p.code            AS branchCode,
       g.branch_id       AS branchId,

       COUNT(um.user_id) AS quantityMember
FROM "group" g
         LEFT JOIN group_member gm ON gm.group_id = g.group_id
         LEFT JOIN "user" uo ON uo.user_id = g.user_id
         JOIN "user" um ON um.user_id = gm.user_id
         LEFT JOIN place p ON g.branch_id = p.place_id

WHERE (:userId IS NULL OR gm.user_id = CAST(:userId AS UUID))
  AND (:status IS NULL OR g.status = :status)
  AND (:groupType IS NULL OR g.group_type = :groupType)
  AND (:branchId IS NULL OR g.branch_id = CAST(:branchId AS UUID))
  AND (
    :keyword IS NULL OR (
        (
            :searchBy = 'name' AND g.name ILIKE '%' || :keyword || '%'
            ) OR (
            :searchBy = 'id' AND g.group_id = CAST(:keyword AS UUID)
            ) OR (
            :searchBy = 'groupCode' AND g.group_code ILIKE '%' || :keyword || '%'
            ) OR (
            :searchBy IS NULL AND (
                g.name ILIKE '%' || :keyword || '%' OR
                g.group_code ILIKE '%' || :keyword || '%' OR
                uo.email ILIKE '%' || :keyword || '%' OR
                uo.first_name ILIKE '%' || :keyword || '%' OR
                uo.last_name ILIKE '%' || :keyword || '%' OR
                p.name ILIKE '%' || :keyword || '%' OR
                p.code ILIKE '%' || :keyword || '%'
                )
            )
        )
    )
GROUP BY g.group_id, g.name, g.group_type, g.user_id,
         g.group_code, g.status, uo.email, uo.first_name,
         uo.last_name, uo.user_code, p.name, p.code, g.branch_id



SELECT g.group_id        AS id,
       g.name            AS name,
       g.group_type      AS groupType,
       g.user_id         AS createdBy,
       g.group_code      AS groupCode,
       g.status          AS status,

       uo.email          AS ownerEmail,
       uo.first_name     AS ownerFirstName,
       uo.last_name      AS ownerLastName,
       uo.user_code      AS ownerUserCode,

       p.name            AS branchName,
       p.code            AS branchCode,
       g.branch_id       AS branchId,

       COUNT(um.user_id) AS quantityMember
FROM "group" g
         LEFT JOIN group_member gm ON gm.group_id = g.group_id
         LEFT JOIN "user" uo ON uo.user_id = g.user_id
         JOIN "user" um ON um.user_id = gm.user_id
         LEFT JOIN place p ON g.branch_id = p.place_id

WHERE (:userId IS NULL OR gm.user_id = CAST(:userId AS UUID))
  AND (:status IS NULL OR g.status = :status)
  AND (:groupType IS NULL OR g.group_type = :groupType)
  AND (:branchId IS NULL OR g.branch_id = CAST(:branchId AS UUID))
  AND (
    :keyword IS NULL OR (
        (
            :searchBy = 'name' AND g.name ILIKE '%' || :keyword || '%'
            ) OR (
            :searchBy = 'id' AND (:keyword ~* '^[0-9a-fA-F-]{36}$' AND g.group_id = CAST(:keyword AS UUID))
            ) OR (
            :searchBy = 'groupCode' AND g.group_code ILIKE '%' || :keyword || '%'
            ) OR (
            :searchBy = 'email' AND uo.email ILIKE '%' || :keyword || '%'
            ) OR (
            :searchBy IS NULL AND (
                g.name ILIKE '%' || :keyword || '%' OR
                g.group_code ILIKE '%' || :keyword || '%' OR
                uo.email ILIKE '%' || :keyword || '%' OR
                p.name ILIKE '%' || :keyword || '%' OR
                p.code ILIKE '%' || :keyword || '%'
                )
            )
        )
    )
GROUP BY g.group_id, g.name, g.group_type, g.user_id,
         g.group_code, g.status, uo.email, uo.first_name,
         uo.last_name, uo.user_code, p.name, p.code, g.branch_id



SELECT *
FROM room_class rc
         LEFT JOIN room_class_price_history rcph
                   ON rc.room_class_id = rcph.room_class_id AND rcph.valid_from <= CURRENT_TIMESTAMP
GROUP BY rc.room_class_id















WITH latest_price AS (
    SELECT room_class_id, MAX(valid_from) AS valid_from
    FROM room_class_price_history
    WHERE valid_from <= CURRENT_TIMESTAMP
    GROUP BY room_class_id
),
     price_with_latest AS (
         SELECT rcph.*
         FROM room_class_price_history rcph
                  JOIN latest_price lp
                       ON rcph.room_class_id = lp.room_class_id AND rcph.valid_from = lp.valid_from
     ),
     services AS (
         SELECT room_class_id,
                JSON_AGG(JSON_BUILD_OBJECT(
                        'id', src.service_id,
                        'quantity', src.quantity,
                        'name', s.name
                         )) AS services
         FROM service_room_class src
                  JOIN service s ON src.service_id = s.service_id
         GROUP BY src.room_class_id
     ),
     equipment AS (
         SELECT erc.room_class_id,
                JSON_AGG(JSON_BUILD_OBJECT(
                        'id', erc.equipment_id,
                        'quantity', erc.quantity,
                        'name', e.name
                         )) AS equipments
         FROM equipment_room_class erc
                  JOIN equipment e ON erc.equipment_id = e.equipment_id
         GROUP BY erc.room_class_id
     )

SELECT
    rc.room_class_id AS id,
    rc.room_class_code AS roomClassCode,
    rc.status AS status,
    rc.capacity AS capacity,
    rcph.total_price AS totalPrice,
    rcph.valid_from AS validFrom,
    rcph.valid_end AS validEnd,
    rcph.base_price AS basePrice,
    s.services AS jsonServices,
    e.equipments AS jsonEquipments
FROM room_class rc
         LEFT JOIN price_with_latest rcph ON rc.room_class_id = rcph.room_class_id
         LEFT JOIN services s ON rc.room_class_id = s.room_class_id
         LEFT JOIN equipment e ON rc.room_class_id = e.room_class_id
WHERE
    (:keyword IS NULL OR (
        (
            (:searchBy IS NULL OR :searchBy = 'id')
                AND rc.room_class_id::text ILIKE '%' || :keyword || '%'
            )
            OR (
            (:searchBy IS NULL OR :searchBy = 'code')
                AND rc.room_class_code ILIKE '%' || :keyword || '%'
            )
            OR (
            (:searchBy IS NULL OR :searchBy = 'serviceName')
                AND EXISTS (
                SELECT 1
                FROM jsonb_array_elements(s.services::jsonb) AS service
                WHERE service->>'name' ILIKE '%' || :keyword || '%'
            )
            )
            OR (
            (:searchBy IS NULL OR :searchBy = 'equipmentName')
                AND EXISTS (
                SELECT 1
                FROM jsonb_array_elements(e.equipments::jsonb) AS equipment
                WHERE equipment->>'name' ILIKE '%' || :keyword || '%'
            )
            )
        ))
  AND (:startPrice IS NULL OR rcph.total_price >= :startPrice)
  AND (:endPrice IS NULL OR rcph.total_price <= :endPrice)
  AND (:capacity IS NULL OR rc.capacity <= :capacity)
  AND (:status IS NULL OR rc.status = :status)















