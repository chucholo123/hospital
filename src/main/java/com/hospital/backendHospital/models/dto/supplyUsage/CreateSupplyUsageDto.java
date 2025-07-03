package com.hospital.backendHospital.models.dto.supplyUsage;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSupplyUsageDto {

    @NotNull(message = "Hospitalization is required")
    private Long hospitalizationId;

    @NotNull(message = "Supply is required")
    private Long supplyId;

    @NotNull(message = "Quantity used is required")
    private int quantityUsed;
}
