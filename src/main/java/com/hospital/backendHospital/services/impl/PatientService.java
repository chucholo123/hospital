package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.mappers.PatientMapper;
import com.hospital.backendHospital.models.dto.patient.PatientResponseDto;
import com.hospital.backendHospital.models.dto.patient.PatientSummaryDto;
import com.hospital.backendHospital.models.dto.patient.UpdatePatientDto;
import com.hospital.backendHospital.models.entity.Patient;
import com.hospital.backendHospital.models.entity.User;
import com.hospital.backendHospital.repositories.PatientRepository;
import com.hospital.backendHospital.repositories.RoleRepository;
import com.hospital.backendHospital.repositories.UserRepository;
import com.hospital.backendHospital.services.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PatientMapper patientMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponseDto> listPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patientMapper.toListDtos(patients);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientSummaryDto> listPatientsByFirstName(String firstName) {
        List<Patient> patients = patientRepository.findAllByUserFirstName(firstName);
        return patientMapper.toListSummaryDtos(patients);
    }

    @Override
    @Transactional
    public Patient createPatient(String username, String bloodType, String emergencyContact) {
        User user = userRepository.findByEmail(username).orElseThrow(()-> new EntityNotFoundException("User not found"));

        Patient patient = Patient.builder()
                .user(user)
                .bloodType(bloodType)
                .emergencyContact(emergencyContact)
                .isActive(true)
                .build();

        patientRepository.save(patient);

        return patient;
    }

    @Override
    @Transactional
    public PatientResponseDto updatePatient(User user, UpdatePatientDto updatePatientDto) {

        if (updatePatientDto == null) {
            throw new IllegalArgumentException("UpdatePatient cannot be null");
        }

        Patient patient = patientRepository.findByUser(user).orElseThrow(()-> new EntityNotFoundException("Patient not found"));

        if (updatePatientDto.getFirstName() != null){
            patient.getUser().setFirstName(updatePatientDto.getFirstName());
        }
        if (updatePatientDto.getLastName() != null){
            patient.getUser().setLastName(updatePatientDto.getLastName());
        }
        if (updatePatientDto.getNewPassword() != null){
            patient.getUser().setPassword(passwordEncoder.encode(updatePatientDto.getNewPassword()));
        }
        if (updatePatientDto.getNewEmergencyContact() != null){
            patient.setEmergencyContact(updatePatientDto.getNewEmergencyContact());
        }

        patientRepository.save(patient);

        return patientMapper.toResponseDto(patient);
    }

    @Override
    @Transactional
    public void desactivePatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Patient not found with id "+ id));

        patient.setActive(false);
        patient.getUser().setActive(false);

        patientRepository.save(patient);
    }
}
