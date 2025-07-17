package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.doctorSchedule.CreateDoctorScheduleDto;
import com.hospital.backendHospital.models.dto.doctorSchedule.DoctorScheduleResponseDto;
import com.hospital.backendHospital.models.filters.DoctorScheduleFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDoctorScheduleService {

    Page<DoctorScheduleResponseDto> filterDoctorSchedules(DoctorScheduleFilterRequest filter, Pageable pageable);

    DoctorScheduleResponseDto createDoctorSchedule(CreateDoctorScheduleDto createDoctorScheduleDto);
}
