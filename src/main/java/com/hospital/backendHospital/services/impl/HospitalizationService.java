package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.exceptions.InvalidDataException;
import com.hospital.backendHospital.mappers.HospitalizationMapper;
import com.hospital.backendHospital.models.dto.hospitalization.CreateHospitalizationDto;
import com.hospital.backendHospital.models.dto.hospitalization.HospitalizationResponseDto;
import com.hospital.backendHospital.models.entity.Hospitalization;
import com.hospital.backendHospital.models.entity.Patient;
import com.hospital.backendHospital.models.entity.Room;
import com.hospital.backendHospital.repositories.HospitalizationRepository;
import com.hospital.backendHospital.repositories.PatientRepository;
import com.hospital.backendHospital.repositories.RoomRepository;
import com.hospital.backendHospital.services.IHospitalzationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HospitalizationService implements IHospitalzationService {

    private final HospitalizationRepository hospitalizationRepository;
    private final HospitalizationMapper hospitalizationMapper;
    private final PatientRepository patientRepository;
    private final RoomRepository roomRepository;

    @Override
    @Transactional(readOnly = true)
    public HospitalizationResponseDto listHospitalizationByPatientId(Long id) {
        Hospitalization hospitalization = hospitalizationRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Hospitalization not found"));

        return hospitalizationMapper.toDto(hospitalization);
    }

    @Override
    @Transactional
    public HospitalizationResponseDto createHospitalization(CreateHospitalizationDto createHospitalizationDto) {
        Patient patient = patientRepository.findById(createHospitalizationDto.getPatientId()).orElseThrow(()-> new EntityNotFoundException("Patient not found"));

        Room room = roomRepository.findById(createHospitalizationDto.getRoomId()).orElseThrow(()-> new EntityNotFoundException("Room not found"));

        boolean isAlreadyHospitalized = hospitalizationRepository.existsByPatientAndIsActiveTrue(patient);

        if (isAlreadyHospitalized){
            throw new InvalidDataException("Patient is already hospitalized");
        }

        if (!room.isAvailable()){
            throw new InvalidDataException("Room isnt available");
        }

        Hospitalization hospitalization = Hospitalization.builder()
                .patient(patient)
                .room(room)
                .admissionDate(LocalDate.now())
                .dischargeDate(null)
                .reason(createHospitalizationDto.getReason())
                .costPerDay(createHospitalizationDto.getCostPerDay())
                .isActive(true)
                .build();

        room.setAvailable(false);

        roomRepository.save(room);

        hospitalizationRepository.save(hospitalization);

        return hospitalizationMapper.toDto(hospitalization);
    }

    @Override
    @Transactional
    public void setDischargeDateById(Long id) {
        Hospitalization hospitalization = hospitalizationRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Hospitalzation not found"));

        hospitalization.setDischargeDate(LocalDate.now());
        hospitalization.setActive(false);
        hospitalization.getRoom().setAvailable(true);

        hospitalizationRepository.save(hospitalization);
    }
}
