package com.hospital.backendHospital.services;

import com.hospital.backendHospital.models.dto.room.CreateRoomDto;
import com.hospital.backendHospital.models.dto.room.RoomResponseDto;

import java.util.List;

public interface IRoomService {

    List<RoomResponseDto> listRooms();

    RoomResponseDto listRoomById(Long id);

    RoomResponseDto createRoom(CreateRoomDto createRoomDto);
}
