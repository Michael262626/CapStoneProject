-- Truncate tables
truncate table users cascade;
truncate table payment_pay_stack cascade;
truncate table address cascade;
truncate table blacklisted_tokens cascade;
truncate table admin cascade;
truncate table agents cascade;

-- Insert into address table
INSERT INTO address (id, street_name, city, zip_code, postal_code)
VALUES (60, 'Some Street', 'Some City', '12345', '67890');

-- Insert into agents table
INSERT INTO agents (username, agent_id, password, phone_number, email, address_id, time_created, time_updated)
VALUES ('Agent1', 2, 'password123', '08012345678', 'agent1@gmail.com', 60, '2024-07-04T15:03:03.792009700', '2024-09-04T15:03:03.792009700');

-- Insert additional addresses
INSERT INTO address(id, city, postal_code, street_name, zip_code)
VALUES
    (100, 'lagos', '3333', 'street name', '55555'),
    (101, 'ikeja', '222', 'street name2', '77777');

-- Insert into blacklisted tokens table
INSERT INTO blacklisted_tokens (id, token, expires_at, blacklisted_at)
VALUES
    (200, 'eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9...', '2024-07-13T14:02:27.425305100Z', '2024-07-13T14:02:27.434315200'),
    (201, 'eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9...', '2024-07-13T14:02:27.425305100Z', '2024-07-13T14:02:27.434315200');

-- Insert into users table
INSERT INTO users(user_id, balance, username, phone_number, email, password, time_created, time_updated, address_id)
VALUES
    (10, 0.00, 'user', '4444444', 'michael@gmail.com', '$2a$10$6rIpDTj3/hiYiHdnzooaWuSjGTZT8C88aIuRlo9Lph./ZY71fsl5S', '2024-07-02 23:41:10.614686', '2024-07-02 23:41:10.614686', 101),
    (20, 0.00, 'admin', '4444444', 'michael23@gmail.com', '$2a$10$6rIpDTj3/hiYiHdnzooaWuSjGTZT8C88aIuRlo9Lph./ZY71fsl5S', '2024-07-02 23:41:10.614686', '2024-07-02 23:41:10.614686', 100);

-- Insert into waste table
INSERT INTO waste(waste_id, user_id, agent_id, price, quantity, type, url, time_created, time_updated)
VALUES
    (100, 2, 12, 400.00, '10kg', 'PLASTIC', 'https://www.cloudinary.com/waste1', '2024-07-04T15:03:03.792009700', '2024-07-04T15:03:03.792009700'),
    (400, 2, 10, 600.00, '10kg', 'POLYTHENEBAG', 'https://www.cloudinary.com/waste2', '2024-07-04T15:03:03.792009700', '2024-07-04T15:03:03.792009700'),
    (600, 2, 6, 700.00, '10kg', 'PLASTIC', 'https://www.cloudinary.com/waste3', '2024-07-04T15:03:03.792009700', '2024-07-04T15:03:03.792009700');

-- Insert into admin table
INSERT INTO admin(id, admin_email, admin_password, username, time_created, time_updated)
VALUES
    (300, 'admin@gmail.com', 'pass', 'username', '2024-07-13T14:02:27.425305100Z', '2024-07-13T14:02:27.434315200');

-- Insert into payment_pay_stack table
INSERT INTO payment_pay_stack(id, user_id, reference, amount, gateway_response, paid_at, created_at, channel, currency, ip_address, pricing_plan_type, time_created)
VALUES
    (1, 10, 'pay_12345', 100.00, 'Success', '2024-07-04T15:03:03.792009700', '2024-07-01 10:05:00', 'card', 'NGN', '192.168.0.1', 'PAYMENT', '2024-07-01 10:05:00'),
    (2, 20, 'pay_67890', 250.50, 'Success', '2024-07-04T15:06:03.792009700', '2024-07-01 11:10:00', 'bank', 'NGN', '192.168.0.2', 'PAYMENT', '2024-07-01 11:10:00');
