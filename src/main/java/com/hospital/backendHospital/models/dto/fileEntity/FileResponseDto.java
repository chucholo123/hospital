package com.hospital.backendHospital.models.dto.fileEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResponseDto {

    private String name;

    private String url;

    private String type;

    private long size;
}
