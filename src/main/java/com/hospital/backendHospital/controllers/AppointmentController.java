package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.auth.service.AuthService;
import com.hospital.backendHospital.models.dto.appointment.AppointmentResponseDto;
import com.hospital.backendHospital.models.dto.appointment.CreateAppointmentDto;
import com.hospital.backendHospital.models.entity.User;
import com.hospital.backendHospital.services.IAppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final IAppointmentService appointmentService;
    private final AuthService authService;

    @GetMapping("/patients/{id}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsByPatient(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.listAppointmentsByPatientId(id));
    }

    @GetMapping("/patients/me")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments(){
        User user = authService.getAuthenticatedUser();
        return ResponseEntity.ok(appointmentService.listAppointmentsByPatientId(user.getId()));
    }

    @GetMapping("/patients/{id}/active")
    public ResponseEntity<AppointmentResponseDto> getActiveAppointmentByPatient(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.listActiveAppointmentByPatientId(id));
    }

    @GetMapping("/patients/active/me")
    public ResponseEntity<AppointmentResponseDto> getActiveAppointment(){
        User user = authService.getAuthenticatedUser();
        return ResponseEntity.ok(appointmentService.listActiveAppointmentByPatient(user));
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> createAppointment(@Valid @RequestBody CreateAppointmentDto createAppointmentDto){
        User user = authService.getAuthenticatedUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(user, createAppointmentDto));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> completeAppointment(@PathVariable Long id){
        User user = authService.getAuthenticatedUser();
        appointmentService.completeAppointment(user, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id){
        User user = authService.getAuthenticatedUser();
        appointmentService.cancelAppointment(user, id);
        return ResponseEntity.noContent().build();
    }
}
