package com.hospital.backendHospital.mappers;

import com.hospital.backendHospital.models.dto.room.CreateRoomDto;
import com.hospital.backendHospital.models.dto.room.RoomResponseDto;
import com.hospital.backendHospital.models.entity.Room;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    Room toEntity(CreateRoomDto createRoomDto);

    RoomResponseDto toResponseDto(Room room);

    List<RoomResponseDto> toListDtos(List<Room> rooms);
}
