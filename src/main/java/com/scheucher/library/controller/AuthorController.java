package com.scheucher.library.controller;

import com.scheucher.library.dto.request.AuthorCreateRequest;
import com.scheucher.library.dto.request.AuthorUpdateRequest;
import com.scheucher.library.dto.response.AuthorResponse;
import com.scheucher.library.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody AuthorCreateRequest request) {
        AuthorResponse author = authorService.createAuthor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorUpdateRequest request) {
        return ResponseEntity.ok(authorService.updateAuthor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<List<AuthorResponse>> createAuthors(
            @Valid @RequestBody List<AuthorCreateRequest> requests) {
        List<AuthorResponse> authors = authorService.createAuthors(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(authors);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AuthorResponse>> searchAuthors(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {

        if (firstName != null && lastName != null) {
            return ResponseEntity.ok(authorService.findAuthorsByName(firstName, lastName));
        } else if (lastName != null) {
            return ResponseEntity.ok(authorService.findAuthorsByLastName(lastName));
        } else {
            return ResponseEntity.ok(authorService.getAllAuthors());
        }
    }
}