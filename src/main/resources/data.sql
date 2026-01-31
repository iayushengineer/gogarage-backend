-- Car Service Station Database - Sample Data
-- Insert statements for all tables with dummy data

-- Clear existing data (in reverse order due to foreign key constraints)
DELETE FROM feedback;
DELETE FROM service_request;
DELETE FROM customer;
DELETE FROM employee;
DELETE FROM vendor;
DELETE FROM admin;
DELETE FROM users;
DELETE FROM stock;
DELETE FROM offer;
DELETE FROM question;
DELETE FROM service_package;

-- Insert Users data (passwords are BCrypt encoded)
-- admin123 -> $2a$10$3N12wrn/OlcmTKKy49b2BO2nL1ppzW.lo66KRvN.5cUV5/QWv0JlW
-- vendor123 -> $2a$10$2yNhUCbzbk2sH7VGf0VrEuSEJZpCUnkFclCccvEVru9iAkjLtP2We
-- emp123 -> $2a$10$9DHmoLelMRlO4znyxFUvsuPNE1ek5i5muwN5FnfB9L.mtsBbdVb16
-- cust123 -> $2a$10$zNv8cCOJM92Apg30n0TRbOBPkZbGMNptpKYnW7YIChIvglREImp3.
INSERT INTO users (email, password, role, active) VALUES
('admin@cs.com', '$2a$10$3N12wrn/OlcmTKKy49b2BO2nL1ppzW.lo66KRvN.5cUV5/QWv0JlW', 'ADMIN', 1),
('vendor@cs.com', '$2a$10$2yNhUCbzbk2sH7VGf0VrEuSEJZpCUnkFclCccvEVru9iAkjLtP2We', 'VENDOR', 1),
('vendor2@cs.com', '$2a$10$2yNhUCbzbk2sH7VGf0VrEuSEJZpCUnkFclCccvEVru9iAkjLtP2We', 'VENDOR', 1),
('employee@cs.com', '$2a$10$9DHmoLelMRlO4znyxFUvsuPNE1ek5i5muwN5FnfB9L.mtsBbdVb16', 'EMPLOYEE', 1),
('employee2@cs.com', '$2a$10$9DHmoLelMRlO4znyxFUvsuPNE1ek5i5muwN5FnfB9L.mtsBbdVb16', 'EMPLOYEE', 1),
('employee3@cs.com', '$2a$10$9DHmoLelMRlO4znyxFUvsuPNE1ek5i5muwN5FnfB9L.mtsBbdVb16', 'EMPLOYEE', 1),
('customer@cs.com', '$2a$10$zNv8cCOJM92Apg30n0TRbOBPkZbGMNptpKYnW7YIChIvglREImp3.', 'CUSTOMER', 1),
('customer2@cs.com', '$2a$10$zNv8cCOJM92Apg30n0TRbOBPkZbGMNptpKYnW7YIChIvglREImp3.', 'CUSTOMER', 1),
('customer3@cs.com', '$2a$10$zNv8cCOJM92Apg30n0TRbOBPkZbGMNptpKYnW7YIChIvglREImp3.', 'CUSTOMER', 1);

-- Insert Admin data
INSERT INTO admin (name, email, password) VALUES
('System Administrator', 'admin@cs.com', '$2a$10$3N12wrn/OlcmTKKy49b2BO2nL1ppzW.lo66KRvN.5cUV5/QWv0JlW');

-- Insert Vendor data
INSERT INTO vendor (name, address, contact, email, password) VALUES
('AutoCare Services', '123 Main Street, Mumbai, Maharashtra', '9876543210', 'vendor@cs.com', '$2a$10$2yNhUCbzbk2sH7VGf0VrEuSEJZpCUnkFclCccvEVru9iAkjLtP2We'),
('QuickFix Motors', '456 Service Road, Pune, Maharashtra', '9876543211', 'vendor2@cs.com', '$2a$10$2yNhUCbzbk2sH7VGf0VrEuSEJZpCUnkFclCccvEVru9iAkjLtP2We');

-- Insert Employee data
INSERT INTO employee (name, birth_date, email, password, vendor_id) VALUES
('Rajesh Kumar', '1990-05-15', 'employee@cs.com', '$2a$10$9DHmoLelMRlO4znyxFUvsuPNE1ek5i5muwN5FnfB9L.mtsBbdVb16', 1),
('Priya Sharma', '1992-08-20', 'employee2@cs.com', '$2a$10$9DHmoLelMRlO4znyxFUvsuPNE1ek5i5muwN5FnfB9L.mtsBbdVb16', 1),
('Amit Patel', '1988-12-10', 'employee3@cs.com', '$2a$10$9DHmoLelMRlO4znyxFUvsuPNE1ek5i5muwN5FnfB9L.mtsBbdVb16', 2);

