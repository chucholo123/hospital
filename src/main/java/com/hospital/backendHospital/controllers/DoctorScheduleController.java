package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.auth.service.AuthService;
import com.hospital.backendHospital.models.dto.doctorSchedule.CreateDoctorScheduleDto;
import com.hospital.backendHospital.models.dto.doctorSchedule.DoctorScheduleResponseDto;
import com.hospital.backendHospital.services.IDoctorScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctorSchedules")
public class DoctorScheduleController {

    private final IDoctorScheduleService doctorScheduleService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<List<DoctorScheduleResponseDto>> listSchedules(){
        List<DoctorScheduleResponseDto> schedules = doctorScheduleService.listDoctorSchedules();
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<DoctorScheduleResponseDto> listScheduleByDoctorId(@PathVariable Long id){
        DoctorScheduleResponseDto schedule = doctorScheduleService.listScheduleByDoctorId(id);
        return ResponseEntity.ok(schedule);
    }

    @PostMapping
    public ResponseEntity<DoctorScheduleResponseDto> createSchedule(@Valid @RequestBody CreateDoctorScheduleDto createDoctorScheduleDto){
        DoctorScheduleResponseDto schedule = doctorScheduleService.createDoctorSchedule(createDoctorScheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(schedule);
    }
}
