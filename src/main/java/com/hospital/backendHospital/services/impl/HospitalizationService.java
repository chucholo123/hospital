package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.exceptions.InvalidDataException;
import com.hospital.backendHospital.mappers.HospitalizationMapper;
import com.hospital.backendHospital.models.dto.hospitalization.CreateHospitalizationDto;
import com.hospital.backendHospital.models.dto.hospitalization.HospitalizationResponseDto;
import com.hospital.backendHospital.models.entity.Hospitalization;
import com.hospital.backendHospital.models.entity.Patient;
import com.hospital.backendHospital.models.entity.Room;
import com.hospital.backendHospital.models.filters.HospitalizationFilterRequest;
import com.hospital.backendHospital.repositories.HospitalizationRepository;
import com.hospital.backendHospital.repositories.PatientRepository;
import com.hospital.backendHospital.repositories.RoomRepository;
import com.hospital.backendHospital.services.IHospitalzationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<HospitalizationResponseDto> filterHospitalization(HospitalizationFilterRequest filter, Pageable pageable) {
        Specification<Hospitalization> spec = Specification.where(null);

        if (filter.getDate() != null){
            spec.and((root, query, cb) ->
                    cb.equal(root.get("admissionDate"), filter.getDate()));
        }

        if (filter.getPatientId() != null){
            spec.and((root, query, cb) ->
                    cb.equal(root.get("patient").get("id"), filter.getPatientId()));
        }

        if (filter.getDoctorId() != null){
            spec.and((root, query, cb) ->
                    cb.equal(root.get("doctor").get("id"), filter.getDoctorId()));
        }

        if (filter.getRoomId() != null){
            spec.and((root, query, cb) ->
                    cb.equal(root.get("room").get("id"), filter.getRoomId()));
        }

        if (filter.getStatus() != null){
            spec.and((root, query, cb) ->
                    cb.equal(root.get("active"), filter.getStatus()));
        }

        return hospitalizationRepository.findAll(spec, pageable)
                .map(hospitalizationMapper::toDto);
    }

    @Override
    @Transactional
    public HospitalizationResponseDto createHospitalization(CreateHospitalizationDto createHospitalizationDto) {
        Patient patient = patientRepository.findById(createHospitalizationDto.getPatientId()).orElseThrow(()-> new EntityNotFoundException("Patient not found"));

        Room room = roomRepository.findById(createHospitalizationDto.getRoomId()).orElseThrow(()-> new EntityNotFoundException("Room not found"));

        boolean isAlreadyHospitalized = hospitalizationRepository.existsByPatientAndActiveTrue(patient);

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
