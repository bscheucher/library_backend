package com.scheucher.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class BookCreateRequest {
    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(97[89])?[0-9]{9}[0-9X]$|^(97[89]-?)?[0-9]{1,5}-?[0-9]{1,7}-?[0-9]{1,7}-?[0-9X]$",
            message = "Invalid ISBN format")
    private String isbn;

    @NotBlank(message = "Title is required")
    @Size(max = 255)
    private String title;

    @Size(max = 500)
    private String description;

    private LocalDate publicationDate;
    private String publisher;
    private Integer numberOfPages;
    private String language;
    private Integer totalCopies = 1;
    private String category;
    private String location;
    private Set<Long> authorIds;
}