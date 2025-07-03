package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.exceptions.InvalidDataException;
import com.hospital.backendHospital.mappers.DoctorMapper;
import com.hospital.backendHospital.models.dto.doctor.CreateDoctorDto;
import com.hospital.backendHospital.models.dto.doctor.DoctorResponseDto;
import com.hospital.backendHospital.models.dto.doctor.UpdateDoctorDto;
import com.hospital.backendHospital.models.entity.Appointment;
import com.hospital.backendHospital.models.entity.Doctor;
import com.hospital.backendHospital.models.entity.Specialty;
import com.hospital.backendHospital.models.entity.User;
import com.hospital.backendHospital.repositories.DoctorRepository;
import com.hospital.backendHospital.repositories.SpecialtyRepository;
import com.hospital.backendHospital.repositories.UserRepository;
import com.hospital.backendHospital.services.IDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService implements IDoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final SpecialtyRepository specialtyRepository;
    private final DoctorMapper doctorMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponseDto> listDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctorMapper.toListDtos(doctors);
    }

    @Override
    @Transactional
    public DoctorResponseDto createDoctor(CreateDoctorDto createDoctorDto) {

        Specialty specialty = specialtyRepository.findById(createDoctorDto.getSpecialty()).orElseThrow(()-> new EntityNotFoundException("Specialty not found"));

        if (userRepository.existsByEmail(createDoctorDto.getEmail())){
            throw new IllegalArgumentException("Username already exists");
        }

        User user = User.builder()
                .email(createDoctorDto.getEmail())
                .firstName(createDoctorDto.getFirstName())
                .lastName(createDoctorDto.getLastName())
                .password(passwordEncoder.encode(createDoctorDto.getPassword()))
                .isActive(true)
                .build();

        Doctor doctor = Doctor.builder()
                .user(user)
                .specialty(specialty)
                .isActive(true)
                .build();

        doctorRepository.save(doctor);

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
