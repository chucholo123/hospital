package com.hospital.backendHospital.models.dto.appointment;

import com.hospital.backendHospital.models.entity.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponseDto {

    private Long id;

    private String patientName;

    private String doctorName;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private AppointmentStatus status;

    private BigDecimal total;
}
