package com.hospital.backendHospital.services;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.mappers.MedicalRecordMapper;
import com.hospital.backendHospital.models.dto.medicalRecord.CreateMedicalRecordDto;
import com.hospital.backendHospital.models.dto.medicalRecord.MedicalRecordResponseDto;
import com.hospital.backendHospital.models.entity.*;
import com.hospital.backendHospital.repositories.AppointmentRepository;
import com.hospital.backendHospital.repositories.DoctorRepository;
import com.hospital.backendHospital.repositories.MedicalRecordRepository;
import com.hospital.backendHospital.repositories.PatientRepository;
import com.hospital.backendHospital.services.impl.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

//    @Mock private MedicalRecordRepository medicalRecordRepository;
//    @Mock private DoctorRepository doctorRepository;
//    @Mock private PatientRepository patientRepository;
//    @Mock private AppointmentRepository appointmentRepository;
//    @Mock private MedicalRecordMapper medicalRecordMapper;
//
//    @InjectMocks
//    private MedicalRecordService medicalRecordService;
//
//    private User patientUser;
//    private User doctorUser;
//    private Patient patient;
//    private Appointment appointment;
//    private CreateMedicalRecordDto createMedicalRecordDto;
//    private MedicalRecord medicalRecord;
//    private MedicalRecordResponseDto medicalRecordResponseDto;
//    private Doctor doctor;
//
//
//     @BeforeEach
//     void setUp() {
//         patientUser = User.builder()
//                 .firstName("jesus")
//                 .build();
//
//         doctorUser = User.builder()
//                 .firstName("carlos")
//                 .build();
//
//         patient = Patient.builder()
//                 .id(1L)
//                 .user(patientUser)
//                 .build();
//
//         doctor = Doctor.builder()
//                 .id(1L)
//                 .user(doctorUser)
//                 .build();
//
//         appointment = Appointment.builder()
//                 .id(1L)
//                 .build();
//
//         createMedicalRecordDto = CreateMedicalRecordDto.builder()
//                 .patientId(1L)
//                 .doctorId(1L)
//                 .appointmentId(1L)
//                 .diagnostics("diagnostic")
//                 .treatment("treatment")
//                 .build();
//
//         medicalRecord = MedicalRecord.builder()
//                 .patient(patient)
//                 .doctor(doctor)
//                 .appointment(appointment)
//                 .diagnostics("diagnostic")
//                 .treatment("treatment")
//                 .build();
//
//         medicalRecordResponseDto = MedicalRecordResponseDto.builder()
//                 .patientName("jesus")
//                 .doctorName("carlos")
//                 .build();
//     }
//
//     @Test
//     void testGetMedicalRecordsByPatientId() {
//         List<MedicalRecord> medicalRecords = List.of(medicalRecord);
//         List<MedicalRecordResponseDto> medicalRecordResponse = List.of(medicalRecordResponseDto);
//
//         when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
//         when(medicalRecordRepository.findAllByPatientId(1L)).thenReturn(medicalRecords);
//         when(medicalRecordMapper.toListMedicalRecords(medicalRecords)).thenReturn(medicalRecordResponse);
//
//         List<MedicalRecordResponseDto> result = medicalRecordService.listMedicalRecordsByPatientId(1L);
//
//         assertNotNull(result);
//         assertEquals(1, result.size());
//         assertSame(medicalRecordResponse, result);
//         assertEquals("jesus", result.get(0).getPatientName());
//         assertEquals("carlos", result.get(0).getDoctorName());
//
//         verify(patientRepository).findById(1L);
//         verify(medicalRecordRepository).findAllByPatientId(1L);
//         verify(medicalRecordMapper).toListMedicalRecords(medicalRecords);
//     }
//
//    @Test
//    void testGetMedicalRecordsByPatientId_ShouldThrowEntityNotFound() {
//         when(patientRepository.findById(1L)).thenReturn(Optional.empty());
//
//        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, ()-> medicalRecordService.listMedicalRecordsByPatientId(1L));
//
//        assertEquals("Patient not found with id " + 1, exception.getMessage());
//    }
//
//    @Test
//    void testGetMedicalRecordsByPatientId_WhenMedicalRecordsEmpty() {
//        List<MedicalRecord> medicalRecords = List.of();
//
//        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
//        when(medicalRecordRepository.findAllByPatientId(1L)).thenReturn(medicalRecords);
//
//        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, ()-> medicalRecordService.listMedicalRecordsByPatientId(1L));
//
//        assertEquals("Medical records not found", exception.getMessage());
//    }
}