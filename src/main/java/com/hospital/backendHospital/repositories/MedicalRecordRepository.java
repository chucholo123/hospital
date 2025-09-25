package com.hospital.backendHospital.repositories;

import com.hospital.backendHospital.models.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    Optional<MedicalRecord> findByPatientId(Long id);

    boolean existsByPatientId(Long id);
}
