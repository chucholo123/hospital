package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.models.dto.hospitalization.CreateHospitalizationDto;
import com.hospital.backendHospital.models.dto.hospitalization.HospitalizationResponseDto;
import com.hospital.backendHospital.services.IHospitalzationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hospitalizations")
public class HospitalizationController {

    private final IHospitalzationService hospitalizationService;

    @GetMapping("/{id}")
    public ResponseEntity<HospitalizationResponseDto> getHospitalizationById(@PathVariable Long id) {
        HospitalizationResponseDto response = hospitalizationService.listHospitalizationByPatientId(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<HospitalizationResponseDto> createHospitalization(@Valid @RequestBody CreateHospitalizationDto createHospitalizationDto) {
        HospitalizationResponseDto response = hospitalizationService.createHospitalization(createHospitalizationDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/discharge")
    public ResponseEntity<Void> dischargePatient(@PathVariable Long id) {
        hospitalizationService.setDischargeDateById(id);

        return ResponseEntity.noContent().build();
    }
}
