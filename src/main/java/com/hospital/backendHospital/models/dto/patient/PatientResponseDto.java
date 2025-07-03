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

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    private String maritalStatus;

    private String address;

    private String phoneNumber;

    private String allergies;

    private String chronicDiseases;

    private String currentMedications;

    private Double height;

    private Double weight;

    private Boolean isSmoker;

    private String insuranceProvider;

    private String insuranceNumber;

    private String emergencyContact;

    private String emergencyContactName;

    private String emergencyContactRelation;

    private String bloodType;

    private boolean isActive;
}
