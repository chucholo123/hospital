package com.hospital.backendHospital.models.dto.medicalRecord;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  CreateMedicalRecordDto {

    @NotNull(message = "Patient is required")
    private Long patientId;

    @NotBlank(message = "Diagnostic is required")
    private String diagnostics;

    @NotBlank(message = "Treatment is required")
    private String treatment;
}
