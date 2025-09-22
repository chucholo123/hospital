package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.fileEntity.FileResponseDto;
import com.hospital.backendHospital.models.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IFileService {

    FileEntity store (MultipartFile file);

    Optional<FileEntity> getFile(UUID id) throws FileNotFoundException;

    List<FileResponseDto> getAllFiles();
}
