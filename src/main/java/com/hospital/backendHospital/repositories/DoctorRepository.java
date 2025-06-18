package com.hospital.backendHospital.repositories;

import com.hospital.backendHospital.models.entity.Doctor;
import com.hospital.backendHospital.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

    Optional<Doctor> findByUser(User user);
}
