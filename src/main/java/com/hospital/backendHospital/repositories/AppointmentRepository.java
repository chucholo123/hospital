package com.hospital.backendHospital.repositories;

import com.hospital.backendHospital.models.entity.Appointment;
import com.hospital.backendHospital.models.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByPatientId(Long id);

    Appointment findByPatientIdAndStatus(Long id, AppointmentStatus status);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Appointment a WHERE a.date = :date AND ((a.startTime < :endTime AND a.endTime > :startTime))")
    boolean existsOverlappingAppointment(@Param("date") LocalDate date,
                                         @Param("startTime") LocalTime startTime,
                                         @Param("endTime") LocalTime endTime);
}
