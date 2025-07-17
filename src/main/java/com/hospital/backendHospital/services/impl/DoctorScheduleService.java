package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.exceptions.InvalidDataException;
import com.hospital.backendHospital.mappers.DoctorScheduleMapper;
import com.hospital.backendHospital.models.dto.doctorSchedule.CreateDoctorScheduleDto;
import com.hospital.backendHospital.models.dto.doctorSchedule.DoctorScheduleResponseDto;
import com.hospital.backendHospital.models.entity.Doctor;
import com.hospital.backendHospital.models.entity.DoctorSchedule;
import com.hospital.backendHospital.models.filters.DoctorScheduleFilterRequest;
import com.hospital.backendHospital.repositories.DoctorRepository;
import com.hospital.backendHospital.repositories.DoctorScheduleRepository;
import com.hospital.backendHospital.services.IDoctorScheduleService;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class DoctorScheduleService implements IDoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorScheduleMapper doctorScheduleMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorScheduleResponseDto> filterDoctorSchedules(DoctorScheduleFilterRequest filter, Pageable pageable) {
        Specification<DoctorSchedule> spec = Specification.where(null);

        if (filter.getDayOfWeek() != null){
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("dayOfWeek"), filter.getDayOfWeek()));
        }

        if (filter.getStartTime() != null){
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("startTime"), filter.getStartTime()));
        }

        if (filter.getFirstName() != null){
            spec = spec.and((root, query, cb) -> {
                Join<Object, Object> doctorJoin = root.join("doctor");
                Join<Object, Object> userJoin = doctorJoin.join("user");
                return cb.like(cb.lower(userJoin.get("firstName")), "%" + filter.getFirstName().toLowerCase() + "%");
            });
        }

        if (filter.getLastName() != null && !filter.getLastName().isBlank()) {
            spec = spec.and((root, query, cb) -> {
                Join<Object, Object> doctorJoin = root.join("doctor");
                Join<Object, Object> userJoin = doctorJoin.join("user");
                return cb.like(cb.lower(userJoin.get("lastName")), "%" + filter.getLastName().toLowerCase() + "%");
            });
        }

        return doctorScheduleRepository.findAll(spec, pageable)
                .map(doctorScheduleMapper::toResponseDto);
    }

    @Override
    @Transactional
    public DoctorScheduleResponseDto createDoctorSchedule(CreateDoctorScheduleDto createDoctorScheduleDto) {
        Doctor doctor = doctorRepository.findById(createDoctorScheduleDto.getDoctor()).orElseThrow(()-> new EntityNotFoundException("Doctor not found"));

        if (doctorScheduleRepository.existsByDoctorIdAndDayOfWeek(doctor.getId(), createDoctorScheduleDto.getDayOfWeek())){
            throw new InvalidDataException("Schedule already exist");
        }

        if (createDoctorScheduleDto.getStartTime().isAfter(LocalTime.of(22,0,0))){
            throw new InvalidDataException("Star time must be valid");
        }

        if (createDoctorScheduleDto.getEndTime().isBefore(createDoctorScheduleDto.getStartTime())){
            throw new InvalidDataException("End time must be valid");
        }

        DoctorSchedule doctorSchedule = DoctorSchedule.builder()
                .dayOfWeek(createDoctorScheduleDto.getDayOfWeek())
                .startTime(createDoctorScheduleDto.getStartTime())
                .endTime(createDoctorScheduleDto.getEndTime())
                .doctor(doctor)
                .build();

        doctorScheduleRepository.save(doctorSchedule);

        return doctorScheduleMapper.toResponseDto(doctorSchedule);
    }
}
