package com.g96.ftms.repository;

import com.g96.ftms.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByRoomName(String roomName);

    Optional<Room> findByRoomName(String roomName);
}