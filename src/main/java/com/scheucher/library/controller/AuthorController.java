package com.scheucher.library.controller;

import com.scheucher.library.entity.Author;
import com.scheucher.library.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<Author> getAllAuthors(){
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author){
        return authorService.saveAuthor(author);
    }

    @PostMapping("/batch")
    public List<Author> createAuthors(@Valid @RequestBody List<Author> authors) {
        return authorService.saveAuthors(authors);
    }
}
