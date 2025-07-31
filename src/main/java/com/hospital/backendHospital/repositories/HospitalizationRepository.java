package com.hospital.backendHospital.repositories;

import com.hospital.backendHospital.models.entity.Hospitalization;
import com.hospital.backendHospital.models.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HospitalizationRepository extends JpaRepository<Hospitalization, Long>, JpaSpecificationExecutor<Hospitalization> {

    boolean existsByPatientAndActiveTrue(Patient patient);
}
