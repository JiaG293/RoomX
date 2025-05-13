package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.RoomClass;

import java.math.BigDecimal;
import java.util.Optional;

public interface RoomClassRepository {
    Optional<RoomClass> findById(String id);
    RoomClass save(RoomClass roomClass);

    boolean checkExistsRoomClassCode(String roomClassCode);

    Optional<RoomClass> findByRoomClassCode(String roomClassCode);

    Optional<RoomClass> findByIdAndStatus(String roomClassId, String status);

}
