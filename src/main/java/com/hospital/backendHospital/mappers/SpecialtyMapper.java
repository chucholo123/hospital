package com.hospital.backendHospital.mappers;

import com.hospital.backendHospital.models.dto.specialty.CreateSpecialtyDto;
import com.hospital.backendHospital.models.dto.specialty.SpecialtyResponseDto;
import com.hospital.backendHospital.models.entity.Specialty;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {

    Specialty toEntity(CreateSpecialtyDto createSpecialtyDto);

    SpecialtyResponseDto toResponseDto(Specialty specialty);

    List<SpecialtyResponseDto> toListDtos(List<Specialty> specialties);
}
