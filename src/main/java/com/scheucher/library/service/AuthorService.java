package com.scheucher.library.service;

import com.scheucher.library.entity.Author;
import com.scheucher.library.repository.AuthorRepository;
import com.scheucher.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    final private AuthorRepository authorRepository;

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    };

    public Author getAuthorById(Long id) {
        return authorRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }

    public List<Author> saveAuthors(List<Author> authors) {
        return authorRepository.saveAll(authors);
    }
}
