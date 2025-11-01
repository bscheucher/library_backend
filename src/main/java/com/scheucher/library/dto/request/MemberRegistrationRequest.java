package com.scheucher.library.dto.request;

import com.scheucher.library.entity.MembershipType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MemberRegistrationRequest {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String phoneNumber;
    private LocalDate dateOfBirth;
    private MembershipType membershipType = MembershipType.STANDARD;

    // Address fields
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}