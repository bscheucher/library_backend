// BookService.java
package com.scheucher.library.service;

import com.scheucher.library.entity.Book;
import com.scheucher.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id){
        return bookRepository.
                findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> saveAllBooks(List<Book> books){
        return bookRepository.saveAll(books);
    }
}