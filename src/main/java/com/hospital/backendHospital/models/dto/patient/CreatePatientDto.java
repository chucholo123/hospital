package com.hospital.backendHospital.models.dto.patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePatientDto {

    @Size(min = 10, max = 20, message = "Username must be between 10 and 20 characters")
    @NotBlank(message = "Username is required")
    private String username;

    @Size(min = 10, max = 30, message = "First name must be between 10 and 30 characters")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Size(min = 10, max = 30, message = "Last name must be between 10 and 30 characters")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Size(min = 8, message = "The password must be at least 8 characters")
    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Blood type is required")
    private String bloodType;

    @Size(min = 10, max = 10, message = "The emergency contact must be valid")
    @NotBlank(message = "Emergency contact is required")
    private String emergencyContact;
}
