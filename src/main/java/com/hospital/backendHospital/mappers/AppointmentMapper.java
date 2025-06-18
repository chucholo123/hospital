package com.hospital.backendHospital.mappers;

import com.hospital.backendHospital.models.dto.appointment.AppointmentResponseDto;
import com.hospital.backendHospital.models.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "patientName", source = "patient.user.username")
    @Mapping(target = "doctorName", source = "doctor.user.username")
    AppointmentResponseDto toResponseDto(Appointment appointment);

    List<AppointmentResponseDto> toListAppointments(List<Appointment> appointments);
}
