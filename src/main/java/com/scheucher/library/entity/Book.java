package com.scheucher.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(97[89])?[0-9]{9}[0-9X]$|^(97[89]-?)?[0-9]{1,5}-?[0-9]{1,7}-?[0-9]{1,7}-?[0-9X]$",
            message = "Invalid ISBN format")
    @Column(unique = true, nullable = false)
    private String isbn;

    @NotBlank(message = "Title is required")
    @Size(max = 255)
    @Column(nullable = false)
    private String title;

    @Size(max = 500)
    private String description;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "number_of_pages")
    private Integer numberOfPages;

    @Column(name = "language")
    private String language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status = BookStatus.AVAILABLE;

    @Column(name = "total_copies")
    private Integer totalCopies = 1;

    @Column(name = "available_copies")
    private Integer availableCopies = 1;

    // Many-to-Many relationship with Author
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @ToString.Exclude
    private Set<Author> authors = new HashSet<>();

    // One-to-Many relationship with Loan
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Loan> loans = new HashSet<>();

    // Category/Genre
    @Column(name = "category")
    private String category;

    @Column(name = "location")
    private String location; // Physical location in library (e.g., "Shelf A-23")

    // Audit fields
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }

    // Helper method to add an author
    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }

    // Helper method to remove an author
    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
    }
}
