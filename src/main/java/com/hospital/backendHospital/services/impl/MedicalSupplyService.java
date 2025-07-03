package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.exceptions.InvalidDataException;
import com.hospital.backendHospital.mappers.MedicalSupplyMapper;
import com.hospital.backendHospital.models.dto.medicalSupply.CreateMedicalSupplyDto;
import com.hospital.backendHospital.models.dto.medicalSupply.MedicalSupplyResponseDto;
import com.hospital.backendHospital.models.entity.MedicalSupply;
import com.hospital.backendHospital.models.entity.SupplyCategory;
import com.hospital.backendHospital.repositories.MedicalSupplyRepository;
import com.hospital.backendHospital.repositories.SupplyCategoryRepository;
import com.hospital.backendHospital.services.IMedicalSupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalSupplyService implements IMedicalSupplyService {

    private final MedicalSupplyRepository medicalSupplyRepository;
    private final SupplyCategoryRepository supplyCategoryRepository;
    private final MedicalSupplyMapper medicalSupplyMapper;

    @Override
    public List<MedicalSupplyResponseDto> getAllSupplies() {
        List<MedicalSupply> medicalSupplies = medicalSupplyRepository.findAll();

        return medicalSupplyMapper.toListDto(medicalSupplies);
    }

    @Override
    public List<MedicalSupplyResponseDto> getSuppliesByCategory(Long categoryId) {
        List<MedicalSupply> medicalSupplies = medicalSupplyRepository.findAllByCategoryId(categoryId);

        return medicalSupplyMapper.toListDto(medicalSupplies);
    }

    @Override
    public MedicalSupplyResponseDto getSupplyById(Long id) {
        MedicalSupply medicalSupply = medicalSupplyRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Medical supply not found"));

        return medicalSupplyMapper.toResponseDto(medicalSupply);
    }

    @Override
    public MedicalSupplyResponseDto createSupply(CreateMedicalSupplyDto createMedicalSupplyDto) {
        SupplyCategory supplyCategory = supplyCategoryRepository.findById(createMedicalSupplyDto.getCategoryId()).orElseThrow(()->
                new EntityNotFoundException("Supply category not found"));

        if (createMedicalSupplyDto.getQuantity() < createMedicalSupplyDto.getMinimumStock()){
            throw new InvalidDataException("Minimum stock cannot exceed available quantity");
        }

        MedicalSupply medicalSupply = MedicalSupply.builder()
                .name(createMedicalSupplyDto.getName())
                .description(createMedicalSupplyDto.getDescription())
                .quantity(createMedicalSupplyDto.getQuantity())
                .minimumStock(createMedicalSupplyDto.getMinimumStock())
                .unitCost(createMedicalSupplyDto.getUnitCost())
                .category(supplyCategory)
                .isActive(true)
                .build();

        medicalSupplyRepository.save(medicalSupply);

        return medicalSupplyMapper.toResponseDto(medicalSupply);
    }

    @Override
    public void deleteSupply(Long id) {
        if (!medicalSupplyRepository.existsById(id)){
            throw new EntityNotFoundException("Medical supply not found");
        }

        medicalSupplyRepository.deleteById(id);
    }
}
