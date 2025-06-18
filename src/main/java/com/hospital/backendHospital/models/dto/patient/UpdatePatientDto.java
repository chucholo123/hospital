package com.hospital.backendHospital.models.dto.patient;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePatientDto {

    @Size(min = 10, max = 30, message = "First name must be between 10 and 30 characters")
    private String firstName;

    @Size(min = 10, max = 30, message = "Last name must be between 10 and 30 characters")
    private String lastName;

    @Size(min = 8, message = "The password must be at least 8 characters")
    private String newPassword;

    @Size(min = 10, max = 10, message = "The emergency contact must be valid")
    private String newEmergencyContact;
}
