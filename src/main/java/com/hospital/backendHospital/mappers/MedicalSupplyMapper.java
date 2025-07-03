package com.hospital.backendHospital.mappers;

import com.hospital.backendHospital.models.dto.medicalSupply.MedicalSupplyResponseDto;
import com.hospital.backendHospital.models.entity.MedicalSupply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicalSupplyMapper {

    @Mapping(target = "category", source = "category.name")
    MedicalSupplyResponseDto toResponseDto(MedicalSupply medicalSupply);

    List<MedicalSupplyResponseDto> toListDto(List<MedicalSupply> medicalSupplies);
}
