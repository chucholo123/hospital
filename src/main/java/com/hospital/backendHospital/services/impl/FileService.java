package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.models.entity.FileEntity;
import com.hospital.backendHospital.services.IFileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.UUID;

public class FileService implements IFileService {
    @Override
    public FileEntity store(MultipartFile file) {
        return null;
    }

    @Override
    public Optional<FileEntity> getFile(UUID id) throws FileNotFoundException {
        return Optional.empty();
    }
}
