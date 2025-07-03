package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.auth.service.AuthService;
import com.hospital.backendHospital.models.dto.supplyUsage.CreateSupplyUsageDto;
import com.hospital.backendHospital.models.dto.supplyUsage.SupplyUsageResponseDto;
import com.hospital.backendHospital.models.entity.User;
import com.hospital.backendHospital.services.ISupplyUsageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supply-usage")
@RequiredArgsConstructor
public class SupplyUsageController {

    private final ISupplyUsageService supplyUsageService;
    private final AuthService authService;

    @GetMapping("/hospitalization/{hospitalizationId}")
    public ResponseEntity<List<SupplyUsageResponseDto>> getUsageByHospitalization(@PathVariable Long hospitalizationId) {
        return ResponseEntity.ok(supplyUsageService.getUsageByHospitalization(hospitalizationId));
    }

    @PostMapping
    public ResponseEntity<SupplyUsageResponseDto> registerUsage(@Valid @RequestBody CreateSupplyUsageDto dto) {
        User user = authService.getAuthenticatedUser();
        return ResponseEntity.ok(supplyUsageService.registerUsage(user, dto));
    }
}
