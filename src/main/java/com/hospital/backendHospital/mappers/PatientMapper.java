package com.hospital.backendHospital.mappers;

import com.hospital.backendHospital.models.dto.patient.PatientResponseDto;
import com.hospital.backendHospital.models.dto.patient.PatientSummaryDto;
import com.hospital.backendHospital.models.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "isActive", source = "active")
    PatientResponseDto toResponseDto(Patient patient);

    List<PatientSummaryDto> toListSummaryDtos(List<Patient> patients);

    List<PatientResponseDto> toListDtos(List<Patient> patients);
}
