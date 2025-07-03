package com.hospital.backendHospital.repositories;

import com.hospital.backendHospital.models.entity.SupplyMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplyMovementRepository extends JpaRepository<SupplyMovement, Long> {

    List<SupplyMovement> findAllBySupplyId(Long supplyId);

}
