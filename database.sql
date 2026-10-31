-- Run this script in MySQL to create the book management database and books table.
-- book_id is a required manually supplied primary key; it is not AUTO_INCREMENT.
CREATE DATABASE IF NOT EXISTS book_management_db;

USE book_management_db;

CREATE TABLE IF NOT EXISTS books (
    book_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    PRIMARY KEY (book_id),
    CONSTRAINT chk_books_quantity_nonnegative CHECK (quantity >= 0)
);

-- Also remove AUTO_INCREMENT if books already existed with that attribute.
ALTER TABLE books MODIFY book_id INT NOT NULL;
