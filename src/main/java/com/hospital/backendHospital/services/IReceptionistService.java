package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.recepcionist.CreateReceptionistDto;
import com.hospital.backendHospital.models.dto.recepcionist.ReceptionistResponseDto;
import com.hospital.backendHospital.models.dto.recepcionist.UpdateReceptionistDto;

import java.util.List;

public interface IReceptionistService {

    List<ReceptionistResponseDto> getReceptionists();

    ReceptionistResponseDto createReceptionist(CreateReceptionistDto createReceptionistDto);

    ReceptionistResponseDto updateReceptionist(Long id, UpdateReceptionistDto updateReceptionistDto);

    void deactivateReceptionist(Long id);
}
