package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.supplyUsage.CreateSupplyUsageDto;
import com.hospital.backendHospital.models.dto.supplyUsage.SupplyUsageResponseDto;
import com.hospital.backendHospital.models.entity.User;

import java.util.List;

public interface ISupplyUsageService {

    List<SupplyUsageResponseDto> getUsageByHospitalization(Long hospitalizationId);

    SupplyUsageResponseDto registerUsage(User user, CreateSupplyUsageDto createSupplyUsageDto);
}
