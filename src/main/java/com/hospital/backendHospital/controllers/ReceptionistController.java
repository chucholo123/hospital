package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.models.dto.recepcionist.CreateReceptionistDto;
import com.hospital.backendHospital.models.dto.recepcionist.ReceptionistResponseDto;
import com.hospital.backendHospital.models.dto.recepcionist.UpdateReceptionistDto;
import com.hospital.backendHospital.services.IReceptionistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/receptionists")
public class ReceptionistController {

    private final IReceptionistService receptionistService;

    @GetMapping
    public ResponseEntity<List<ReceptionistResponseDto>> getReceptionists(){
        List<ReceptionistResponseDto> receptionists = receptionistService.getReceptionists();

        return ResponseEntity.ok(receptionists);
    }

    @PostMapping("/create")
    public ResponseEntity<ReceptionistResponseDto> createReceptionist(@Valid @RequestBody CreateReceptionistDto createReceptionistDto){
        ReceptionistResponseDto receptionist = receptionistService.createReceptionist(createReceptionistDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(receptionist);

    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<ReceptionistResponseDto> updateReceptionist(@PathVariable Long id, @Valid @RequestBody UpdateReceptionistDto updateReceptionistDto){
        ReceptionistResponseDto receptionist = receptionistService.updateReceptionist(id, updateReceptionistDto);

        return ResponseEntity.ok(receptionist);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateReceptionist(Long id){
        receptionistService.deactivateReceptionist(id);

        return ResponseEntity.noContent().build();
    }
}
