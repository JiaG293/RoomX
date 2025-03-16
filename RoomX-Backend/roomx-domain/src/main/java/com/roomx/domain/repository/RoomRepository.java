package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.Room;

import java.util.Optional;

public interface RoomRepository {
    Optional<Room> findById(String id);
    Room save(Room room);
    boolean checkExistsRoomCode(String roomCode);
}
