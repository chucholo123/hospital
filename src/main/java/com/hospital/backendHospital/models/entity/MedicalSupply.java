package com.hospital.backendHospital.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "medical_supplies")
public class MedicalSupply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "minimum_stock", nullable = false)
    private int minimumStock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnityEnum unity;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;
}
