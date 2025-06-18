package com.hospital.backendHospital.models.dto.doctor;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDoctorDto {

    @Size(min = 10, max = 30, message = "First name must be between 10 and 30 characters")
    private String firstName;

    @Size(min = 10, max = 30, message = "Last name must be between 10 and 30 characters")
    private String lastName;

    @Size(min = 8, message = "The password must be at least 8 characters")
    private String newPassword;
}
