package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.Room;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    Optional<Room> findById(String id);
    Room save(Room room);
    boolean checkExistsRoomCode(String roomCode);

    List<Room> findAll();

    List<Room> findAllByStatus(String status);


    List<Room> findAllByCapacityGreaterThanOrEqualAndStatus(int requiredCapacity, String status);

    List<Room> findAllByBranchIdAndStatus(String branchId, String status);

    List<Room> findAllByPlaceIdAndStatus(String placeId, String status);

    Optional<BigDecimal> findPriceByIdAndValidTimestamp(String roomId, Instant timestamp);

}
