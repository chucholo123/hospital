package com.hospital.backendHospital.models.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponseDto {

    private String name;

    private int capacity;

    private boolean available;
}
