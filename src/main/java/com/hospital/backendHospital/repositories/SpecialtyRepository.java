package com.hospital.backendHospital.repositories;

import com.hospital.backendHospital.models.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    Optional<Specialty> findByName(String name);

    boolean existsByName(String name);
}
