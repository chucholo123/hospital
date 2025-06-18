package com.hospital.backendHospital.models.dto.specialty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialtyResponseDto {

    private String name;

    private String description;

    private BigDecimal cost;
}
