package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.models.dto.medicalSupply.CreateMedicalSupplyDto;
import com.hospital.backendHospital.models.dto.medicalSupply.MedicalSupplyResponseDto;
import com.hospital.backendHospital.models.filters.MedicalSupplyFilterRequest;
import com.hospital.backendHospital.services.IMedicalSupplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/medicalSupplies")
public class MedicalSupplyController {

    private final IMedicalSupplyService medicalSupplyService;

    @GetMapping
    public ResponseEntity<Page<MedicalSupplyResponseDto>> filterMedicalSupply(MedicalSupplyFilterRequest filter, Pageable pageable){
        Page<MedicalSupplyResponseDto> medicalSupplies = medicalSupplyService.filterMedicalSupply(filter, pageable);

        return ResponseEntity.ok(medicalSupplies);
    }

    @PostMapping
    public ResponseEntity<MedicalSupplyResponseDto> createSupply(@Valid @RequestBody CreateMedicalSupplyDto createMedicalSupplyDto){
        log.info(String.valueOf(createMedicalSupplyDto));
        MedicalSupplyResponseDto medicalSupply = medicalSupplyService.createSupply(createMedicalSupplyDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(medicalSupply);
    }
}
