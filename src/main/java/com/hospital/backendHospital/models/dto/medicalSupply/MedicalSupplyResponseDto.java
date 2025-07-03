package com.hospital.backendHospital.models.dto.medicalSupply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalSupplyResponseDto {

    private String name;

    private String description;

    private int quantity;

    private int minimumStock;

    private BigDecimal unitCost;

    private String category;

    private boolean isActive;
}
