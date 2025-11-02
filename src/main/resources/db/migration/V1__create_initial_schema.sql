-- Create addresses table
CREATE TABLE addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    postal_code VARCHAR(255),
    country VARCHAR(255)
);

-- Create users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    user_role VARCHAR(50) NOT NULL,
    password VARCHAR(255)
);

-- Create authors table
CREATE TABLE authors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    biography TEXT,
    date_of_birth DATE,
    date_of_death DATE,
    nationality VARCHAR(100),
    website VARCHAR(255),
    email VARCHAR(255),
    created_at DATE NOT NULL,
    updated_at DATE NOT NULL
);

-- Create books table
CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(50) UNIQUE NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    publication_date DATE,
    publisher VARCHAR(255),
    number_of_pages INT,
    language VARCHAR(50),
    status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
    total_copies INT DEFAULT 1,
    available_copies INT DEFAULT 1,
    category VARCHAR(100),
    location VARCHAR(255),
    created_at DATE NOT NULL,
    updated_at DATE NOT NULL
);

-- Create book_authors join table
CREATE TABLE book_authors (
    book_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);

-- Create members table
CREATE TABLE members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_code VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    user_id BIGINT UNIQUE,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    address_id BIGINT,
    date_of_birth DATE,
    membership_type VARCHAR(50) NOT NULL DEFAULT 'STANDARD',
    membership_start_date DATE NOT NULL,
    membership_end_date DATE,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    max_books_allowed INT DEFAULT 5,
    outstanding_fees DOUBLE DEFAULT 0.0,
    created_at DATE NOT NULL,
    updated_at DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (address_id) REFERENCES addresses(id)
);

-- Create employees table
CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    user_id BIGINT UNIQUE,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    address_id BIGINT,
    date_of_birth DATE,
    employment_start_date DATE NOT NULL,
    employment_end_date DATE,
    max_books_allowed INT DEFAULT 5,
    outstanding_fees DOUBLE DEFAULT 0.0,
    created_at DATE NOT NULL,
    updated_at DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (address_id) REFERENCES addresses(id)
);

-- Create loans table
CREATE TABLE loans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    loan_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    renewal_count INT DEFAULT 0,
    max_renewals INT DEFAULT 2,
    fine_amount DOUBLE DEFAULT 0.0,
    fine_paid BOOLEAN DEFAULT FALSE,
    notes TEXT,
    issued_by_employee_id BIGINT,
    returned_to_employee_id BIGINT,
    created_at DATE NOT NULL,
    updated_at DATE NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (issued_by_employee_id) REFERENCES employees(id),
    FOREIGN KEY (returned_to_employee_id) REFERENCES employees(id)
);

-- Create indexes for better performance
CREATE INDEX idx_books_isbn ON books(isbn);
CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_authors_last_name ON authors(last_name);
CREATE INDEX idx_members_email ON members(email);
CREATE INDEX idx_members_member_code ON members(member_code);
CREATE INDEX idx_loans_status ON loans(status);
CREATE INDEX idx_loans_due_date ON loans(due_date);