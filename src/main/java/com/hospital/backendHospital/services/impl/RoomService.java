package com.hospital.backendHospital.services.impl;

import com.hospital.backendHospital.exceptions.EntityNotFoundException;
import com.hospital.backendHospital.exceptions.InvalidDataException;
import com.hospital.backendHospital.mappers.RoomMapper;
import com.hospital.backendHospital.models.dto.room.CreateRoomDto;
import com.hospital.backendHospital.models.dto.room.RoomResponseDto;
import com.hospital.backendHospital.models.entity.Room;
import com.hospital.backendHospital.repositories.RoomRepository;
import com.hospital.backendHospital.services.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponseDto> listRooms() {
        List<Room> rooms = roomRepository.findAll();

        return roomMapper.toListDtos(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    public RoomResponseDto listRoomById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Room not found"));

        return roomMapper.toResponseDto(room);
    }

    @Override
    @Transactional
    public RoomResponseDto createRoom(CreateRoomDto createRoomDto) {
        if (roomRepository.existsByName(createRoomDto.getName())) {
            throw new InvalidDataException("Room name already exists");
        }

        Room room = roomMapper.toEntity(createRoomDto);
        room.setAvailable(true);

        Room savedRoom = roomRepository.save(room);

        return roomMapper.toResponseDto(savedRoom);
    }
}
