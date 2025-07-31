package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.supplyMovement.CreateSupplyMovementDto;
import com.hospital.backendHospital.models.dto.supplyMovement.SupplyMovementResponseDto;
import com.hospital.backendHospital.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISupplyMovementService {

    Page<SupplyMovementResponseDto> getMovements(Pageable pageable);

    SupplyMovementResponseDto registerEntry(User user, CreateSupplyMovementDto CreateSupplyMovementDto);

    SupplyMovementResponseDto registerExit(User user, CreateSupplyMovementDto CreateSupplyMovementDto);
}
