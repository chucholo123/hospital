package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.auth.service.AuthService;
import com.hospital.backendHospital.models.dto.medicalRecord.CreateMedicalRecordDto;
import com.hospital.backendHospital.models.dto.medicalRecord.MedicalRecordResponseDto;
import com.hospital.backendHospital.models.entity.User;
import com.hospital.backendHospital.services.IMedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medicalRecords")
public class MedicalRecordController {

    private final IMedicalRecordService medicalRecordService;
    private final AuthService authService;

    @GetMapping("/patients/{id}")
    public ResponseEntity<List<MedicalRecordResponseDto>> listMedicalRecordsByPatient(@PathVariable Long id){
        List<MedicalRecordResponseDto> medicalRecords = medicalRecordService.listMedicalRecordsByPatientId(id);
        return ResponseEntity.ok(medicalRecords);
    }

    @GetMapping("/patients/me")
    public ResponseEntity<List<MedicalRecordResponseDto>> listMedicalRecords(){
        User user = authService.getAuthenticatedUser();
        List<MedicalRecordResponseDto> medicalRecords = medicalRecordService.listMedicalRecordsByPatientId(user.getId());
        return ResponseEntity.ok(medicalRecords);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<MedicalRecordResponseDto> listMedicalRecordByAppointment(@PathVariable Long id){
        MedicalRecordResponseDto medicalRecord = medicalRecordService.listMedicalRecordByAppointmentId(id);
        return ResponseEntity.ok(medicalRecord);
    }

    @PostMapping
    public ResponseEntity<MedicalRecordResponseDto> createMedicalRecord(@Valid @RequestBody CreateMedicalRecordDto createMedicalRecordDto){
        MedicalRecordResponseDto medicalRecord = medicalRecordService.createMedicalRecord(createMedicalRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecord);
    }
}