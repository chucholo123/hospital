package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.hospitalization.CreateHospitalizationDto;
import com.hospital.backendHospital.models.dto.hospitalization.HospitalizationResponseDto;

public interface IHospitalzationService {

    HospitalizationResponseDto listHospitalizationByPatientId(Long id);

    HospitalizationResponseDto createHospitalization(CreateHospitalizationDto createHospitalizationDto);

    void setDischargeDateById(Long id);
}
