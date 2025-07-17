package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.exceptions.InvalidDataException;
import com.hospital.backendHospital.mappers.DoctorMapper;
import com.hospital.backendHospital.models.dto.doctor.CreateDoctorAndScheduleDto;
import com.hospital.backendHospital.models.dto.doctor.DoctorResponseDto;
import com.hospital.backendHospital.models.dto.doctor.UpdateDoctorDto;
import com.hospital.backendHospital.models.entity.*;
import com.hospital.backendHospital.models.filters.DoctorFilterRequest;
import com.hospital.backendHospital.repositories.DoctorRepository;
import com.hospital.backendHospital.repositories.DoctorScheduleRepository;
import com.hospital.backendHospital.repositories.SpecialtyRepository;
import com.hospital.backendHospital.repositories.UserRepository;
import com.hospital.backendHospital.services.IDoctorService;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService implements IDoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final UserRepository userRepository;
    private final SpecialtyRepository specialtyRepository;
    private final DoctorMapper doctorMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorResponseDto> filterDoctors(DoctorFilterRequest filter, Pageable pageable) {
        Specification<Doctor> spec = Specification.where(null);

        if (filter.getFirstName() != null) {
            spec = spec.and((root, query, cb) -> {
                Join<Object, Object> userJoin = root.join("user");
                return cb.like(cb.lower(userJoin.get("firstName")), "%" + filter.getFirstName().toLowerCase() + "%");
            });
        }

        if (filter.getLastName() != null) {
            spec = spec.and((root, query, cb) -> {
                Join<Object, Object> userJoin = root.join("user");
                return cb.like(cb.lower(userJoin.get("lastName")), "%" + filter.getLastName().toLowerCase() + "%");
            });
        }

        if (filter.getSpecialty() != null && !filter.getSpecialty().isBlank()) {
            spec = spec.and((root, query, cb) -> {
                Join<Object, Object> specialtyJoin = root.join("specialty");
                return cb.equal(cb.lower(specialtyJoin.get("name")), filter.getSpecialty().toLowerCase());
            });
        }

        if (filter.getStatus() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("isActive"), filter.getStatus()));
        }

        return doctorRepository.findAll(spec, pageable)
                .map(doctorMapper::toResponseDto);
    }

    @Override
    @Transactional
    public DoctorResponseDto createDoctor(CreateDoctorAndScheduleDto createDoctorAndScheduleDto) {

        Specialty specialty = specialtyRepository.findById(createDoctorAndScheduleDto.getSpecialty()).orElseThrow(()-> new EntityNotFoundException("Specialty not found"));

        if (userRepository.existsByEmail(createDoctorAndScheduleDto.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }

        User user = User.builder()
                .email(createDoctorAndScheduleDto.getEmail())
                .firstName(createDoctorAndScheduleDto.getFirstName())
                .lastName(createDoctorAndScheduleDto.getLastName())
                .password(passwordEncoder.encode(createDoctorAndScheduleDto.getPassword()))
                .isActive(true)
                .build();

        Doctor doctor = Doctor.builder()
                .user(user)
                .specialty(specialty)
                .isActive(true)
                .build();

        doctorRepository.save(doctor);

        for (var scheduleDto : createDoctorAndScheduleDto.getSchedules()) {

            if (doctorScheduleRepository.existsByDoctorIdAndDayOfWeek(doctor.getId(), scheduleDto.getDayOfWeek())) {
                throw new InvalidDataException("Schedule already exists for day: " + scheduleDto.getDayOfWeek());
            }

            if (scheduleDto.getStartTime().isAfter(LocalTime.of(22, 0))) {
                throw new InvalidDataException("Start time must be before 22:00");
            }

            if (scheduleDto.getEndTime().isBefore(scheduleDto.getStartTime())) {
                throw new InvalidDataException("End time must be after start time");
            }

            DoctorSchedule doctorSchedule = DoctorSchedule.builder()
                    .doctor(doctor)
                    .dayOfWeek(scheduleDto.getDayOfWeek())
                    .startTime(scheduleDto.getStartTime())
                    .endTime(scheduleDto.getEndTime())
                    .build();

            doctorScheduleRepository.save(doctorSchedule);
        }

        return doctorMapper.toResponseDto(doctor);
    }

    @Override
    @Transactional
    public DoctorResponseDto updateDoctor(Long id, UpdateDoctorDto updateDoctorDto) {

        if (updateDoctorDto == null){
            throw new InvalidDataException("UpdateDoctor cannot be null");
        }

        Doctor doctor = doctorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Doctor not found with id " + id));

        if (updateDoctorDto.getFirstName() != null){
            doctor.getUser().setFirstName(updateDoctorDto.getFirstName());
        }
        if (updateDoctorDto.getLastName() != null){
            doctor.getUser().setLastName(updateDoctorDto.getLastName());
        }
        if (updateDoctorDto.getNewPassword() != null){
            doctor.getUser().setPassword(passwordEncoder.encode(updateDoctorDto.getNewPassword()));
        }

        doctorRepository.save(doctor);

        return doctorMapper.toResponseDto(doctor);
    }

    @Override
    @Transactional
    public void desactiveDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Doctor not found with id " + id));

        List<Appointment> appointments = doctor.getAppointments().stream()
                .filter(appointment -> appointment.getDate().isAfter(LocalDate.now()))
                .toList();

        if (!appointments.isEmpty()){
            throw new InvalidDataException("Doctor " + doctor.getUser().getFirstName() + " cannot be deactivated because he has scheduled appointments");
        }

        doctor.setActive(false);
        doctor.getUser().setActive(false);

        doctorRepository.save(doctor);
    }
}
