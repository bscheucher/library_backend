package com.scheucher.library.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AuthorResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String biography;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private String nationality;
    private String website;
    private String email;
    private Integer bookCount;
    private Integer age;
}