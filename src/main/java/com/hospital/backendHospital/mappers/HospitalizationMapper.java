package com.hospital.backendHospital.mappers;

import com.hospital.backendHospital.models.dto.hospitalization.HospitalizationResponseDto;
import com.hospital.backendHospital.models.entity.Hospitalization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HospitalizationMapper {

    @Mapping(target = "patientName", source = "patient.user.firstName")
    @Mapping(target = "roomName", source = "room.name")
    HospitalizationResponseDto toDto(Hospitalization hospitalization);
}
