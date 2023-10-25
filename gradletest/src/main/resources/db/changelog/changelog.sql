-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE test (
    test_id INT AUTO_INCREMENT PRIMARY KEY,
    test_name VARCHAR(255) NOT NULL);