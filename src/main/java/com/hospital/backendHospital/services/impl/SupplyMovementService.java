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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyMovementService implements ISupplyMovementService {

    private final SupplyMovementRepository supplyMovementRepository;
    private final MedicalSupplyRepository medicalSupplyRepository;
    private final SupplyMovementMapper supplyMovementMapper;

    @Override
    public List<SupplyMovementResponseDto> getAllMovements() {
        List<SupplyMovement> supplyMovements = supplyMovementRepository.findAll();

        return supplyMovementMapper.toListoDto(supplyMovements);
    }

    @Override
    public SupplyMovementResponseDto getMovementById(Long id) {
        SupplyMovement supplyMovement = supplyMovementRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Supply movement not found"));

        return supplyMovementMapper.toResponseDto(supplyMovement);
    }

    @Override
    public SupplyMovementResponseDto registerEntry(User user, CreateSupplyMovementDto createSupplyMovementDto) {
        MedicalSupply medicalSupply = medicalSupplyRepository.findById(createSupplyMovementDto.getSupplyId()).orElseThrow(()->
                new EntityNotFoundException("Medical supply not found"));

        if (createSupplyMovementDto.getQuantityChanged() <= 0) {
            throw new InvalidDataException("Quantity must be positive");
        }

        SupplyMovement supplyMovement = SupplyMovement.builder()
                .supply(medicalSupply)
                .type(MovementType.IN)
                .quantityChanged(createSupplyMovementDto.getQuantityChanged())
                .reason(createSupplyMovementDto.getReason())
                .timestamp(LocalDateTime.now())
                .performedBy(user)
                .build();

        supplyMovementRepository.save(supplyMovement);

        medicalSupply.setQuantity(medicalSupply.getQuantity() + createSupplyMovementDto.getQuantityChanged());
        medicalSupplyRepository.save(medicalSupply);

        return supplyMovementMapper.toResponseDto(supplyMovement);
    }

    @Override
    public SupplyMovementResponseDto registerExit(User user, CreateSupplyMovementDto createSupplyMovementDto) {
        MedicalSupply medicalSupply = medicalSupplyRepository.findById(createSupplyMovementDto.getSupplyId()).orElseThrow(()->
                new EntityNotFoundException("Medical supply not found"));

        if (createSupplyMovementDto.getQuantityChanged() <= 0) {
            throw new InvalidDataException("Quantity must be positive");
        }

        if (createSupplyMovementDto.getQuantityChanged() > medicalSupply.getQuantity()){
            throw new InvalidDataException("This quantity exceeds the stock");
        }

        SupplyMovement supplyMovement = SupplyMovement.builder()
                .supply(medicalSupply)
                .type(MovementType.OUT)
                .quantityChanged(createSupplyMovementDto.getQuantityChanged())
                .reason(createSupplyMovementDto.getReason())
                .timestamp(LocalDateTime.now())
                .performedBy(user)
                .build();

        supplyMovementRepository.save(supplyMovement);

        medicalSupply.setQuantity(medicalSupply.getQuantity() - createSupplyMovementDto.getQuantityChanged());
        medicalSupplyRepository.save(medicalSupply);

        return supplyMovementMapper.toResponseDto(supplyMovement);
    }

    @Override
    public List<SupplyMovementResponseDto> getMovementsBySupply(Long supplyId) {
        List<SupplyMovement> supplyMovements = supplyMovementRepository.findAllBySupplyId(supplyId);

        return supplyMovementMapper.toListoDto(supplyMovements);
    }
}
