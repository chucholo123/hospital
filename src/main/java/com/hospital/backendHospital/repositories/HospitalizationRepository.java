package com.hospital.backendHospital.repositories;

import com.hospital.backendHospital.models.entity.Hospitalization;
import com.hospital.backendHospital.models.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalizationRepository extends JpaRepository<Hospitalization, Long> {

    boolean existsByPatientAndIsActiveTrue(Patient patient);
}
