package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.auth.service.AuthService;
import com.hospital.backendHospital.models.dto.patient.CreatePatientDto;
import com.hospital.backendHospital.models.dto.patient.PatientResponseDto;
import com.hospital.backendHospital.models.dto.patient.PatientSummaryDto;
import com.hospital.backendHospital.models.dto.patient.UpdatePatientDto;
import com.hospital.backendHospital.models.entity.User;
import com.hospital.backendHospital.services.IPatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<PatientResponseDto>> listPatients() {
        List<PatientResponseDto> patients = patientService.listPatients();

        return ResponseEntity.ok(patients);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PatientSummaryDto>> listPatientsByFirstName(@RequestParam String firstName) {
        List<PatientSummaryDto> patients = patientService.listPatientsByFirstName(firstName);

        return ResponseEntity.ok(patients);
    }

    @PostMapping("/create-patient")
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody CreatePatientDto createPatientDto){
        PatientResponseDto patient = patientService.createPatient(createPatientDto);

        return ResponseEntity.ok(patient);
    }

    @PatchMapping("/update/me")
    public ResponseEntity<PatientResponseDto> updatePatient(@Valid @RequestBody UpdatePatientDto updatePatientDto) {
        User user = authService.getAuthenticatedUser();

        PatientResponseDto patientResponseDto = patientService.updatePatient(user, updatePatientDto);

        return ResponseEntity.ok(patientResponseDto);
    }

    @PatchMapping("/{id}/desactivate")
    public ResponseEntity<Void> desactivePatient(@PathVariable Long id) {
        patientService.desactivePatientById(id);

        return ResponseEntity.noContent().build();
    }
}
