package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.specialty.CreateSpecialtyDto;
import com.hospital.backendHospital.models.dto.specialty.SpecialtyResponseDto;

import java.util.List;

public interface ISpecialtyService {

    List<SpecialtyResponseDto> listSpecialties();

    SpecialtyResponseDto listSpecialtyByName(String name);

    SpecialtyResponseDto createSpecialty(CreateSpecialtyDto createSpecialtyDto);
}
