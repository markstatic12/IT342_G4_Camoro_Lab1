-- ============================================
-- Database Setup for XAMPP/phpMyAdmin
-- Authentication System
-- ============================================

-- Create database
CREATE DATABASE IF NOT EXISTS peertayo_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE peertayo_db;
-- Create users table
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_email (email),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sample data (optional - for testing)
-- Password: password123 (BCrypt hashed)
-- INSERT INTO users (first_name, last_name, email, password, status) 
-- VALUES ('John', 'Doe', 'john@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'active');

-- ============================================
-- Verify setup
-- ============================================
SHOW TABLES;
DESCRIBE users;
