package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.models.dto.specialty.CreateSpecialtyDto;
import com.hospital.backendHospital.models.dto.specialty.SpecialtyResponseDto;
import com.hospital.backendHospital.services.ISpecialtyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/specialties")
public class SpecialtyController {

    private final ISpecialtyService specialtyService;

    @GetMapping
    public ResponseEntity<List<SpecialtyResponseDto>> getAllSpecialties(){
        return ResponseEntity.ok(specialtyService.listSpecialties());
    }

    @GetMapping("/search")
    public ResponseEntity<SpecialtyResponseDto> getSpecialtyByName(@RequestParam String name){
        return ResponseEntity.ok(specialtyService.listSpecialtyByName(name));
    }

    @PostMapping
    public ResponseEntity<SpecialtyResponseDto> createSpecialty(@Valid @RequestBody CreateSpecialtyDto createSpecialtyDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(specialtyService.createSpecialty(createSpecialtyDto));
    }
}
