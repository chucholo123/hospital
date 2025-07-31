package com.hospital.backendHospital.models.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalizationFilterRequest {

    private LocalDate date;

    private Long patientId;

    private Long doctorId;

    private Long roomId;

    private String status;
}
