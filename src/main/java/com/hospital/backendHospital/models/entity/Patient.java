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

    @OneToOne(optional = false, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String allergies;

    @Column(name = "blood_type", nullable = false)
    private String bloodType;

    @Column(name = "chronic_diseases", nullable = false)
    private String chronicDiseases;

    @Column(name = "current_medications", nullable = false)
    private String currentMedications;

    @Column(name = "emergency_contact", nullable = false)
    private String emergencyContact;

    @Column(name = "emergency_contact_name", nullable = false)
    private String emergencyContactName;

    @Column(name = "emergency_contact_relation", nullable = false)
    private String emergencyContactRelation;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private Double weight;

    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;

    @Column(nullable = false)
    private boolean smoker;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "patient")
    private List<Appointment> appointments = new ArrayList<>();
}