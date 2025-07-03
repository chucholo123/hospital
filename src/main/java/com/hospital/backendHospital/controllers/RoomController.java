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
    public ResponseEntity<List<RoomResponseDto>> getAllRooms(){
        return ResponseEntity.ok(roomService.listRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDto> listRoomById(@PathVariable Long id){
        return ResponseEntity.ok(roomService.listRoomById(id));
    }

    @PostMapping
    public ResponseEntity<RoomResponseDto> createRoom(@Valid @RequestBody CreateRoomDto createRoomDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(createRoomDto));
    }
}
