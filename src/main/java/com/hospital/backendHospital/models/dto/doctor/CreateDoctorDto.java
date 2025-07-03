package com.hospital.backendHospital.models.dto.doctor;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateDoctorDto {

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 10, max = 30, message = "First name must be between 10 and 30 characters")
    @NotBlank(message = "Username is required")
    private String firstName;

    @Size(min = 10, max = 30, message = "Last name must be between 10 and 30 characters")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Size(min = 8, message = "The password must be at least 8 characters")
    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Specialty is required")
    @Positive(message = "Specialty must be valid")
    private Long specialty;
}
