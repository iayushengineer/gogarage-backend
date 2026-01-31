-- Car Service Station Database Schema
-- DDL for all tables based on JPA entities

-- Drop tables if they exist (in reverse order due to foreign key constraints)
DROP TABLE IF EXISTS feedback;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS offer;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS service_request;
DROP TABLE IF EXISTS service_package;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS vendor;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS users;

-- Create Users table (Base authentication table)
CREATE TABLE users (
    email VARCHAR(70) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    active INT DEFAULT 1 NOT NULL
);

-- Create Admin table
CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    email VARCHAR(70) NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- Create Vendor table
CREATE TABLE vendor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    address VARCHAR(200),
    contact VARCHAR(30),
    email VARCHAR(45) NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- Create Employee table
CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    birth_date DATE,
    email VARCHAR(45) NOT NULL,
    password VARCHAR(100) NOT NULL,
    vendor_id INT NOT NULL,
    FOREIGN KEY (vendor_id) REFERENCES vendor(id) ON DELETE CASCADE
);

-- Create Customer table
CREATE TABLE customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    birth_date DATE,
    contact VARCHAR(20),
    address VARCHAR(200),
    email VARCHAR(45) NOT NULL,
    password VARCHAR(100) NOT NULL,
    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE SET NULL
);

-- Create Service Package table
CREATE TABLE service_package (
    package_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    package_name VARCHAR(100) NOT NULL,
    package_description VARCHAR(500),
    package_price DOUBLE NOT NULL
);

-- Create Service Request table
CREATE TABLE service_request (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_type VARCHAR(45),
    vehicle_model VARCHAR(45),
    vehicle_brand VARCHAR(45),
    vehicle_reg_no VARCHAR(45),
    service_date DATE,
    out_date DATE,
    delivery_type VARCHAR(20),
    customer_id INT NOT NULL,
    status VARCHAR(20),
    labour_charges DOUBLE DEFAULT 0.0,
    discount DOUBLE DEFAULT 1.0,
    product_charges DOUBLE DEFAULT 0.0,
    total DOUBLE DEFAULT 0.0,
    package_id INT,
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE,
    FOREIGN KEY (package_id) REFERENCES service_package(package_id) ON DELETE SET NULL
);

-- Create Stock table
CREATE TABLE stock (
    stock_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(30) NOT NULL UNIQUE,
    price DOUBLE NOT NULL,
    quantity INT NOT NULL
);

-- Create Feedback table
CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    feedback_message VARCHAR(100),
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);

-- Create Offer table
CREATE TABLE offer (
    offer_id INT AUTO_INCREMENT PRIMARY KEY,
    offer_name VARCHAR(100) NOT NULL,
    offer_discount DOUBLE NOT NULL,
    min_value DOUBLE NOT NULL
);

-- Create Question table (FAQ)
CREATE TABLE question (
    question_id INT AUTO_INCREMENT PRIMARY KEY,
    question VARCHAR(100) NOT NULL
);

-- Create indexes for better performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_admin_email ON admin(email);
CREATE INDEX idx_vendor_email ON vendor(email);
CREATE INDEX idx_employee_email ON employee(email);
CREATE INDEX idx_employee_vendor ON employee(vendor_id);
CREATE INDEX idx_customer_email ON customer(email);
CREATE INDEX idx_customer_employee ON customer(employee_id);
CREATE INDEX idx_service_request_customer ON service_request(customer_id);
CREATE INDEX idx_service_request_package ON service_request(package_id);
CREATE INDEX idx_service_request_status ON service_request(status);
CREATE INDEX idx_service_request_date ON service_request(service_date);
CREATE INDEX idx_stock_item_name ON stock(item_name);
CREATE INDEX idx_feedback_customer ON feedback(customer_id);
CREATE INDEX idx_offer_min_value ON offer(min_value);

-- Insert default admin user
INSERT INTO users (email, password, role, active) VALUES 
('admin@carservice.com', 'admin123', 'ADMIN', 1);

INSERT INTO admin (name, email, password) VALUES 
('System Administrator', 'admin@carservice.com', 'admin123');

-- Insert sample vendor
INSERT INTO users (email, password, role, active) VALUES 
('vendor@carservice.com', 'vendor123', 'VENDOR', 1);

INSERT INTO vendor (name, address, contact, email, password) VALUES 
('AutoCare Services', '123 Main Street, Mumbai, Maharashtra', '9876543210', 'vendor@carservice.com', 'vendor123');

-- Insert sample employee
INSERT INTO users (email, password, role, active) VALUES 
('employee@carservice.com', 'emp123', 'EMPLOYEE', 1);

INSERT INTO employee (name, birth_date, email, password, vendor_id) VALUES 
('Rajesh Kumar', '1990-05-15', 'employee@carservice.com', 'emp123', 1);

-- Insert sample customer
INSERT INTO users (email, password, role, active) VALUES 
('customer@carservice.com', 'cust123', 'CUSTOMER', 1);

INSERT INTO customer (name, birth_date, contact, address, email, password, employee_id) VALUES 
('John Doe', '1985-03-25', '9123456789', '789 Customer Lane, Delhi', 'customer@carservice.com', 'cust123', 1);

-- Insert sample service packages
INSERT INTO service_package (package_name, package_description, package_price) VALUES 
('Basic Service', 'Oil change, filter replacement, basic inspection', 2500.00),
('Premium Service', 'Complete service including engine tune-up, brake check, AC service', 5000.00),
('Full Service', 'Comprehensive service with all checks and replacements', 8000.00),
('Emergency Repair', 'Emergency breakdown service and repair', 3000.00);

-- Insert sample stock items
INSERT INTO stock (item_name, price, quantity) VALUES 
('Engine Oil', 500.00, 50),
('Air Filter', 300.00, 30),
('Brake Pads', 1200.00, 20),
('Spark Plugs', 150.00, 100),
('Gear Oil', 400.00, 25);

-- Insert sample offers
INSERT INTO offer (offer_name, offer_discount, min_value) VALUES 
('New Customer Discount', 10.0, 2000.0),
('Premium Service Special', 15.0, 5000.0),
('Loyalty Customer Bonus', 20.0, 8000.0);

-- Insert sample questions (FAQ)
INSERT INTO question (question) VALUES 
('How often should I service my car?'),
('What is included in a basic service?'),
('Do you provide pickup and drop service?'),
('What are your operating hours?'),
('Do you service all car brands?');

-- Insert sample service request
INSERT INTO service_request (vehicle_type, vehicle_model, vehicle_brand, vehicle_reg_no, 
                           service_date, out_date, delivery_type, customer_id, status, 
                           labour_charges, discount, product_charges, total, package_id) VALUES 
('CAR', 'Swift', 'Maruti', 'MH 12 AB 1234', '2024-01-15', '2024-01-16', 'PICKUP', 
 1, 'COMPLETED', 1500.00, 0.9, 1000.00, 2250.00, 1);

-- Insert sample feedback
INSERT INTO feedback (customer_id, feedback_message) VALUES 
(1, 'Excellent service! Very satisfied with the quality of work.');

COMMIT;