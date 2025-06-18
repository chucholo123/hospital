package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.patient.PatientResponseDto;
import com.hospital.backendHospital.models.dto.patient.PatientSummaryDto;
import com.hospital.backendHospital.models.dto.patient.UpdatePatientDto;
import com.hospital.backendHospital.models.entity.Patient;
import com.hospital.backendHospital.models.entity.User;

import java.util.List;

public interface IPatientService {

    List<PatientResponseDto> listPatients();

    List<PatientSummaryDto> listPatientsByFirstName(String firstName);

    Patient createPatient(String username, String bloodType, String emergencyContact);

    PatientResponseDto updatePatient(User user, UpdatePatientDto updatePatientDto);

    void desactivePatientById(Long id);
}
