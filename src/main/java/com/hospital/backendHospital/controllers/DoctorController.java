package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.auth.service.AuthService;
import com.hospital.backendHospital.models.dto.doctor.CreateDoctorDto;
import com.hospital.backendHospital.models.dto.doctor.DoctorResponseDto;
import com.hospital.backendHospital.models.dto.doctor.UpdateDoctorDto;
import com.hospital.backendHospital.models.entity.User;
import com.hospital.backendHospital.services.IDoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final IDoctorService doctorService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<List<DoctorResponseDto>> listDoctors(){
        List<DoctorResponseDto> doctors = doctorService.listDoctors();
        return ResponseEntity.ok(doctors);
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDto> createDoctor(@Valid @RequestBody CreateDoctorDto createDoctorDto){
        DoctorResponseDto doctorResponseDto = doctorService.createDoctor(createDoctorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorResponseDto);
    }

    @PatchMapping("/update/me")
    public ResponseEntity<DoctorResponseDto> updateDoctor(@Valid @RequestBody UpdateDoctorDto updateDoctorDto){
        User user = authService.getAuthenticatedUser();
        DoctorResponseDto doctorResponseDto = doctorService.updateDoctor(user.getId(), updateDoctorDto);
        return ResponseEntity.ok(doctorResponseDto);
    }

    @PatchMapping("/{id}/desactive")
    public ResponseEntity<Void> desactiveDoctor(@PathVariable Long id){
        doctorService.desactiveDoctorById(id);
        return ResponseEntity.noContent().build();
    }
}
