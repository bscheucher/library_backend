package com.scheucher.library.repository;

import com.scheucher.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // ✅ ADD: Fetch all authors with books eagerly
    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books")
    List<Author> findAllWithBooks();

    // ✅ ADD: Fetch single author with books eagerly
    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books WHERE a.id = :id")
    Optional<Author> findByIdWithBooks(Long id);

    List<Author> findByLastName(String lastName);
    List<Author> findByFirstNameAndLastName(String firstName, String lastName);
}