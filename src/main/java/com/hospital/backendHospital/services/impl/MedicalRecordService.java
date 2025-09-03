package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.exceptions.InvalidDataException;
import com.hospital.backendHospital.mappers.MedicalRecordMapper;
import com.hospital.backendHospital.models.dto.medicalRecord.CreateMedicalRecordDto;
import com.hospital.backendHospital.models.dto.medicalRecord.MedicalRecordResponseDto;
import com.hospital.backendHospital.models.entity.Appointment;
import com.hospital.backendHospital.models.entity.Doctor;
import com.hospital.backendHospital.models.entity.MedicalRecord;
import com.hospital.backendHospital.models.entity.Patient;
import com.hospital.backendHospital.repositories.AppointmentRepository;
import com.hospital.backendHospital.repositories.DoctorRepository;
import com.hospital.backendHospital.repositories.MedicalRecordRepository;
import com.hospital.backendHospital.repositories.PatientRepository;
import com.hospital.backendHospital.services.IMedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordService implements IMedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    private final AppointmentRepository appointmentRepository;

    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecordResponseDto> listMedicalRecordsByPatientId(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Patient not found with id " + id));

        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAllByPatientId(patient.getId());

        if (medicalRecords.isEmpty()){
            throw new EntityNotFoundException("Medical records not found");
        }

        return medicalRecordMapper.toListMedicalRecords(medicalRecords);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalRecordResponseDto listMedicalRecordByAppointmentId(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Appointment not found with id " + id));

        MedicalRecord medicalRecord = medicalRecordRepository.findByAppointmentId(appointment.getId()).orElseThrow(()-> new EntityNotFoundException("Medical record not found"));

        return medicalRecordMapper.toResponseDto(medicalRecord);
    }

    // Considerar crear historial medico solo para citas completadas
    @Override
    @Transactional
    public MedicalRecordResponseDto createMedicalRecord(CreateMedicalRecordDto createMedicalRecordDto) {
        Patient patient = patientRepository.findById(createMedicalRecordDto.getPatientId()).orElseThrow(()-> new EntityNotFoundException("Patient not found"));

        Doctor doctor = doctorRepository.findById(createMedicalRecordDto.getDoctorId()).orElseThrow(()-> new EntityNotFoundException("Doctor not found"));

        Appointment appointment = appointmentRepository.findById(createMedicalRecordDto.getAppointmentId()).orElseThrow(()-> new EntityNotFoundException("Appointment not found"));

        if (medicalRecordRepository.existsByAppointmentId(createMedicalRecordDto.getAppointmentId())){
            throw new InvalidDataException("Medical record already exists for this appointment");
        }

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .patient(patient)
                .doctor(doctor)
                .appointment(appointment)
                .date(LocalDate.now())
                .diagnostics(createMedicalRecordDto.getDiagnostics())
                .treatment(createMedicalRecordDto.getTreatment())
                .build();

        medicalRecordRepository.save(medicalRecord);

        return medicalRecordMapper.toResponseDto(medicalRecord);
    }
}
