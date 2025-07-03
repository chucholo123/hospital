package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.supplyMovement.CreateSupplyMovementDto;
import com.hospital.backendHospital.models.dto.supplyMovement.SupplyMovementResponseDto;
import com.hospital.backendHospital.models.entity.User;

import java.util.List;

public interface ISupplyMovementService {

    List<SupplyMovementResponseDto> getAllMovements();

    SupplyMovementResponseDto getMovementById(Long id);

    SupplyMovementResponseDto registerEntry(User user, CreateSupplyMovementDto CreateSupplyMovementDto);

    SupplyMovementResponseDto registerExit(User user, CreateSupplyMovementDto CreateSupplyMovementDto);

    List<SupplyMovementResponseDto> getMovementsBySupply(Long supplyId);
}
