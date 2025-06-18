package com.hospital.backendHospital.repositories;

import com.hospital.backendHospital.models.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByName(String name);
}
