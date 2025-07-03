package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.models.dto.medicalSupply.CreateMedicalSupplyDto;
import com.hospital.backendHospital.models.dto.medicalSupply.MedicalSupplyResponseDto;
import com.hospital.backendHospital.services.IMedicalSupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/medicalSupplies")
public class MedicalSupplyController {

    private final IMedicalSupplyService medicalSupplyService;

    @GetMapping("/getAll")
    public ResponseEntity<List<MedicalSupplyResponseDto>> getAllMedicalSupplies(){
        List<MedicalSupplyResponseDto> medicalSupplies = medicalSupplyService.getAllSupplies();

        return ResponseEntity.ok(medicalSupplies);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<MedicalSupplyResponseDto>> getSuppliesByCategory(@PathVariable Long categoryId){
        List<MedicalSupplyResponseDto> medicalSupplies = medicalSupplyService.getSuppliesByCategory(categoryId);

        return ResponseEntity.ok(medicalSupplies);
    }

    @GetMapping("/{supplyId}")
    public ResponseEntity<MedicalSupplyResponseDto> getSupplyById(@PathVariable Long supplyId){
        MedicalSupplyResponseDto medicalSupply = medicalSupplyService.getSupplyById(supplyId);

        return ResponseEntity.ok(medicalSupply);
    }

    @PostMapping
    public ResponseEntity<MedicalSupplyResponseDto> createSupply(@RequestBody CreateMedicalSupplyDto createMedicalSupplyDto){
        MedicalSupplyResponseDto medicalSupply = medicalSupplyService.createSupply(createMedicalSupplyDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(medicalSupply);
    }
}
