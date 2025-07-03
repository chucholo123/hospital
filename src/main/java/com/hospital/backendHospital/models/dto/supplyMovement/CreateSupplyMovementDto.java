package com.hospital.backendHospital.models.dto.supplyMovement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSupplyMovementDto {

    @NotNull(message = "Supply is required")
    private Long supplyId;

    @NotNull(message = "Quantity changed is required")
    @Positive(message = "Quantity changed must be valid")
    private int quantityChanged;

    @NotBlank(message = "Reason is required")
    private String reason;
}
