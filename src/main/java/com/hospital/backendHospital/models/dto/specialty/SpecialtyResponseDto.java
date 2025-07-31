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

    private Integer id;

    private String name;

    private String description;

    private BigDecimal cost;

    private int doctorCount;

    private boolean active;
}
