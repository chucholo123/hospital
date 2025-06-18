package com.hospital.backendHospital.models.dto.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRoomDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Capacity is required")
    private int capacity;
}
