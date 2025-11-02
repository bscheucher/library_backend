package com.scheucher.library.controller;

import com.scheucher.library.dto.request.BookCreateRequest;
import com.scheucher.library.dto.response.BookResponse;
import com.scheucher.library.entity.Book;
import com.scheucher.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> bookResponses = bookService.getAllBooks();
        System.out.println("!!!!!!!!!!!!!!BOOK RESPONSES IN CONTROLLER!!!!!!!!!!!!: " + bookResponses.toString());
        return ResponseEntity.ok(bookResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookCreateRequest request) {
        BookResponse book = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<BookResponse>> createBooks(
            @Valid @RequestBody List<BookCreateRequest> requests) {
        List<BookResponse> books = bookService.createBooks(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookCreateRequest request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}