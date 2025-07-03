package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.models.dto.supplyCategory.CreateSupplyCategoryDto;
import com.hospital.backendHospital.models.dto.supplyCategory.SupplyCategoryResponseDto;
import com.hospital.backendHospital.services.ISupplyCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supply-categories")
@RequiredArgsConstructor
public class SupplyCategoryController {

    private final ISupplyCategoryService supplyCategoryService;

    @GetMapping
    public ResponseEntity<List<SupplyCategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok(supplyCategoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplyCategoryResponseDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(supplyCategoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<SupplyCategoryResponseDto> createCategory(
            @Valid @RequestBody CreateSupplyCategoryDto dto) {
        return ResponseEntity.ok(supplyCategoryService.createCategory(dto));
    }
}
