package com.hospital.backendHospital.models.dto.patient;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePatientDto {

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be valid")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Allergies is required")
    private String allergies;

    @NotBlank(message = "Blood type is required")
    private String bloodType;

    @NotBlank(message = "Chronic diseases is required")
    private String chronicDiseases;

    @NotBlank(message = "Current medications is required")
    private String currentMedications;

    @NotBlank(message = "Emergency contact is required")
    @Pattern(regexp = "^\\d{10}$", message = "Emergency number must be valid")
    private String emergencyContact;

    @NotBlank(message = "Emergency contact name is required")
    private String emergencyContactName;

    @NotBlank(message = "Emergency contact relations is required")
    private String emergencyContactRelation;

    @NotNull(message = "Height is required")
    private Double height;

    @NotNull(message = "Weight is required")
    private Double weight;

    @NotNull(message = "This camp is required")
    private boolean smoker;
}
