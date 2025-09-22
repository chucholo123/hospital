package com.hospital.backendHospital.repositories;

import com.hospital.backendHospital.models.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepository extends JpaRepository<FileEntity, UUID> {
}
