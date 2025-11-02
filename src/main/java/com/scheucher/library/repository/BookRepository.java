// BookRepository.java
package com.scheucher.library.repository;

import com.scheucher.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Add this import
import org.springframework.stereotype.Repository;

import java.util.List; // Add this import
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Added for getAllBooks to fetch authors eagerly
    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors")
    List<Book> findAllWithAuthors(); // New method

    // Added for getBookById to fetch authors eagerly
    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.authors WHERE b.id = :id")
    Optional<Book> findByIdWithAuthors(Long id); // New method

    Optional<Book> findByIsbn(String isbn);

}