package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.auth.service.AuthService;
import com.hospital.backendHospital.models.dto.doctorSchedule.CreateDoctorScheduleDto;
import com.hospital.backendHospital.models.dto.doctorSchedule.DoctorScheduleResponseDto;
import com.hospital.backendHospital.models.filters.DoctorScheduleFilterRequest;
import com.hospital.backendHospital.services.IDoctorScheduleService;
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
@RequestMapping("/api/v1/doctorSchedules")
public class DoctorScheduleController {

    private final IDoctorScheduleService doctorScheduleService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<Page<DoctorScheduleResponseDto>> filterDoctorSchedules
            (DoctorScheduleFilterRequest filter, @PageableDefault(page = 0, size = 5) Pageable pageable){
        Page<DoctorScheduleResponseDto> schedules  = doctorScheduleService.filterDoctorSchedules(filter, pageable);

        return ResponseEntity.ok(schedules);
    }

    @PostMapping
    public ResponseEntity<DoctorScheduleResponseDto> createSchedule(@Valid @RequestBody CreateDoctorScheduleDto createDoctorScheduleDto){
        DoctorScheduleResponseDto schedule = doctorScheduleService.createDoctorSchedule(createDoctorScheduleDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(schedule);
    }
}
