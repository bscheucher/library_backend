package com.scheucher.library.dto.response;

import com.scheucher.library.entity.BookStatus;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class BookResponse {
    private Long id;
    private String isbn;
    private String title;
    private String description;
    private LocalDate publicationDate;
    private String publisher;
    private Integer numberOfPages;
    private String language;
    private BookStatus status;
    private Integer availableCopies;
    private Integer totalCopies;
    private String category;
    private String location;
    private Set<AuthorSummaryResponse> authors;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
