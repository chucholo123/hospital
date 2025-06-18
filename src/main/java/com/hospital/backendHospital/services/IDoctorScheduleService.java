package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.doctorSchedule.CreateDoctorScheduleDto;
import com.hospital.backendHospital.models.dto.doctorSchedule.DoctorScheduleResponseDto;

import java.util.List;

public interface IDoctorScheduleService {

    List<DoctorScheduleResponseDto> listDoctorSchedules();

    DoctorScheduleResponseDto listScheduleByDoctorId(Long id);

    DoctorScheduleResponseDto createDoctorSchedule(CreateDoctorScheduleDto createDoctorScheduleDto);
}
