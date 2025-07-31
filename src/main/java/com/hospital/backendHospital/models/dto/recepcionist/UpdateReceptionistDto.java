package com.hospital.backendHospital.models.dto.recepcionist;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateReceptionistDto {

    @Email(message = "Email must be valid")
    private String email;

    @Size(min = 10, max = 30, message = "First name must be between 5 and 20 characters")
    private String firstName;

    @Size(min = 10, max = 30, message = "Last name must be between 5 and 20 characters")
    private String lastName;

    @Size(min = 8, message = "The password must be at least 8 characters")
    private String password;
}
