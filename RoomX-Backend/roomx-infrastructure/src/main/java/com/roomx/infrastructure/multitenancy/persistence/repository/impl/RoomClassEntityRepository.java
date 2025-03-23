package com.roomx.infrastructure.multitenancy.persistence.repository.impl;


import com.roomx.domain.model.aggrerate.RoomClass;
import com.roomx.domain.repository.RoomClassRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.RoomClassEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaRoomClassEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RoomClassEntityRepository implements RoomClassRepository {
    private final JpaRoomClassEntityRepository jpaRoomClassEntityRepository;
    private final RoomClassEntityMapper roomClassEntityMapper;

    @Override
    public Optional<RoomClass> findById(String id) {
        return jpaRoomClassEntityRepository
                .findById(UUID.fromString(id))
                .map(roomClassEntityMapper::toDomain);
    }

    @Override
    public RoomClass save(RoomClass roomClass) {
        var roomClassEntity = roomClassEntityMapper.toEntity(roomClass);
        var savedRoomClassEntity = jpaRoomClassEntityRepository.save(roomClassEntity);
        return roomClassEntityMapper.toDomain(savedRoomClassEntity);
    }

    @Override
    public boolean checkExistsRoomClassCode(String roomClassCode) {
        return jpaRoomClassEntityRepository.existsByRoomClassCode(roomClassCode);
    }

    @Override
    public Optional<RoomClass> findByRoomClassCode(String roomClassCode) {
        return jpaRoomClassEntityRepository
                .findByRoomClassCode(roomClassCode)
                .map(roomClassEntityMapper::toDomain);
    }

    @Override
    public Optional<RoomClass> findByIdAndStatus(String roomClassId, String status) {
        return jpaRoomClassEntityRepository
                .findByIdAndStatus(UUID.fromString(roomClassId), status)
                .map(roomClassEntityMapper::toDomain);
    }

}
