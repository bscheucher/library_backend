package com.scheucher.library.service;

import com.scheucher.library.dto.request.AuthorCreateRequest;
import com.scheucher.library.dto.request.AuthorUpdateRequest;
import com.scheucher.library.dto.response.AuthorResponse;
import com.scheucher.library.entity.Author;
import com.scheucher.library.exception.ResourceNotFoundException;
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
        // ✅ CHANGED: Use eager fetch method
        return authorRepository.findAllWithBooks()
                .stream()
                .map(authorMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AuthorResponse getAuthorById(Long id) {
        // ✅ CHANGED: Use eager fetch method
        Author author = authorRepository.findByIdWithBooks(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));
        return authorMapper.toResponse(author);
    }

    @Transactional
    public AuthorResponse createAuthor(AuthorCreateRequest request) {
        Author author = authorMapper.toEntity(request);
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.toResponse(savedAuthor);
    }

    @Transactional
    public AuthorResponse updateAuthor(Long id, AuthorUpdateRequest request) {
        Author author = authorRepository.findByIdWithBooks(id)  // ✅ CHANGED
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));

        authorMapper.updateEntityFromDto(request, author);

        Author updatedAuthor = authorRepository.save(author);
        return authorMapper.toResponse(updatedAuthor);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author", "id", id);
        }
        authorRepository.deleteById(id);
    }

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
