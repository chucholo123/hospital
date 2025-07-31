package com.hospital.backendHospital.mappers;

import com.hospital.backendHospital.models.dto.specialty.CreateSpecialtyDto;
import com.hospital.backendHospital.models.dto.specialty.SpecialtyResponseDto;
import com.hospital.backendHospital.models.entity.Specialty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {

    Specialty toEntity(CreateSpecialtyDto createSpecialtyDto);

    @Mapping(target = "doctorCount", expression = "java(countDoctors(specialty))")
    SpecialtyResponseDto toResponseDto(Specialty specialty);

    List<SpecialtyResponseDto> toListDtos(List<Specialty> specialties);

    default int countDoctors(Specialty specialty) {
        return specialty.getDoctor() != null ? specialty.getDoctor().size() : 0;
    }
}
