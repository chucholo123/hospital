package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.doctor.CreateDoctorDto;
import com.hospital.backendHospital.models.dto.doctor.DoctorResponseDto;
import com.hospital.backendHospital.models.dto.doctor.UpdateDoctorDto;

import java.util.List;

public interface IDoctorService {

    List<DoctorResponseDto> listDoctors();

    DoctorResponseDto createDoctor(CreateDoctorDto createDoctorDto);

    DoctorResponseDto updateDoctor(Long id, UpdateDoctorDto updateDoctorDto);

    void desactiveDoctorById(Long id);
}
