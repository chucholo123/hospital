package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.auth.service.AuthService;
import com.hospital.backendHospital.models.dto.doctor.CreateDoctorAndScheduleDto;
import com.hospital.backendHospital.models.dto.doctor.DoctorResponseDto;
import com.hospital.backendHospital.models.dto.doctor.UpdateDoctorDto;
import com.hospital.backendHospital.models.entity.User;
import com.hospital.backendHospital.models.filters.DoctorFilterRequest;
import com.hospital.backendHospital.services.IDoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final IDoctorService doctorService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<Page<DoctorResponseDto>> filterDoctors
            (DoctorFilterRequest filter, @PageableDefault(page = 0, size = 5) Pageable pageable){
        Page<DoctorResponseDto> doctors = doctorService.filterDoctors(filter, pageable);

        return ResponseEntity.ok(doctors);
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDto> createDoctor(@Valid @RequestBody CreateDoctorAndScheduleDto createDoctorAndScheduleDto){
        DoctorResponseDto doctorResponseDto = doctorService.createDoctor(createDoctorAndScheduleDto);
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
