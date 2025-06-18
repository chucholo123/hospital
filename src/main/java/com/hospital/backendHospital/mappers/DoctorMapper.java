package com.hospital.backendHospital.mappers;

import com.hospital.backendHospital.models.dto.doctor.DoctorResponseDto;
import com.hospital.backendHospital.models.dto.doctor.UpdateDoctorDto;
import com.hospital.backendHospital.models.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "specialty", source = "specialty.name")
    @Mapping(target = "isActive", source = "active")
    DoctorResponseDto toResponseDto(Doctor doctor);

    List<DoctorResponseDto> toListDtos(List<Doctor> doctors);
}
