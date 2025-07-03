package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.medicalSupply.CreateMedicalSupplyDto;
import com.hospital.backendHospital.models.dto.medicalSupply.MedicalSupplyResponseDto;

import java.util.List;

public interface IMedicalSupplyService {

    List<MedicalSupplyResponseDto> getAllSupplies();

    List<MedicalSupplyResponseDto> getSuppliesByCategory(Long categoryId);

    MedicalSupplyResponseDto getSupplyById(Long id);

    MedicalSupplyResponseDto createSupply(CreateMedicalSupplyDto createMedicalSupplyDto);

    //MedicalSupplyResponseDto updateSupply(Long id, UpdateMedicalSupplyDto dto)

    void deleteSupply(Long id);
}
