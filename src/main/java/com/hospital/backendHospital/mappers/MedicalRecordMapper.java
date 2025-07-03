package com.hospital.backendHospital.mappers;

import com.hospital.backendHospital.models.dto.medicalRecord.MedicalRecordResponseDto;
import com.hospital.backendHospital.models.entity.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    @Mapping(target = "patientName", source = "patient.user.firstName")
    @Mapping(target = "doctorName", source = "doctor.user.firstName")
    @Mapping(target = "appointmentId", source = "appointment.id")
    MedicalRecordResponseDto toResponseDto(MedicalRecord medicalRecord);

    List<MedicalRecordResponseDto> toListMedicalRecords(List<MedicalRecord> medicalRecords);
}
