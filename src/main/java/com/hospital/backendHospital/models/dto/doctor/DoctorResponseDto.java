package com.hospital.backendHospital.models.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorResponseDto {

    private String firstName;

    private String lastName;

    private String specialty;

    private boolean isActive;
}
