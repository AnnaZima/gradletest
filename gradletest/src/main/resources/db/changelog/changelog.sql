-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE specialty (
    id INT AUTO_INCREMENT PRIMARY KEY,
    spec_name VARCHAR(255) NOT NULL);