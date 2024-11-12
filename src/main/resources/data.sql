/*INSERT INTO roles (role_name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (role_name) VALUES ('ROLE_COORDINATOR');
INSERT INTO roles (role_name) VALUES ('ROLE_CLASS_ADMIN');
INSERT INTO roles (role_name) VALUES ('ROLE_TRAINER');
INSERT INTO roles (role_name) VALUES ('ROLE_TRAINEE');

INSERT INTO users (full_name, email, img_ava, phone, emergency_phone, address, account, password, date_of_birth, status)
VALUES
    ('Phương Diệu', 'phuongdieu@example.com', 'path/to/image_phuongdieu.jpg', '0123456784', '0987654326', 'Hà Nội', 'phuongdieu', '$2b$12$9gDUVpWVXRgQaWx7mkYMyujFyU1V4W7lRtIiYBPt/2dz7dUwBj5Ee', '1995-06-15', TRUE),
    ('Nguyễn Văn A', 'nguyenvana@example.com', 'path/to/imageA.jpg', '0123456789', '0987654321', 'Hà Nội', 'nguyenvana', '$2b$12$A6hJc3z4X1gVZCqZs4nLBe/ER2OL6X5IbZfQ6CP3noCgg3d1Qp3Fq', '1990-01-01', TRUE),
    ('Trần Thị B', 'tranthib@example.com', 'path/to/imageB.jpg', '0123456780', '0987654322', 'TP. Hồ Chí Minh', 'tranthib', '$2b$12$5vUyIfA0L7Z2m8Rg2TyQxeJbqQZTQz3uZz5rI5g5pOQ3G2Ae7OsVK', '1992-02-02', TRUE),
    ('Lê Văn C', 'levanc@example.com', 'path/to/imageC.jpg', '0123456781', '0987654323', 'Đà Nẵng', 'levanc', '$2b$12$Y2i5H4H8c/zN9.h4TnFW3OQOKXxH.3e8i6PHyB/6AP4ePHm4hXDOO', '1993-03-03', FALSE);

INSERT INTO curriculums (curriculum_name, descriptions, created_date, status)
VALUES
    ('Curriculum A', 'Description for Curriculum A', NOW(), TRUE),
    ('Curriculum B', 'Description for Curriculum B', NOW(), TRUE),
    ('Curriculum C', 'Description for Curriculum C', NOW(), FALSE);*/

/*INSERT INTO user_roles (user_id, role_id) VALUES
                                              (4, 16),  -- User Nguyễn Văn A, Role ROLE_ADMIN
                                              (5, 17),  -- User Trần Thị B, Role ROLE_COORDINATOR
                                              (6, 18),  -- User Lê Văn C, Role ROLE_CLASS_ADMIN
                                              (7, 19);
*/
/*INSERT INTO users (full_name, email, img_ava, phone, emergency_phone, address, account, password, date_of_birth, status)
VALUES
    ('Nguyễn Văn', 'nguyenvan@example.com', 'path/to/imageA.jpg', '0123856789', '0987654321', 'Hà Nội', 'nguyenvan', '$2b$12$D9POmuc2B37KlY5eDp02O.Jg18U1gg7JDk4mZGuBWG83m90EJ/Q6y', '1990-01-01', TRUE);



*//*
INSERT INTO roles (role_name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (role_name) VALUES ('ROLE_COORDINATOR');
INSERT INTO roles (role_name) VALUES ('ROLE_CLASS_ADMIN');
INSERT INTO roles (role_name) VALUES ('ROLE_TRAINER');
INSERT INTO roles (role_name) VALUES ('ROLE_TRAINEE');*/
/*
INSERT
INTO user_roles (user_id, role_id) VALUES
                                              (4, 26);  -- User Nguyễn Văn A, Role ROLE_ADMIN
*/

/*INSERT INTO subject (subject_code, subject_name, document_link, weight_percentage, status, descriptions)
VALUES
    ('SUB001', 'Math Fundamentals', 'https://example.com/math-fundamentals', 20.00, TRUE, 'This is a basic math course.'),
    ('SUB002', 'Physics Basics', 'https://example.com/physics-basics', 25.50, TRUE, 'Introduction to physics.'),
    ('SUB003', 'Chemistry 101', 'https://example.com/chemistry-101', 15.75, TRUE, 'Basic chemistry course for beginners.'),
    ('SUB004', 'Computer Science', 'https://example.com/computer-science', 30.00, TRUE, 'Fundamentals of computer science.'),
    ('SUB005', 'History Overview', 'https://example.com/history-overview', 10.50, TRUE, 'A brief history overview course.');
*/

