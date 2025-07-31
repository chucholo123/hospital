package com.hospital.backendHospital.mappers;

import com.hospital.backendHospital.models.dto.supplyMovement.SupplyMovementResponseDto;
import com.hospital.backendHospital.models.entity.SupplyMovement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplyMovementMapper {

    @Mapping(target = "supplyName", source = "supply.name")
    @Mapping(target = "performedBy", source = "performedBy.firstName")
    SupplyMovementResponseDto toResponseDto(SupplyMovement supplyMovement);

    default Page<SupplyMovementResponseDto> toListDto(Page<SupplyMovement> supplyMovements){
        return supplyMovements.map(this::toResponseDto);
    }
}
