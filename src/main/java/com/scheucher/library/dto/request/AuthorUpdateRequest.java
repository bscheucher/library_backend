package com.scheucher.library.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorUpdateRequest {
    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Size(max = 1000)
    private String biography;

    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private String nationality;
    private String website;
    private String email;
}