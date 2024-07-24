truncate table waste cascade;
truncate table users cascade;

insert into users(id, username, email, password, phone_number, time_created, time_updated) values
(2, 'solomon', 'john@example.com', 'password123', '1234567890', '2024-07-04T15:03:03.792009700', '2024-07-04T15:03:03.792009700');

insert into waste(waste_id, user_id, agent_id, price, quantity, type, url, time_created, time_updated) values
(100, 2, 12, 400.00, '10kg', 'PLASTIC', 'https://www.cloudinary.com/waste1', '2024-07-04T15:03:03.792009700', '2024-07-04T15:03:03.792009700'),
(400, 2, 10, 600.00, '10kg', 'NYLON', 'https://www.cloudinary.com/waste2', '2024-07-04T15:03:03.792009700', '2024-07-04T15:03:03.792009700'),
(600, 2, 6, 700.00, '10kg', 'PLASTIC', 'https://www.cloudinary.com/waste3', '2024-07-04T15:03:03.792009700', '2024-07-04T15:03:03.792009700');
