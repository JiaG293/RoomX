package com.roomx.infrastructure.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.Room;
import com.roomx.domain.repository.RoomRepository;
import com.roomx.infrastructure.persistence.mapper.PlaceEntityMapper;
import com.roomx.infrastructure.persistence.mapper.RoomEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaRoomEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RoomEntityRepository implements RoomRepository {
    private final PlaceEntityMapper placeEntityMapper;
    private final JpaRoomEntityRepository jpaRoomEntityRepository;
    private final RoomEntityMapper roomEntityMapper;

    @Override
    public Optional<Room> findById(String id) {
        return jpaRoomEntityRepository
                .findById(UUID.fromString(id))
                .map(roomEntityMapper::toDomain);
    }

    @Override
    public Room save(Room room) {
        var roomEntity = roomEntityMapper.toEntity(room);
        var savedRoomEntity = jpaRoomEntityRepository.save(roomEntity);
        return roomEntityMapper.toDomain(savedRoomEntity);
    }

    @Override
    public boolean checkExistsRoomCode(String roomCode) {
        return jpaRoomEntityRepository.existsByRoomCode(roomCode);
    }

    @Override
    public List<Room> findAll() {
        return jpaRoomEntityRepository
                .findAll().stream()
                .map(roomEntityMapper::toDomain).toList();
    }

    @Override
    public List<Room> findAllByStatus(String status) {
        return jpaRoomEntityRepository.findAllByStatus(status)
                .stream().map(roomEntityMapper::toDomain).toList();
    }

    @Override
    public List<Room> findAllByCapacityGreaterThanOrEqualAndStatus(int requiredCapacity, String status) {
        return jpaRoomEntityRepository
                .findAllByStatusIsAndRoomClassCapacityGreaterThanEqual(status, requiredCapacity)
                .stream().map(roomEntityMapper::toDomain).toList();
    }

    @Override
    public List<Room> findAllByBranchIdAndStatus(String branchId, String status) {
        return jpaRoomEntityRepository
                .findAllByStatusBranchId(status, UUID.fromString(branchId))
                .stream().map(roomEntityMapper::toDomain).toList();
    }

    @Override
    public List<Room> findAllByBranchIdAndStatusMinimum(String branchId, String status) {
        return jpaRoomEntityRepository
                .findAllByStatusBranchId(status, UUID.fromString(branchId))
                .stream().map(roomEntity -> Room.builder()
                                .id(roomEntity.getId())
                                .roomCode(roomEntity.getRoomCode())
                                .status(roomEntity.getStatus())
                                .description(roomEntity.getDescription())
                                .place(placeEntityMapper.toDomain(roomEntity.getPlace()))
                                .build()
                        ).toList();
    }

    @Override
    public List<Room> findAllByPlaceIdAndStatus(String placeId, String status) {
        return jpaRoomEntityRepository
                .findAllByPlaceIdAndStatus(UUID.fromString(placeId), status)
                .stream().map(roomEntityMapper::toDomain).toList();
    }

    @Override
    public Optional<BigDecimal> findPriceByIdAndValidTimestamp(String roomId, Instant timestamp) {
        return jpaRoomEntityRepository
                .findPriceByIdAndValidTimestamp(UUID.fromString(roomId), timestamp);
    }

}
