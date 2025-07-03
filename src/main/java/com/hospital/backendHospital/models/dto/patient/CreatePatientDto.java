package com.hospital.backendHospital.models.dto.patient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Marital status is required")
    private String maritalStatus;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\d{10}$",
            message = "Phone number must be valid"
    )
    private String phoneNumber;

    @NotBlank(message = "Allergies is required")
    private String allergies;

    @NotBlank(message = "Chronic diseases is required")
    private String chronicDiseases;

    @NotBlank(message = "Current medications is required")
    private String currentMedications;

    @NotBlank(message = "Height is required")
    private Double height;

    @NotBlank(message = "Weight is required")
    private Double weight;

    @NotBlank(message = "This camp is required")
    private Boolean isSmoker;

    @NotBlank(message = "Insurance provider is required")
    private String insuranceProvider;

    @NotBlank(message = "Insurance number is required")
    private String insuranceNumber;

    @NotBlank(message = "Energency contact is required")
    @Pattern(
            regexp = "^\\d{10}$",
            message = "Phone number must be valid"
    )
    private String emergencyContact;

    @NotBlank(message = "Emergency contact name is required")
    private String emergencyContactName;

    @NotBlank(message = "Emergency contact relations is required")
    private String emergencyContactRelation;

    @NotBlank(message = "Blood type is required")
    private String bloodType;
}
