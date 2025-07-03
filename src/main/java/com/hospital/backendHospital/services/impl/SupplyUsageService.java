package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.exceptions.InvalidDataException;
import com.hospital.backendHospital.mappers.SupplyUsageMapper;
import com.hospital.backendHospital.models.dto.supplyUsage.CreateSupplyUsageDto;
import com.hospital.backendHospital.models.dto.supplyUsage.SupplyUsageResponseDto;
import com.hospital.backendHospital.models.entity.*;
import com.hospital.backendHospital.repositories.HospitalizationRepository;
import com.hospital.backendHospital.repositories.MedicalSupplyRepository;
import com.hospital.backendHospital.repositories.SupplyMovementRepository;
import com.hospital.backendHospital.repositories.SupplyUsageRepository;
import com.hospital.backendHospital.services.ISupplyUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyUsageService implements ISupplyUsageService {

    private final SupplyUsageRepository supplyUsageRepository;
    private final SupplyUsageMapper supplyUsageMapper;
    private final HospitalizationRepository hospitalizationRepository;
    private final MedicalSupplyRepository medicalSupplyRepository;
    private final SupplyMovementRepository supplyMovementRepository;

    @Override
    public List<SupplyUsageResponseDto> getUsageByHospitalization(Long hospitalizationId) {
        List<SupplyUsage> supplyUsages = supplyUsageRepository.findAll();

        return supplyUsageMapper.toListoDto(supplyUsages);
    }

    @Override
    public SupplyUsageResponseDto registerUsage(User user, CreateSupplyUsageDto createSupplyUsageDto) {
        Hospitalization hospitalization = hospitalizationRepository.findById(createSupplyUsageDto.getHospitalizationId()).orElseThrow(
                ()-> new EntityNotFoundException("Hospitalization not found"));

        MedicalSupply medicalSupply = medicalSupplyRepository.findById(createSupplyUsageDto.getSupplyId()).orElseThrow(
                ()-> new EntityNotFoundException("Medical supply not found"));

        if (!hospitalization.isActive()){
            throw new InvalidDataException("Hospitalization has ended");
        }

        if (createSupplyUsageDto.getQuantityUsed() > medicalSupply.getQuantity()) {
            throw new InvalidDataException("Not enough supply in stock");
        }

        SupplyUsage supplyUsage = SupplyUsage.builder()
                .hospitalization(hospitalization)
                .supply(medicalSupply)
                .quantityUsed(createSupplyUsageDto.getQuantityUsed())
                .usedAt(LocalDateTime.now())
                .build();

        supplyUsageRepository.save(supplyUsage);

        medicalSupply.setQuantity(medicalSupply.getQuantity() - createSupplyUsageDto.getQuantityUsed());
        medicalSupplyRepository.save(medicalSupply);

        SupplyMovement supplyMovement = SupplyMovement.builder()
                .supply(medicalSupply)
                .type(MovementType.OUT)
                .quantityChanged(createSupplyUsageDto.getQuantityUsed())
                .reason("Used in hospitalization ID " + hospitalization.getId())
                .timestamp(LocalDateTime.now())
                .performedBy(user)
                .build();

        supplyMovementRepository.save(supplyMovement);

        return supplyUsageMapper.toResponseDto(supplyUsage);
    }
}
