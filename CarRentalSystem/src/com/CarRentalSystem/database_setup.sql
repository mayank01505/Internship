-- ========================================
-- CAR RENTAL SYSTEM - DATABASE SETUP
-- ========================================
-- This file contains SQL commands to set up the database
-- Run these commands in MySQL Workbench or MySQL Command Line

-- Step 1: Create the database
CREATE DATABASE IF NOT EXISTS car_rental_db;

-- Step 2: Use the database
USE car_rental_db;

-- Step 3: Create the cars table
CREATE TABLE IF NOT EXISTS cars (
    plate_no VARCHAR(20) PRIMARY KEY,           -- Unique plate number
    model VARCHAR(50) NOT NULL,                  -- Car model name
    category VARCHAR(20) NOT NULL,               -- SUV, Sedan, or Hatchback
    rate_per_day DOUBLE NOT NULL,                -- Daily rental rate in Rupees
    status VARCHAR(20) DEFAULT 'Available'       -- Available or Rented
);

-- Step 4: Insert sample data (you can add more cars)
INSERT INTO cars (plate_no, model, category, rate_per_day, status) VALUES
('MH01AB1234', 'Maruti Suzuki Brezza', 'SUV', 2500.00, 'Available'),
('MH02CD5678', 'Hyundai Creta', 'SUV', 3000.00, 'Available'),
('DL03EF9012', 'Honda City', 'Sedan', 2000.00, 'Available'),
('KA04GH3456', 'Maruti Suzuki Dzire', 'Sedan', 1800.00, 'Available'),
('TN05IJ7890', 'Hyundai i20', 'Hatchback', 1500.00, 'Available'),
('GJ06KL2345', 'Maruti Suzuki Swift', 'Hatchback', 1400.00, 'Available'),
('RJ07MN6789', 'Mahindra Scorpio', 'SUV', 2800.00, 'Available'),
('UP08OP0123', 'Tata Nexon', 'SUV', 2600.00, 'Available'),
('MP09QR4567', 'Hyundai Verna', 'Sedan', 2200.00, 'Available'),
('WB10ST8901', 'Tata Altroz', 'Hatchback', 1600.00, 'Available');

-- Step 5: Verify the data
SELECT * FROM cars;

-- ========================================
-- Additional useful commands:
-- ========================================

-- To view only available cars:
-- SELECT * FROM cars WHERE status = 'Available';

-- To view cars by category:
-- SELECT * FROM cars WHERE category = 'SUV';

-- To check rented cars:
-- SELECT * FROM cars WHERE status = 'Rented';
