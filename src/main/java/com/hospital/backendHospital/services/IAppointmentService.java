package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.appointment.AppointmentResponseDto;
import com.hospital.backendHospital.models.dto.appointment.CreateAppointmentDto;
import com.hospital.backendHospital.models.entity.User;

import java.util.List;

public interface IAppointmentService {

    List<AppointmentResponseDto> listAppointmentsByPatientId(Long id);

    AppointmentResponseDto listActiveAppointmentByPatientId(Long id);

    AppointmentResponseDto listActiveAppointmentByPatient(User user);

    AppointmentResponseDto createAppointment(User user, CreateAppointmentDto createAppointmentDto);

    void completeAppointment(Long id);

    void cancelAppointment(User user, Long id);
}
