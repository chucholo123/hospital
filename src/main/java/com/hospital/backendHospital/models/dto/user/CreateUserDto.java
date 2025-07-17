package com.hospital.backendHospital.models.dto.user;

import jakarta.validation.constraints.Email;
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
public class CreateUserDto {

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 10, max = 30, message = "First name must be between 5 and 20 characters")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Size(min = 10, max = 30, message = "Last name must be between 5 and 20 characters")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Size(min = 8, message = "The password must be at least 8 characters")
    @NotBlank(message = "Password is required")
    private String password;
}
