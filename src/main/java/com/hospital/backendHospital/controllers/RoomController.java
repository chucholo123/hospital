package com.hospital.backendHospital.controllers;

import com.hospital.backendHospital.models.dto.room.CreateRoomDto;
import com.hospital.backendHospital.models.dto.room.RoomResponseDto;
import com.hospital.backendHospital.services.IRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final IRoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> listRooms(){
        List<RoomResponseDto> rooms = roomService.listRooms();

        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDto> listRoomById(@PathVariable Long id){
        RoomResponseDto room = roomService.listRoomById(id);

        return ResponseEntity.ok(room);
    }

    @PostMapping
    public ResponseEntity<RoomResponseDto> createRoom(@Valid @RequestBody CreateRoomDto createRoomDto){
        RoomResponseDto room = roomService.createRoom(createRoomDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }
}
