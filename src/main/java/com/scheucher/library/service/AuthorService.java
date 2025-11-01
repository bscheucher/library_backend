package com.scheucher.library.service;

import com.scheucher.library.dto.request.AuthorCreateRequest;
import com.scheucher.library.dto.request.AuthorUpdateRequest;
import com.scheucher.library.dto.response.AuthorResponse;
import com.scheucher.library.entity.Author;
import com.scheucher.library.mapper.AuthorMapper;
import com.scheucher.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AuthorResponse getAuthorById(Long id) {
        Author author = authorRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        return authorMapper.toResponse(author);
    }

    @Transactional  // Override for write operation
    public AuthorResponse createAuthor(AuthorCreateRequest request) {
        // Convert request DTO to entity
        Author author = authorMapper.toEntity(request);

        // Save the entity
        Author savedAuthor = authorRepository.save(author);

        // Convert saved entity to response DTO
        return authorMapper.toResponse(savedAuthor);
    }

    @Transactional
    public AuthorResponse updateAuthor(Long id, AuthorUpdateRequest request) {
        Author author = authorRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        // Update entity from DTO
        authorMapper.updateEntityFromDto(request, author);

        // Save and return response
        Author updatedAuthor = authorRepository.save(author);
        return authorMapper.toResponse(updatedAuthor);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }

    // Batch operations
    @Transactional
    public List<AuthorResponse> createAuthors(List<AuthorCreateRequest> requests) {
        List<Author> authors = requests.stream()
                .map(authorMapper::toEntity)
                .collect(Collectors.toList());

        List<Author> savedAuthors = authorRepository.saveAll(authors);

        return savedAuthors.stream()
                .map(authorMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Search operations
    public List<AuthorResponse> findAuthorsByLastName(String lastName) {
        return authorRepository.findByLastName(lastName)
                .stream()
                .map(authorMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<AuthorResponse> findAuthorsByName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName)
                .stream()
                .map(authorMapper::toResponse)
                .collect(Collectors.toList());
    }
}