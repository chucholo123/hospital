package com.hospital.backendHospital.models.dto.medicalSupply;

import com.hospital.backendHospital.models.entity.UnityEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMedicalSupplyDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be valid")
    private int quantity;

    @NotNull(message = "Minimum stock is required")
    @Positive(message = "Minimum stock must be valid")
    private int minimumStock;

    @NotNull(message = "Unity is required")
    private UnityEnum unity;
}
