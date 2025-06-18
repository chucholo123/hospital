package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.auth.service.AuthService;
import com.hospital.backendHospital.models.dto.appointment.AppointmentResponseDto;
import com.hospital.backendHospital.models.dto.appointment.CreateAppointmentDto;
import com.hospital.backendHospital.models.entity.Patient;
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
    public ResponseEntity<List<AppointmentResponseDto>> listAppointmentsByPatient(@PathVariable Long id){
        List<AppointmentResponseDto> appointments = appointmentService.listAppointmentsByPatientId(id);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patients/me")
    public ResponseEntity<List<AppointmentResponseDto>> listAppointments(){
        User user = authService.getAuthenticatedUser();
        List<AppointmentResponseDto> appointments = appointmentService.listAppointmentsByPatientId(user.getId());
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/patients/{id}/active")
    public ResponseEntity<AppointmentResponseDto> listActiveAppointmentByPatient(@PathVariable Long id){
        AppointmentResponseDto appointment = appointmentService.listActiveAppointmentByPatientId(id);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/patients/active/me")
    public ResponseEntity<AppointmentResponseDto> listActiveAppointment(){
        User user = authService.getAuthenticatedUser();
        AppointmentResponseDto appointment = appointmentService.listActiveAppointmentByPatient(user);
        return ResponseEntity.ok(appointment);
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> createAppointment(@Valid @RequestBody CreateAppointmentDto createAppointmentDto){
        User user = authService.getAuthenticatedUser();
        AppointmentResponseDto appointment = appointmentService.createAppointment(user, createAppointmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> completeAppointment(@PathVariable Long id){
        appointmentService.completeAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id){
        User user = authService.getAuthenticatedUser();
        appointmentService.cancelAppointment(user, id);
        return ResponseEntity.noContent().build();
    }
}
