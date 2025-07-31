package com.hospital.backendHospital.repositories;

import com.hospital.backendHospital.models.entity.RoleEnum;
import com.hospital.backendHospital.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    List<User> findByRoles_RoleEnum(RoleEnum roleEnum);

    Optional<User> findByEmail(String email);
}
