package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.doctor.CreateDoctorAndScheduleDto;
import com.hospital.backendHospital.models.dto.doctor.DoctorResponseDto;
import com.hospital.backendHospital.models.dto.doctor.UpdateDoctorAndScheduleDto;
import com.hospital.backendHospital.models.filters.DoctorFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDoctorService {

    Page<DoctorResponseDto> filterDoctors(DoctorFilterRequest request, Pageable pageable);

    DoctorResponseDto createDoctorAndSchedule(CreateDoctorAndScheduleDto createDoctorAndScheduleDto);

    DoctorResponseDto updateDoctorAndSchedule(Long id, UpdateDoctorAndScheduleDto updateDoctorDto);

    void deactivateDoctor(Long id);
}
