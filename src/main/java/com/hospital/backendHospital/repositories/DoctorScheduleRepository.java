package com.hospital.backendHospital.repositories;

import com.hospital.backendHospital.models.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.DayOfWeek;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long>, JpaSpecificationExecutor<DoctorSchedule> {
    DoctorSchedule findByDoctorId(Long id);

    Boolean existsByDoctorIdAndDayOfWeek(Long id, DayOfWeek dayOfWeek);
}
