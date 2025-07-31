package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.patient.CreatePatientDto;
import com.hospital.backendHospital.models.dto.patient.PatientResponseDto;
import com.hospital.backendHospital.models.dto.patient.PatientSummaryDto;
import com.hospital.backendHospital.models.dto.patient.UpdatePatientDto;
import com.hospital.backendHospital.models.entity.Patient;
import com.hospital.backendHospital.models.entity.User;
import com.hospital.backendHospital.models.filters.PatientFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPatientService {

    Page<PatientResponseDto> filterPatients(PatientFilterRequest filter, Pageable pageable);

    PatientResponseDto createPatient(User user, CreatePatientDto createPatientDto);

    PatientResponseDto updatePatient(User user, UpdatePatientDto updatePatientDto);

    void deactivatePatient(Long id);
}
