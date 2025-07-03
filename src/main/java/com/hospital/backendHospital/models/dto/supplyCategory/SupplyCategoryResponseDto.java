package com.hospital.backendHospital.models.dto.supplyCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplyCategoryResponseDto {

    private Long id;

    private String name;
}
