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
  AND b.status = 'ACTIVE'

