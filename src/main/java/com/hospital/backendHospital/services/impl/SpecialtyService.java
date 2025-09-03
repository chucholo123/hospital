package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.mappers.SpecialtyMapper;
import com.hospital.backendHospital.models.dto.specialty.CreateSpecialtyDto;
import com.hospital.backendHospital.models.dto.specialty.SpecialtyResponseDto;
import com.hospital.backendHospital.models.entity.Specialty;
import com.hospital.backendHospital.repositories.SpecialtyRepository;
import com.hospital.backendHospital.services.ISpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyService implements ISpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SpecialtyResponseDto> listSpecialties() {

        List<Specialty> specialties = specialtyRepository.findAll();

        return specialtyMapper.toListDtos(specialties);
    }

    @Override
    @Transactional(readOnly = true)
    public SpecialtyResponseDto listSpecialtyByName(String name) {
        Specialty specialty = specialtyRepository.findByName(name).orElseThrow(()-> new EntityNotFoundException("Specialty not found"));

        return specialtyMapper.toResponseDto(specialty);
    }

    @Override
    @Transactional
    public SpecialtyResponseDto createSpecialty(CreateSpecialtyDto createSpecialtyDto) {
        if (specialtyRepository.existsByName(createSpecialtyDto.getName())){
            throw new IllegalArgumentException("Specialty already exists");
        }

        Specialty specialty = specialtyMapper.toEntity(createSpecialtyDto);
        specialtyRepository.save(specialty);

        return specialtyMapper.toResponseDto(specialty);
    }
}
