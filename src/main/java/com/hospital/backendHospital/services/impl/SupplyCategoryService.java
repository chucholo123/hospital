package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.mappers.SupplyCategoryMapper;
import com.hospital.backendHospital.models.dto.supplyCategory.CreateSupplyCategoryDto;
import com.hospital.backendHospital.models.dto.supplyCategory.SupplyCategoryResponseDto;
import com.hospital.backendHospital.models.entity.SupplyCategory;
import com.hospital.backendHospital.repositories.SupplyCategoryRepository;
import com.hospital.backendHospital.services.ISupplyCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyCategoryService implements ISupplyCategoryService {

    private final SupplyCategoryRepository supplyCategoryRepository;
    private final SupplyCategoryMapper supplyCategoryMapper;

    @Override
    public List<SupplyCategoryResponseDto> getAllCategories() {
        List<SupplyCategory> supplyCategories = supplyCategoryRepository.findAll();

        return supplyCategoryMapper.toListoDto(supplyCategories);
    }

    @Override
    public SupplyCategoryResponseDto getCategoryById(Long id) {
        SupplyCategory supplyCategory = supplyCategoryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Supply category not found"));

        return supplyCategoryMapper.toResponseDto(supplyCategory);
    }

    @Override
    public SupplyCategoryResponseDto createCategory(CreateSupplyCategoryDto createSupplyCategoryDto) {
        SupplyCategory supplyCategory = supplyCategoryMapper.toEntity(createSupplyCategoryDto);

        return supplyCategoryMapper.toResponseDto(supplyCategory);

    }
}