/*INSERT INTO curriculum_subject (curriculum_id, subject_id)
VALUES
    (1, 1),  -- Curriculum 1 liên kết với Subject 1
    (1, 2),  -- Curriculum 1 liên kết với Subject 2
    (1, 3),  -- Curriculum 2 liên kết với Subject 1
    (2, 3),  -- Curriculum 2 liên kết với Subject 3
    (2, 4);  -- Curriculum 3 liên kết với Subject 4
*/

-- INSERT INTO roles (role_name, roleLevel)
-- VALUES ('ROLE_ADMIN', 1),
--        ('ROLE_COORDINATOR', 2),
--        ('ROLE_CLASS_ADMIN', 3),
--        ('ROLE_TRAINER', 4),
--        ('ROLE_TRAINEE', 5);
--
-- INSERT INTO users (full_name, email, img_ava, phone, emergency_phone, address, account, password, date_of_birth)
-- VALUES ('Stephanie Lopez', 'wilsonamy@marshall-meyer.com', 'https://placeimg.com/722/947/any', '2917912089',
--         '9601106045', '7436 Adam Street\nLake Michael, NJ 09321', 'robinsonandre',
--         '$2a$12$9FdYW2ple4pyNWI1PmyTMOWot/y8IKewuYvp6chuOfmhsfx28p8Ji', '1995-09-24'),
--        ('Connie Evans', 'rogersjohnny@ross.info', 'https://placeimg.com/222/743/any', '0699354520', '6209852834',
--         '209 Hernandez Green Suite 531\nLake Nicole, PA 97890', 'riveralisa',
--         '$2a$12$9FdYW2ple4pyNWI1PmyTMOWot/y8IKewuYvp6chuOfmhsfx28p8Ji', '1966-01-15'),
--        ('Daniel Scott', 'lopezderek@yahoo.com', 'https://dummyimage.com/440x932', '1510235440', '7767217018',
--         '0312 Samuel Shore Suite 564\nSerranoview, OH 29741', 'qtorres',
--         '$2a$12$9FdYW2ple4pyNWI1PmyTMOWot/y8IKewuYvp6chuOfmhsfx28p8Ji', '1975-11-23'),
--        ('Willie Montgomery', 'amanda41@yahoo.com', 'https://dummyimage.com/760x406', '8789394256', '8345585199',
--         '60904 Adkins Walk Apt. 432\nStokeston, MT 54742', 'dunnpaul',
--         '$2a$12$9FdYW2ple4pyNWI1PmyTMOWot/y8IKewuYvp6chuOfmhsfx28p8Ji', '1989-09-09'),
--        ('Teresa Flowers', 'bcasey@yahoo.com', 'https://placekitten.com/867/25', '7775645821', '4291632616',
--         '1162 Odom Via\nLake Henry, OR 07088', 'seansalinas',
--         '$2a$12$9FdYW2ple4pyNWI1PmyTMOWot/y8IKewuYvp6chuOfmhsfx28p8Ji', '1984-07-07'),
--        ('Louis Roberts', 'qmoore@hotmail.com', 'https://placekitten.com/204/100', '4833778903', '6496741428',
--         '819 Kendra Isle\nJonesshire, KY 50147', 'mirandalisa',
--         '$2a$12$9FdYW2ple4pyNWI1PmyTMOWot/y8IKewuYvp6chuOfmhsfx28p8Ji', '1987-11-13'),
--        ('Tracy Skinner', 'jordanrachel@gmail.com', 'https://placekitten.com/373/643', '4194406064', '5809117064',
--         '732 Blackwell Roads\nPort John, MD 96872', 'larsonashley',
--         '$2a$12$9FdYW2ple4pyNWI1PmyTMOWot/y8IKewuYvp6chuOfmhsfx28p8Ji', '1991-04-17'),
--        ('Kathryn Norman', 'morrowkimberly@gmail.com', 'https://dummyimage.com/499x951', '2117792012', '2289690611',
--         '76757 Richard Forges\nPort Christianview, CO 76064', 'williamjones',
--         '$2a$12$9FdYW2ple4pyNWI1PmyTMOWot/y8IKewuYvp6chuOfmhsfx28p8Ji', '1973-03-26'),
--        ('Joel Turner', 'leemcdonald@yahoo.com', 'https://placeimg.com/674/174/any', '3084146805', '7097834320',
--         '069 Sanders Forges\nNorth James, RI 69903', 'rcarlson',
--         '$2a$12$9FdYW2ple4pyNWI1PmyTMOWot/y8IKewuYvp6chuOfmhsfx28p8Ji', '1975-12-03');
-- INSERT INTO user_roles (user_id, role_id)
-- VALUES (1, 1),
--        (2, 2),
--        (3, 3),
--        (4, 4),
--        (5, 1),
--        (6, 2),
--        (7, 3),
--        (8, 4),
--        (9, 5);