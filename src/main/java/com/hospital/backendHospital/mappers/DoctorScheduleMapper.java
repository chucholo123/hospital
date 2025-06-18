package com.hospital.backendHospital.mappers;

import com.hospital.backendHospital.models.dto.doctorSchedule.DoctorScheduleResponseDto;
import com.hospital.backendHospital.models.entity.DoctorSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorScheduleMapper {

    @Mapping(target = "doctorName", source = "doctor.user.username")
    DoctorScheduleResponseDto toResponseDto(DoctorSchedule doctorSchedule);

    List<DoctorScheduleResponseDto> toListSchedules(List<DoctorSchedule> doctorSchedules);
}
