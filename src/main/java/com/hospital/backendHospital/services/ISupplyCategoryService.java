package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.supplyCategory.CreateSupplyCategoryDto;
import com.hospital.backendHospital.models.dto.supplyCategory.SupplyCategoryResponseDto;

import java.util.List;

public interface ISupplyCategoryService {

    List<SupplyCategoryResponseDto> getAllCategories();

    SupplyCategoryResponseDto getCategoryById(Long id);

    SupplyCategoryResponseDto createCategory(CreateSupplyCategoryDto createSupplyCategoryDto);
}
