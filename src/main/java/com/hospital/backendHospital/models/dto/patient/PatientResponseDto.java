package com.hospital.backendHospital.models.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private LocalDate dateOfBirth;

    private String gender;

    private String allergies;

    private String bloodType;

    private String chronicDiseases;

    private String currentMedications;

    private String emergencyContact;

    private String emergencyContactName;

    private String emergencyContactRelation;

    private Double height;

    private Double weight;

    private LocalDate registerDate;

    private boolean smoker;

    private boolean active;
}
