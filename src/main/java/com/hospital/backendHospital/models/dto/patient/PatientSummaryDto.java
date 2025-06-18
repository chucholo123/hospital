package com.hospital.backendHospital.models.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientSummaryDto {

    private String firstName;

    private String lastName;
}
