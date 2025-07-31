package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.auth.service.AuthService;
import com.hospital.backendHospital.models.dto.patient.CreatePatientDto;
import com.hospital.backendHospital.models.dto.patient.PatientResponseDto;
import com.hospital.backendHospital.models.dto.patient.UpdatePatientDto;
import com.hospital.backendHospital.models.entity.User;
import com.hospital.backendHospital.models.filters.PatientFilterRequest;
import com.hospital.backendHospital.services.IPatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final IPatientService patientService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<Page<PatientResponseDto>> filterPatients(PatientFilterRequest filter, @PageableDefault(page = 0, size = 5) Pageable pageable){
        Page<PatientResponseDto> patients = patientService.filterPatients(filter, pageable);

        return ResponseEntity.ok(patients);
    }

    @PostMapping("/create")
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody CreatePatientDto createPatientDto){
        User user = authService.getAuthenticatedUser();

        PatientResponseDto patient = patientService.createPatient(user, createPatientDto);

        return ResponseEntity.ok(patient);
    }

    @PatchMapping("/update/me")
    public ResponseEntity<PatientResponseDto> updatePatient(@Valid @RequestBody UpdatePatientDto updatePatientDto) {
        User user = authService.getAuthenticatedUser();

        PatientResponseDto patientResponseDto = patientService.updatePatient(user, updatePatientDto);

        return ResponseEntity.ok(patientResponseDto);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivatePatient(@PathVariable Long id) {
        patientService.deactivatePatient(id);

        return ResponseEntity.noContent().build();
    }
}
