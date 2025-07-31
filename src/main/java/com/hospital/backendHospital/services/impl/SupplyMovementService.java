package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.exceptions.InvalidDataException;
import com.hospital.backendHospital.mappers.SupplyMovementMapper;
import com.hospital.backendHospital.models.dto.supplyMovement.CreateSupplyMovementDto;
import com.hospital.backendHospital.models.dto.supplyMovement.SupplyMovementResponseDto;
import com.hospital.backendHospital.models.entity.MedicalSupply;
import com.hospital.backendHospital.models.entity.MovementType;
import com.hospital.backendHospital.models.entity.SupplyMovement;
import com.hospital.backendHospital.models.entity.User;
import com.hospital.backendHospital.repositories.MedicalSupplyRepository;
import com.hospital.backendHospital.repositories.SupplyMovementRepository;
import com.hospital.backendHospital.services.ISupplyMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SupplyMovementService implements ISupplyMovementService {

    private final SupplyMovementRepository supplyMovementRepository;
    private final MedicalSupplyRepository medicalSupplyRepository;
    private final SupplyMovementMapper supplyMovementMapper;


    @Override
    @Transactional(readOnly = true)
    public Page<SupplyMovementResponseDto> getMovements(Pageable pageable) {
        Page<SupplyMovement> supplyMovements = supplyMovementRepository.findAll(pageable);

        return supplyMovementMapper.toListDto(supplyMovements);
    }

    @Override
    @Transactional
    public SupplyMovementResponseDto registerEntry(User user, CreateSupplyMovementDto createSupplyMovementDto) {
        MedicalSupply medicalSupply = medicalSupplyRepository.findById(createSupplyMovementDto.getSupplyId()).orElseThrow(()->
                new EntityNotFoundException("Medical supply not found"));

        SupplyMovement supplyMovement = SupplyMovement.builder()
                .supply(medicalSupply)
                .type(MovementType.IN)
                .quantityChanged(createSupplyMovementDto.getQuantityChanged())
                .timestamp(LocalDateTime.now())
                .performedBy(user)
                .build();

        supplyMovementRepository.save(supplyMovement);

        medicalSupply.setQuantity(medicalSupply.getQuantity() + createSupplyMovementDto.getQuantityChanged());
        medicalSupplyRepository.save(medicalSupply);

        return supplyMovementMapper.toResponseDto(supplyMovement);
    }

    @Override
    @Transactional
    public SupplyMovementResponseDto registerExit(User user, CreateSupplyMovementDto createSupplyMovementDto) {
        MedicalSupply medicalSupply = medicalSupplyRepository.findById(createSupplyMovementDto.getSupplyId()).orElseThrow(()->
                new EntityNotFoundException("Medical supply not found"));

        if (createSupplyMovementDto.getQuantityChanged() > medicalSupply.getQuantity()){
            throw new InvalidDataException("This quantity exceeds the stock");
        }

        if (medicalSupply.getQuantity() - createSupplyMovementDto.getQuantityChanged() < medicalSupply.getMinimumStock()) {
            throw new InvalidDataException("Amount exceeds the minimum stock");
        }

        SupplyMovement supplyMovement = SupplyMovement.builder()
                .supply(medicalSupply)
                .type(MovementType.OUT)
                .quantityChanged(createSupplyMovementDto.getQuantityChanged())
                .timestamp(LocalDateTime.now())
                .performedBy(user)
                .build();

        supplyMovementRepository.save(supplyMovement);

        medicalSupply.setQuantity(medicalSupply.getQuantity() - createSupplyMovementDto.getQuantityChanged());
        medicalSupplyRepository.save(medicalSupply);

        return supplyMovementMapper.toResponseDto(supplyMovement);
    }
}
