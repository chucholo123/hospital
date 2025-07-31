package com.hospital.backendHospital.repositories;

import com.hospital.backendHospital.models.entity.MedicalSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MedicalSupplyRepository extends JpaRepository<MedicalSupply, Long>, JpaSpecificationExecutor<MedicalSupply> {

    boolean existsByName(String name);
}
