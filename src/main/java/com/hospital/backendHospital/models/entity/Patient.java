package com.hospital.backendHospital.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(name = "marital_status", nullable = false)
    private String maritalStatus;

    @Column(nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String allergies;

    @Column(name = "chronic_diseases", nullable = false)
    private String chronicDiseases;

    @Column(name = "current_medications", nullable = false)
    private String currentMedications;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private Double weight;

    @Column(name = "is_smoker", nullable = false)
    private Boolean isSmoker;

    @Column(name = "insurance_provider", nullable = false)
    private String insuranceProvider;

    @Column(name = "insurance_number", nullable = false)
    private String insuranceNumber;

    @Column(name = "emergency_contact", nullable = false)
    private String emergencyContact;

    @Column(name = "emergency_contact_name", nullable = false)
    private String emergencyContactName;

    @Column(name = "emergency_contact_relation", nullable = false)
    private String emergencyContactRelation;

    @Column(name = "blood_type", nullable = false)
    private String bloodType;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "patient")
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "patient")
    private List<MedicalRecord> medicalRecords = new ArrayList<>();
}

