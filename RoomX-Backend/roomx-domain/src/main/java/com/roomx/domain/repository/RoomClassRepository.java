package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.RoomClass;

import java.util.Optional;

public interface RoomClassRepository {
    Optional<RoomClass> findById(String id);
    RoomClass save(RoomClass roomClass);

    boolean checkExistsRoomClassCode(String roomClassCode);

    Optional<RoomClass> findByRoomClassCode(String roomClassCode);
}
