truncate table users CASCADE;
truncate table blacklisted_tokens CASCADE;

-- INSERT INTO user_authorities(user_id, authorities)VALUES
--                                           (1, 'USER'),
--                                           (2, 'ADMIN');

insert into blacklisted_tokens (id, token, expires_at, blacklisted_at) values
                                                                           (200, 'eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJqd3QtcHJvamVjdCIsImlhdCI6MTcyMDg3NTc0NywiZXhwIjoxNzIwODc5MzQ3LCJzdWIiOiJhZG1pbiIsInByaW5jaXBhbCI6ImFkbWluIiwiY3JlZGVudGlhbHMiOiJbUFJPVEVDVEVEXSIsInJvbGVzIjpbIkFETUlOIl19.QGMnCxOGMbG8-BoPz2Zf2OGiTAy4iVC0mUKhzKS6vj007zNSMMNTrQBVnFQrzQPJg6mMw2xu6ZxVNS9EmLo2s19fFGMPKXGaEx4UWq8W-w_XyQG-oS6916pz7dNb0twKt8T9VvCe-uicllXjx3-ok5M-L-cpyE3_3Bc-aTMWfzGI0FU6Vi1zTykE_NvvKvJUKVyMIWlV7JV3pbPZTgzjdRCkkU-pw7WOajTJU54ngNI89-wq51oH9yyBlQukiURGLugpXpv6y8EXjZA-s21USLOWPyaGx5_ae1Ac8qft4-a9yLH5YJh-hw39kHG7PG-PNd7d5NLhf3kaahgdAB_99g',
                                                                            '2024-07-13T14:02:27.425305100Z', '2024-07-13T14:02:27.434315200'),
                                                                           (201, 'eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJqd3QtcHJvamVjdCIsImlhdCI6MTcyMDg3NTc0NywiZXhwIjoxNzIwODc5MzQ3LCJzdWIiOiJhZG1pbiIsInByaW5jaXBhbCI6ImFkbWluIiwiY3JlZGVudGlhbHMiOiJbUFJPVEVDVEVEXSIsInJvbGVzIjpbIkFETUlOIl19.QGMnCxOGMbG8-BoPz2Zf2OGiTAy4iVC0mUKhzKS6vj007zNSMMNTrQBVnFQrzQPJg6mMw2xu6ZxVNS9EmLo2s19fFGMPKXGaEx4UWq8W-w_XyQG-oS6916pz7dNb0twKt8T9VvCe-uicllXjx3-ok5M-L-cpyE3_3Bc-aTMWfzGI0FU6Vi1zTykE_NvvKvJUKVyMIWlV7JV3pbPZTgzjdRCkkU-pw7WOajTJU54ngNI89-wq51oH9yyBlQukiURGLugpXpv6y8EXjZA-s21USLOWPyaGx5_ae1Ac8qft4-a9yLH5YJh-hw39kHG7PG-PNd7d5NLhf3kaahgdAB_99g',
                                                                            '2024-07-13T14:02:27.425305100Z', '2024-07-13T14:02:27.434315200');
insert into users(id, username, email, password, time_created, time_updated) values
                                                               (10, 'user', 'michael@gmail.com', '$2a$10$6rIpDTj3/hiYiHdnzooaWuSjGTZT8C88aIuRlo9Lph./ZY71fsl5S', '2024-07-02 23:41:10.614686', '2024-07-02 23:41:10.614686'),
                                                               (20, 'admin', 'michael23@gmail.com', '$2a$10$6rIpDTj3/hiYiHdnzooaWuSjGTZT8C88aIuRlo9Lph./ZY71fsl5S', '2024-07-02 23:41:10.614686', '2024-07-02 23:41:10.614686');


insert into waste(waste_id, user_id, agent_id, price, quantity, type, url, time_created, time_updated) values
                                                                                                           (100, 2, 12, 400.00, '10kg', 'PLASTIC', 'https://www.cloudinary.com/waste1', '2024-07-04T15:03:03.792009700', '2024-07-04T15:03:03.792009700'),
                                                                                                           (400, 2, 10, 600.00, '10kg', 'POLYTHENEBAG', 'https://www.cloudinary.com/waste2', '2024-07-04T15:03:03.792009700', '2024-07-04T15:03:03.792009700'),
                                                                                                           (600, 2, 6, 700.00, '10kg', 'PLASTIC', 'https://www.cloudinary.com/waste3', '2024-07-04T15:03:03.792009700', '2024-07-04T15:03:03.792009700');
