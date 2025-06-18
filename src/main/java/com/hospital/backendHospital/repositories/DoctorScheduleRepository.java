package com.hospital.backendHospital.repositories;

import com.hospital.backendHospital.models.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
    DoctorSchedule findByDoctorId(Long id);

    Boolean existsByDoctorIdAndDayOfWeek(Long id, DayOfWeek dayOfWeek);
}
