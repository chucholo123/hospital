package com.hospital.backendHospital.mappers;

import com.hospital.backendHospital.models.dto.medicalSupply.CreateMedicalSupplyDto;
import com.hospital.backendHospital.models.dto.medicalSupply.MedicalSupplyResponseDto;
import com.hospital.backendHospital.models.entity.MedicalSupply;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicalSupplyMapper {

    MedicalSupply toEntity(CreateMedicalSupplyDto createMedicalSupplyDto);

    MedicalSupplyResponseDto toResponseDto(MedicalSupply medicalSupply);
}
