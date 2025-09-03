package com.hospital.backendHospital.services;

import com.hospital.backendHospital.exceptions.InvalidDataException;
import com.hospital.backendHospital.mappers.MedicalSupplyMapper;
import com.hospital.backendHospital.models.dto.medicalSupply.CreateMedicalSupplyDto;
import com.hospital.backendHospital.models.dto.medicalSupply.MedicalSupplyResponseDto;
import com.hospital.backendHospital.models.entity.MedicalSupply;
import com.hospital.backendHospital.models.entity.UnityEnum;
import com.hospital.backendHospital.models.filters.MedicalSupplyFilterRequest;
import com.hospital.backendHospital.repositories.MedicalSupplyRepository;
import com.hospital.backendHospital.services.impl.MedicalSupplyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalSupplyServiceTest {

    @Mock private MedicalSupplyRepository medicalSupplyRepository;
    @Mock private MedicalSupplyMapper medicalSupplyMapper;

    @InjectMocks
    private MedicalSupplyService medicalSupplyService;

    private MedicalSupply medicalSupply;
    private MedicalSupplyResponseDto responseDto;
    private CreateMedicalSupplyDto createDto;

    @BeforeEach
    void setUp() {
        createDto = CreateMedicalSupplyDto.builder()
                .name("paracetamol")
                .quantity(40)
                .minimumStock(10)
                .unity(UnityEnum.BOX)
                .build();

        medicalSupply = MedicalSupply.builder()
                .name("paracetamol")
                .quantity(40)
                .minimumStock(10)
                .unity(UnityEnum.BOX)
                .build();

        responseDto = MedicalSupplyResponseDto.builder()
                .name(medicalSupply.getName())
                .quantity(medicalSupply.getQuantity())
                .minimumStock(medicalSupply.getMinimumStock())
                .unity(medicalSupply.getUnity())
                .build();

    }

    @Test
    void testGetMedicalSupply() {
        MedicalSupplyFilterRequest filter = MedicalSupplyFilterRequest.builder()
                .name("paracetamol")
                .build();

        Pageable pageable = PageRequest.of(0, 5);

        List<MedicalSupply> medicalSupplies = List.of(medicalSupply);
        Page<MedicalSupply> medicalSupplyPage = new PageImpl<>(medicalSupplies, pageable, medicalSupplies.size());

        when(medicalSupplyRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(medicalSupplyPage);
        when(medicalSupplyMapper.toResponseDto(medicalSupply)).thenReturn(responseDto);

        Page<MedicalSupplyResponseDto> result = medicalSupplyService.filterMedicalSupply(filter, pageable);

        MedicalSupplyResponseDto resultDto = result.getContent().get(0);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(medicalSupply.getName(), resultDto.getName());

        verify(medicalSupplyRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testCreateSupply() {
        when(medicalSupplyRepository.existsByName(createDto.getName())).thenReturn(false);
        when(medicalSupplyMapper.toEntity(createDto)).thenReturn(medicalSupply);

        ArgumentCaptor<MedicalSupply> medicalSupplyCaptor = ArgumentCaptor.forClass(MedicalSupply.class);
        when(medicalSupplyRepository.save(medicalSupplyCaptor.capture())).thenReturn(medicalSupply);

        when(medicalSupplyMapper.toResponseDto(medicalSupply)).thenReturn(responseDto);

        MedicalSupplyResponseDto result = medicalSupplyService.createSupply(createDto);

        // Verificar el objeto guardado
        MedicalSupply savedSupply = medicalSupplyCaptor.getValue();

        assertEquals(createDto.getName(), savedSupply.getName());
        assertEquals(createDto.getQuantity(), savedSupply.getQuantity());
        assertEquals(createDto.getMinimumStock(), savedSupply.getMinimumStock());
        assertEquals(createDto.getUnity(), savedSupply.getUnity());

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(responseDto, result);

        verify(medicalSupplyRepository).existsByName(createDto.getName());
        verify(medicalSupplyMapper).toEntity(createDto);
        verify(medicalSupplyRepository).save(any(MedicalSupply.class));
        verify(medicalSupplyMapper).toResponseDto(medicalSupply);
    }

    @Test
    void testCreateSupply_ShouldThrowInvalidData() {
        when(medicalSupplyRepository.existsByName(createDto.getName())).thenReturn(true);

        InvalidDataException exception = assertThrows(InvalidDataException.class, ()-> medicalSupplyService.createSupply(createDto));

        assertEquals("Medical supply already exists", exception.getMessage());
    }
}
