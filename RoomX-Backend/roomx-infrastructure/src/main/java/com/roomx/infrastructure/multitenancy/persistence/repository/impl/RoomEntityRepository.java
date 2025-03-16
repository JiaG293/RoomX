package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.Room;
import com.roomx.domain.repository.RoomRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.RoomEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaRoomEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RoomEntityRepository implements RoomRepository {
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
}
