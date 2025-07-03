package com.hospital.backendHospital.mappers;

import com.hospital.backendHospital.models.dto.supplyUsage.SupplyUsageResponseDto;
import com.hospital.backendHospital.models.entity.SupplyUsage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplyUsageMapper {

    @Mapping(target = "hospitalizaciontId", source = "hospitalization.id")
    @Mapping(target = "supply", source = "supply.name")
    SupplyUsageResponseDto toResponseDto(SupplyUsage supplyUsage);

    List<SupplyUsageResponseDto> toListoDto(List<SupplyUsage> supplyUsages);
}
