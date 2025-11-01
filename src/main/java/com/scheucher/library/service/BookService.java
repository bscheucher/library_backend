package com.scheucher.library.service;

import com.scheucher.library.dto.request.BookCreateRequest;
import com.scheucher.library.dto.response.BookResponse;
import com.scheucher.library.entity.Author;
import com.scheucher.library.entity.Book;
import com.scheucher.library.exception.BadRequestException;
import com.scheucher.library.exception.DuplicateResourceException;
import com.scheucher.library.exception.ResourceNotFoundException;
import com.scheucher.library.mapper.BookMapper;
import com.scheucher.library.repository.AuthorRepository;
import com.scheucher.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        return bookMapper.toResponse(book);
    }

    @Transactional
    public BookResponse createBook(BookCreateRequest request) {
        // Check if ISBN already exists
        if (bookRepository.findByIsbn(request.getIsbn()).isPresent()) {
            throw new DuplicateResourceException("Book", "ISBN", request.getIsbn());
        }

        Book book = bookMapper.toEntity(request);

        // Handle author associations
        if (request.getAuthorIds() != null && !request.getAuthorIds().isEmpty()) {
            Set<Author> authors = new HashSet<>(authorRepository.findAllById(request.getAuthorIds()));

            // Validate that all author IDs exist
            if (authors.size() != request.getAuthorIds().size()) {
                throw new BadRequestException("One or more author IDs are invalid");
            }

            book.setAuthors(authors);
        }

        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponse(savedBook);
    }

    @Transactional
    public BookResponse updateBook(Long id, BookCreateRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

        // Check if ISBN is being changed to one that already exists
        if (!book.getIsbn().equals(request.getIsbn())) {
            bookRepository.findByIsbn(request.getIsbn()).ifPresent(existingBook -> {
                throw new DuplicateResourceException("Book", "ISBN", request.getIsbn());
            });
        }

        bookMapper.updateEntityFromDto(request, book);

        // Update author associations if provided
        if (request.getAuthorIds() != null) {
            Set<Author> authors = new HashSet<>(authorRepository.findAllById(request.getAuthorIds()));

            // Validate that all author IDs exist
            if (authors.size() != request.getAuthorIds().size()) {
                throw new BadRequestException("One or more author IDs are invalid");
            }

            book.setAuthors(authors);
        }

        Book updatedBook = bookRepository.save(book);
        return bookMapper.toResponse(updatedBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book", "id", id);
        }
        bookRepository.deleteById(id);
    }

    // Batch operations
    @Transactional
    public List<BookResponse> createBooks(List<BookCreateRequest> requests) {
        List<Book> books = requests.stream()
                .map(bookMapper::toEntity)
                .collect(Collectors.toList());

        List<Book> savedBooks = bookRepository.saveAll(books);

        return savedBooks.stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }
}