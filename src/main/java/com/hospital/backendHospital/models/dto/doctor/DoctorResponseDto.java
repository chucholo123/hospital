package com.hospital.backendHospital.models.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorResponseDto {

    private Long doctorId;

    private String firstName;

    private String lastName;

    private String email;

    private String specialty;

    private String phoneNumber;

    private LocalDate registerDate;

    private boolean active;
}
