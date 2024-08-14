TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE payment_pay_stack CASCADE;
TRUNCATE TABLE address CASCADE;
TRUNCATE TABLE blacklisted_tokens CASCADE;
TRUNCATE TABLE admin CASCADE;
TRUNCATE TABLE agents CASCADE;
TRUNCATE TABLE waste CASCADE;

-- Insert data into address table
INSERT INTO address (id, street_name, city, zip_code, postal_code)
VALUES
    (60, 'Some Street', 'Some City', '12345', '67890'),
    (100, 'street name', 'lagos', '55555', '3333'),
    (101, 'street name2', 'ikeja', '77777', '222');

-- Insert data into agents table
INSERT INTO agents (username, id, password, phone_number, email, address_id_id, time_created, time_updated)
VALUES
    ('Agent1', 12, 'password123', '08012345678', 'agent1@gmail.com', 100, '2024-07-04T15:03:03.792009700', '2024-09-04T15:03:03.792009700'),
    ('Agent2', 10, 'password123', '08012345678', 'agent3@gmail.com', 101, '2024-07-04T15:03:03.792009700', '2024-09-04T15:03:03.792009700'),
    ('Agent2', 13, 'password123', '08012345678', 'agent4@gmail.com', 60, '2024-07-04T15:03:03.792009700', '2024-09-04T15:03:03.792009700');


-- Insert data into blacklisted_tokens table
INSERT INTO blacklisted_tokens (id, token, expires_at, blacklisted_at)
VALUES
    (200, 'eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJqd3QtcHJvamVjdCIsImlhdCI6MTcyMDg3NTc0NywiZXhwIjoxNzIwODc5MzQ3LCJzdWIiOiJhZG1pbiIsInByaW5jaXBhbCI6ImFkbWluIiwiY3JlZGVudGlhbHMiOiJbUFJPVEVDVEVEXSIsInJvbGVzIjpbIkFETUlOIl19.QGMnCxOGMbG8-BoPz2Zf2OGiTAy4iVC0mUKhzKS6vj007zNSMMNTrQBVnFQrzQPJg6mMw2xu6ZxVNS9EmLo2s19fFGMPKXGaEx4UWq8W-w_XyQG-oS6916pz7dNb0twKt8T9VvCe-uicllXjx3-ok5M-L-cpyE3_3Bc-aTMWfzGI0FU6Vi1zTykE_NvvKvJUKVyMIWlV7JV3pbPZTgzjdRCkkU-pw7WOajTJU54ngNI89-wq51oH9yyBlQukiURGLugpXpv6y8EXjZA-s21USLOWPyaGx5_ae1Ac8qft4-a9yLH5YJh-hw39kHG7PG-PNd7d5NLhf3kaahgdAB_99g', '2024-07-13T14:02:27.425305100Z', '2024-07-13T14:02:27.434315200'),
    (201, 'eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJqd3QtcHJvamVjdCIsImlhdCI6MTcyMDg3NTc0NywiZXhwIjoxNzIwODc5MzQ3LCJzdWIiOiJhZG1pbiIsInByaW5jaXBhbCI6ImFkbWluIiwiY3JlZGVudGlhbHMiOiJbUFJPVEVDVEVEXSIsInJvbGVzIjpbIkFETUlOIl19.QGMnCxOGMbG8-BoPz2Zf2OGiTAy4iVC0mUKhzKS6vj007zNSMMNTrQBVnFQrzQPJg6mMw2xu6ZxVNS9EmLo2s19fFGMPKXGaEx4UWq8W-w_XyQG-oS6916pz7dNb0twKt8T9VvCe-uicllXjx3-ok5M-L-cpyE3_3Bc-aTMWfzGI0FU6Vi1zTykE_NvvKvJUKVyMIWlV7JV3pbPZTgzjdRCkkU-pw7WOajTJU54ngNI89-wq51oH9yyBlQukiURGLugpXpv6y8EXjZA-s21USLOWPyaGx5_ae1Ac8qft4-a9yLH5YJh-hw39kHG7PG-PNd7d5NLhf3kaahgdAB_99g', '2024-07-13T14:02:27.425305100Z', '2024-07-13T14:02:27.434315200');

-- Insert data into users table
INSERT INTO users (user_id, balance, username, phone_number, email, password, time_created, time_updated, address_id)
VALUES
    (10, 0.00, 'user', '4444444', 'michael@gmail.com', '$2a$10$6rIpDTj3/hiYiHdnzooaWuSjGTZT8C88aIuRlo9Lph./ZY71fsl5S', '2024-07-02 23:41:10.614686', '2024-07-02 23:41:10.614686', 101),
    (20, 0.00, 'admin', '4444444', 'michael23@gmail.com', '$2a$10$6rIpDTj3/hiYiHdnzooaWuSjGTZT8C88aIuRlo9Lph./ZY71fsl5S', '2024-07-02 23:41:10.614686', '2024-07-02 23:41:10.614686', 100);

-- Insert data into waste table
INSERT INTO waste (waste_id, user_id, agent_id, price, quantity, type, url, time_created, time_updated)
VALUES
    (100, 2, 13, 400.00, '10kg', 'PLASTIC', 'https://www.cloudinary.com/waste1', '2024-07-04T15:03:03.792009700', '2024-07-04T15:03:03.792009700'),
    (400, 2, 10, 600.00, '10kg', 'POLYTHENEBAG', 'https://www.cloudinary.com/waste2', '2024-07-04T15:03:03.792009700', '2024-07-04T15:03:03.792009700');


-- Insert data into admin table
INSERT INTO admin (id, admin_email, admin_password, username, time_created, time_updated)
VALUES
    (300, 'admin@gmail.com', 'pass', 'username', '2024-07-13T14:02:27.425305100Z', '2024-07-13T14:02:27.434315200');

-- Insert data into payment_pay_stack table
INSERT INTO payment_pay_stack (id, user_id, reference, amount, gateway_response, paid_at, created_at, channel, currency, ip_address, pricing_plan_type, time_created)
VALUES
    (1, 10, 'pay_12345', 100.00, 'Success', '2024-07-04T15:03:03.792009700', '2024-07-01 10:05:00', 'card', 'NGN', '192.168.0.1', 'PAYMENT', '2024-07-01 10:05:00'),
    (2, 20, 'pay_67890', 250.50, 'Success', '2024-07-04T15:06:03.792009700', '2024-07-01 11:10:00', 'bank', 'NGN', '192.168.0.2', 'PAYMENT', '2024-07-01 11:10:00');