-- Insert Customer data
INSERT INTO customer (name, birth_date, contact, address, email, password, employee_id) VALUES
('John Doe', '1985-03-25', '9123456789', '789 Customer Lane, Delhi', 'customer@cs.com', '$2a$10$zNv8cCOJM92Apg30n0TRbOBPkZbGMNptpKYnW7YIChIvglREImp3.', 1),
('Jane Smith', '1990-07-12', '9123456788', '321 Client Street, Bangalore', 'customer2@cs.com', '$2a$10$zNv8cCOJM92Apg30n0TRbOBPkZbGMNptpKYnW7YIChIvglREImp3.', 2),
('Ravi Gupta', '1987-11-30', '9123456787', '654 Service Avenue, Chennai', 'customer3@cs.com', '$2a$10$zNv8cCOJM92Apg30n0TRbOBPkZbGMNptpKYnW7YIChIvglREImp3.', 3);

-- Insert Service Package data
INSERT INTO service_package (package_name, package_description, package_price) VALUES 
('Basic Service', 'Oil change, filter replacement, basic inspection', 2500.00),
('Premium Service', 'Complete service including engine tune-up, brake check, AC service', 5000.00),
('Full Service', 'Comprehensive service with all checks and replacements', 8000.00),
('Emergency Repair', 'Emergency breakdown service and repair', 3000.00),
('Brake Service', 'Complete brake system check and pad replacement', 3500.00),
('AC Service', 'Air conditioning system service and gas refill', 2000.00),
('Engine Tune-up', 'Complete engine performance optimization', 4500.00),
('Transmission Service', 'Transmission fluid change and system check', 3200.00);

-- Insert Stock data
INSERT INTO stock (item_name, price, quantity) VALUES 
('Engine Oil', 500.00, 50),
('Air Filter', 300.00, 30),
('Brake Pads', 1200.00, 20),
('Spark Plugs', 150.00, 100),
('Gear Oil', 400.00, 25),
('Brake Fluid', 250.00, 40),
('Coolant', 350.00, 35),
('Transmission Fluid', 600.00, 15),
('Battery', 4500.00, 10),
('Tire', 3500.00, 20),
('Windshield Wipers', 800.00, 25),
('Headlight Bulb', 200.00, 50);

-- Insert Offer data
INSERT INTO offer (offer_name, offer_discount, min_value) VALUES 
('New Customer Discount', 10.0, 2000.0),
('Premium Service Special', 15.0, 5000.0),
('Loyalty Customer Bonus', 20.0, 8000.0),
('Weekend Special', 12.0, 3000.0),
('Monsoon Service Offer', 18.0, 4000.0),
('Festival Discount', 25.0, 6000.0);

-- Insert Question data (FAQ)
INSERT INTO question (question) VALUES 
('How often should I service my car?'),
('What is included in a basic service?'),
('Do you provide pickup and drop service?'),
('What are your operating hours?'),
('Do you service all car brands?'),
('How long does a typical service take?'),
('Do you provide warranty on services?'),
('Can I track my service request status?'),
('What payment methods do you accept?'),
('Do you have emergency breakdown service?');

-- Insert Service Request data
INSERT INTO service_request (vehicle_type, vehicle_model, vehicle_brand, vehicle_reg_no, 
                           service_date, out_date, delivery_type, customer_id, status, 
                           labour_charges, discount, product_charges, total, package_id) VALUES 
('CAR', 'Swift', 'Maruti', 'MH 12 AB 1234', '2024-01-15', '2024-01-16', 'PICKUP', 
 1, 'COMPLETED', 1500.00, 0.9, 1000.00, 2250.00, 1),
('CAR', 'City', 'Honda', 'KA 05 CD 5678', '2024-02-10', NULL, 'DROPBY', 
 2, 'PENDING', 3000.00, 1.0, 2000.00, 5000.00, 2),
('BIKE', 'Pulsar', 'Bajaj', 'TN 09 EF 9012', '2024-02-20', NULL, 'PICKUP', 
 3, 'PENDING', 800.00, 1.0, 500.00, 1300.00, 1),
('CAR', 'Verna', 'Hyundai', 'DL 01 GH 3456', '2024-01-25', '2024-01-26', 'DROPBY', 
 1, 'COMPLETED', 2500.00, 0.85, 1500.00, 3400.00, 3),
('CAR', 'Innova', 'Toyota', 'UP 32 IJ 7890', '2024-02-05', NULL, 'PICKUP', 
 2, 'IN_PROGRESS', 2000.00, 1.0, 800.00, 2800.00, 4);

-- Insert Feedback data
INSERT INTO feedback (customer_id, feedback_message) VALUES 
(1, 'Excellent service! Very satisfied with the quality of work.'),
(2, 'Good service but could be faster. Overall happy with the experience.'),
(3, 'Professional staff and reasonable pricing. Will recommend to others.'),
(1, 'Second visit was even better. Great improvement in service quality.'),
(2, 'Pickup and drop service is very convenient. Thank you!');

COMMIT;