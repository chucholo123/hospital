package com.hospital.backendHospital.models.dto.medicalSupply;

import com.hospital.backendHospital.models.entity.UnityEnum;
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

    private int quantity;

    private int minimumStock;

    private UnityEnum unity;

    private boolean active;
}
