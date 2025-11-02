-- Insert sample books
INSERT INTO books (isbn, title, description, publication_date, publisher, number_of_pages, language, status, total_copies, available_copies, category, location, created_at, updated_at)
VALUES
('978-0439708180', 'Harry Potter and the Sorcerer''s Stone', 'A young wizard begins his magical education', '1998-09-01', 'Scholastic', 309, 'English', 'AVAILABLE', 5, 5, 'Fantasy', 'A-101', CURRENT_DATE, CURRENT_DATE),
('978-0451524935', '1984', 'A dystopian social science fiction novel', '1949-06-08', 'Signet Classic', 328, 'English', 'AVAILABLE', 3, 2, 'Fiction', 'B-205', CURRENT_DATE, CURRENT_DATE),
('978-0141439518', 'Pride and Prejudice', 'A romantic novel of manners', '1813-01-28', 'Penguin Classics', 432, 'English', 'AVAILABLE', 4, 4, 'Romance', 'C-310', CURRENT_DATE, CURRENT_DATE),
('978-0684801223', 'The Old Man and the Sea', 'The story of an aging Cuban fisherman', '1952-09-01', 'Scribner', 127, 'English', 'AVAILABLE', 2, 1, 'Fiction', 'B-150', CURRENT_DATE, CURRENT_DATE),
('978-0062073488', 'Murder on the Orient Express', 'A detective novel featuring Hercule Poirot', '1934-01-01', 'William Morrow', 256, 'English', 'BORROWED', 3, 0, 'Mystery', 'D-420', CURRENT_DATE, CURRENT_DATE),
('978-0486280615', 'The Adventures of Huckleberry Finn', 'Adventures of a boy and a runaway slave', '1884-12-10', 'Dover Publications', 224, 'English', 'AVAILABLE', 2, 2, 'Adventure', 'A-215', CURRENT_DATE, CURRENT_DATE),
('978-0450411434', 'The Shining', 'A horror novel about a haunted hotel', '1977-01-28', 'Doubleday', 447, 'English', 'AVAILABLE', 4, 3, 'Horror', 'E-501', CURRENT_DATE, CURRENT_DATE),
('978-0061120084', 'To Kill a Mockingbird', 'A novel about racial injustice in the American South', '1960-07-11', 'HarperCollins', 324, 'English', 'AVAILABLE', 5, 4, 'Fiction', 'B-180', CURRENT_DATE, CURRENT_DATE);

-- Link books to authors
INSERT INTO book_authors (book_id, author_id) VALUES
(1, 1), -- Harry Potter -> J.K. Rowling
(2, 2), -- 1984 -> George Orwell
(3, 3), -- Pride and Prejudice -> Jane Austen
(4, 4), -- The Old Man and the Sea -> Ernest Hemingway
(5, 5), -- Murder on the Orient Express -> Agatha Christie
(6, 6), -- Huckleberry Finn -> Mark Twain
(7, 7), -- The Shining -> Stephen King
(8, 8); -- To Kill a Mockingbird -> Harper Lee


