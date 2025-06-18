package com.hospital.backendHospital.models.dto.hospitalization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalizationResponseDto {

    private String patientName;

    private String roomName;

    private LocalDate admissionDate;

    private LocalDate dischargeDate;

    private String reason;

    private BigDecimal costPerDay;

    private boolean isActive;
}
