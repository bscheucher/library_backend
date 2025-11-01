package com.scheucher.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorCreateRequest {
    @NotBlank(message = "First name is required")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
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