package com.hospital.backendHospital.mappers;

import com.hospital.backendHospital.models.dto.supplyCategory.CreateSupplyCategoryDto;
import com.hospital.backendHospital.models.dto.supplyCategory.SupplyCategoryResponseDto;
import com.hospital.backendHospital.models.entity.SupplyCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplyCategoryMapper {

    SupplyCategory toEntity(CreateSupplyCategoryDto createSupplyCategoryDto);

    SupplyCategoryResponseDto toResponseDto(SupplyCategory supplyCategory);

    List<SupplyCategoryResponseDto> toListoDto(List<SupplyCategory> supplyCategories);
}
