package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.exceptions.InvalidDataException;
import com.hospital.backendHospital.mappers.DoctorScheduleMapper;
import com.hospital.backendHospital.models.dto.doctorSchedule.CreateDoctorScheduleDto;
import com.hospital.backendHospital.models.dto.doctorSchedule.DoctorScheduleResponseDto;
import com.hospital.backendHospital.models.entity.Doctor;
import com.hospital.backendHospital.models.entity.DoctorSchedule;
import com.hospital.backendHospital.repositories.DoctorRepository;
import com.hospital.backendHospital.repositories.DoctorScheduleRepository;
import com.hospital.backendHospital.services.IDoctorScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorScheduleService implements IDoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorScheduleMapper doctorScheduleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DoctorScheduleResponseDto> listDoctorSchedules() {
        List<DoctorSchedule> doctorSchedules = doctorScheduleRepository.findAll();
        return doctorScheduleMapper.toListSchedules(doctorSchedules);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorScheduleResponseDto listScheduleByDoctorId(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Doctor not found"));

        DoctorSchedule doctorSchedule = doctorScheduleRepository.findByDoctorId(doctor.getId());

        if (doctorSchedule == null){
            throw new EntityNotFoundException("Schedule not found");
        }

        return doctorScheduleMapper.toResponseDto(doctorSchedule);
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
