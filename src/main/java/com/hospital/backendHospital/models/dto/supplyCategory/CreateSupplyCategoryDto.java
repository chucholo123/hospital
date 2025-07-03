package com.hospital.backendHospital.models.dto.supplyCategory;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSupplyCategoryDto {

    @NotNull(message = "Name is required")
    private String name;
}
