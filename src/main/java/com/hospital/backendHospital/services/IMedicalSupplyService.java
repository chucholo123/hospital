package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.medicalSupply.CreateMedicalSupplyDto;
import com.hospital.backendHospital.models.dto.medicalSupply.MedicalSupplyResponseDto;
import com.hospital.backendHospital.models.filters.MedicalSupplyFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMedicalSupplyService {

    Page<MedicalSupplyResponseDto> filterMedicalSupply(MedicalSupplyFilterRequest filter, Pageable pageable);

    MedicalSupplyResponseDto createSupply(CreateMedicalSupplyDto createMedicalSupplyDto);
}
