package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.doctor.CreateDoctorAndScheduleDto;
import com.hospital.backendHospital.models.dto.doctor.DoctorResponseDto;
import com.hospital.backendHospital.models.dto.doctor.UpdateDoctorDto;
import com.hospital.backendHospital.models.filters.DoctorFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDoctorService {

    Page<DoctorResponseDto> filterDoctors(DoctorFilterRequest request, Pageable pageable);

    DoctorResponseDto createDoctor(CreateDoctorAndScheduleDto createDoctorAndScheduleDto);

    DoctorResponseDto updateDoctor(Long id, UpdateDoctorDto updateDoctorDto);

    void desactiveDoctorById(Long id);
}
