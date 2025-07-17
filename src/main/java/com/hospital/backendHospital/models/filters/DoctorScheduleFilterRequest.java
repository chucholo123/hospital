package com.hospital.backendHospital.models.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorScheduleFilterRequest {

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private String firstName;

    private String lastName;

    private Integer doctorId;
}
