package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.medicalRecord.CreateMedicalRecordDto;
import com.hospital.backendHospital.models.dto.medicalRecord.MedicalRecordResponseDto;

import java.util.List;

public interface IMedicalRecordService {
    MedicalRecordResponseDto listMedicalRecordByPatientId(Long id);

    MedicalRecordResponseDto createMedicalRecord(CreateMedicalRecordDto createMedicalRecordDto);
}
