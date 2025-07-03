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
    private String description;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "minimum_stock", nullable = false)
    private int minimumStock;

    @Column(name = "unit_cost", nullable = false)
    private BigDecimal unitCost;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private SupplyCategory category;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}
