package com.hospital.backendHospital.models.dto.supplyUsage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplyUsageResponseDto {

    private Long id;

    private Long hospitalizaciontId;

    private String supply;

    private int quantityUsed;

    private LocalDateTime usedAt;
}
