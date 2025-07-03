package com.hospital.backendHospital.repositories;

import com.hospital.backendHospital.models.entity.SupplyUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyUsageRepository extends JpaRepository<SupplyUsage, Long> {
}
