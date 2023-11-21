-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_role enum('ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN'),
    status enum('ACTIVE', 'DELETE'));

CREATE TABLE files (
    id INT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255),
    location VARCHAR(255),
    status enum('ACTIVE', 'DELETE'));

CREATE TABLE
events (
id INT AUTO_INCREMENT PRIMARY KEY,
event_name VARCHAR(255),
user_id INT NOT NULL,
file_id INT NOT NULL,
status enum('ACTIVE', 'DELETE'),
FOREIGN KEY (file_id) REFERENCES files (id),
FOREIGN KEY (user_id) REFERENCES users (id));