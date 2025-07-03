package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.auth.service.AuthService;
import com.hospital.backendHospital.models.dto.supplyMovement.CreateSupplyMovementDto;
import com.hospital.backendHospital.models.dto.supplyMovement.SupplyMovementResponseDto;
import com.hospital.backendHospital.models.entity.User;
import com.hospital.backendHospital.services.ISupplyMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supply-movements")
@RequiredArgsConstructor
public class SupplyMovementController {

    private final ISupplyMovementService supplyMovementService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<List<SupplyMovementResponseDto>> getAllMovements() {
        return ResponseEntity.ok(supplyMovementService.getAllMovements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplyMovementResponseDto> getMovementById(@PathVariable Long id) {
        return ResponseEntity.ok(supplyMovementService.getMovementById(id));
    }

    @PostMapping("/entry")
    public ResponseEntity<SupplyMovementResponseDto> registerEntry(@Valid @RequestBody CreateSupplyMovementDto dto) {
        User user = authService.getAuthenticatedUser();
        return ResponseEntity.ok(supplyMovementService.registerEntry(user, dto));
    }

    @PostMapping("/exit")
    public ResponseEntity<SupplyMovementResponseDto> registerExit(@Valid @RequestBody CreateSupplyMovementDto dto) {
        User user = authService.getAuthenticatedUser();
        return ResponseEntity.ok(supplyMovementService.registerExit(user, dto));
    }

    @GetMapping("/supply/{supplyId}")
    public ResponseEntity<List<SupplyMovementResponseDto>> getMovementsBySupply(@PathVariable Long supplyId) {
        return ResponseEntity.ok(supplyMovementService.getMovementsBySupply(supplyId));
    }
}
