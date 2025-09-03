package com.hospital.backendHospital.services;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.mappers.SpecialtyMapper;
import com.hospital.backendHospital.models.dto.specialty.CreateSpecialtyDto;
import com.hospital.backendHospital.models.dto.specialty.SpecialtyResponseDto;
import com.hospital.backendHospital.models.entity.Specialty;
import com.hospital.backendHospital.repositories.SpecialtyRepository;
import com.hospital.backendHospital.services.impl.SpecialtyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpecialtyServiceTest {

    @Mock private SpecialtyRepository specialtyRepository;
    @Mock private SpecialtyMapper specialtyMapper;

    @InjectMocks
    private SpecialtyService specialtyService;

    private CreateSpecialtyDto createDto;
    private SpecialtyResponseDto responseDto;
    private Specialty specialty;

    @BeforeEach
    void setUp() {
        createDto = CreateSpecialtyDto.builder()
                .name("Podiatry")
                .description("This specialty is for kids")
                .cost(BigDecimal.valueOf(500.00))
                .build();

        specialty = Specialty.builder()
                .name(createDto.getName())
                .description(createDto.getDescription())
                .cost(createDto.getCost())
                .build();
    }

    @Test
    void testGetSpecialties() {
        Specialty specialty2 = Specialty.builder()
                .name("General")
                .description("General")
                .cost(BigDecimal.valueOf(250))
                .build();

        List<Specialty> specialties = List.of(specialty, specialty2);
        List<SpecialtyResponseDto> specialtiesDto = List.of(
                new SpecialtyResponseDto(),
                new SpecialtyResponseDto()
        );

        when(specialtyRepository.findAll()).thenReturn(specialties);
        when(specialtyMapper.toListDtos(specialties)).thenReturn(specialtiesDto);

        List<SpecialtyResponseDto> result = specialtyService.listSpecialties();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertSame(specialtiesDto, result);

        verify(specialtyRepository).findAll();
        verify(specialtyMapper).toListDtos(specialties);
    }

    @Test
    void testGetSpecialties_WhenEmptyList() {
        when(specialtyRepository.findAll()).thenReturn(List.of());
        when(specialtyMapper.toListDtos(List.of())).thenReturn(List.of());

        List<SpecialtyResponseDto> result = specialtyService.listSpecialties();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(specialtyRepository).findAll();
        verify(specialtyMapper).toListDtos(List.of());
    }

    @Test
    void testGetSpecialtyByName() {
        SpecialtyResponseDto expectedResponse = new SpecialtyResponseDto();
        when(specialtyRepository.findByName(specialty.getName())).thenReturn(Optional.of(specialty));
        when(specialtyMapper.toResponseDto(specialty)).thenReturn(expectedResponse);

        SpecialtyResponseDto actualResponse = specialtyService.listSpecialtyByName(specialty.getName());

        assertNotNull(actualResponse);
        assertSame(expectedResponse, actualResponse);

        verify(specialtyRepository).findByName(specialty.getName());
        verify(specialtyMapper).toResponseDto(specialty);
    }

    @Test
    void testGetSpecialtyByName_ShouldThrowSpecialtyNotFound() {
        String nonExistingName = "NonExistingSpecialty";
        when(specialtyRepository.findByName(nonExistingName)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> specialtyService.listSpecialtyByName(nonExistingName));

        assertEquals("Specialty not found", exception.getMessage());

        verify(specialtyRepository).findByName(nonExistingName);
        verifyNoInteractions(specialtyMapper);
    }

    @Test
    void testCreateSpecialty() {
        SpecialtyResponseDto responseDto = new SpecialtyResponseDto();

        when(specialtyRepository.existsByName(createDto.getName())).thenReturn(false);
        when(specialtyMapper.toEntity(createDto)).thenReturn(specialty);
        when(specialtyRepository.save(specialty)).thenReturn(specialty);
        when(specialtyMapper.toResponseDto(specialty)).thenReturn(responseDto);

        SpecialtyResponseDto result = specialtyService.createSpecialty(createDto);

        assertNotNull(result);
        assertEquals(responseDto, result);

        verify(specialtyRepository).existsByName(createDto.getName());
        verify(specialtyMapper).toEntity(createDto);
        verify(specialtyRepository).save(specialty);
        verify(specialtyMapper).toResponseDto(specialty);
    }

    @Test
    void testCreateSpecialty_WhenNameExists_ShouldThrowException() {
        when(specialtyRepository.existsByName(createDto.getName())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            specialtyService.createSpecialty(createDto);
        });

        verify(specialtyRepository).existsByName(createDto.getName());
        verifyNoMoreInteractions(specialtyRepository, specialtyMapper);
    }
}
