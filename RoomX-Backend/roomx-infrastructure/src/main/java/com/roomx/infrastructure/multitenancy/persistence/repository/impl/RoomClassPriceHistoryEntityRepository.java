package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.entity.RoomClassPriceHistory;
import com.roomx.domain.repository.RoomClassPriceHistoryRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.RoomClassPriceHistoryEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaRoomClassPriceHistoryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RoomClassPriceHistoryEntityRepository implements RoomClassPriceHistoryRepository {
    private final JpaRoomClassPriceHistoryEntityRepository jpaRoomClassPriceHistoryEntityRepository;
    private final RoomClassPriceHistoryEntityMapper roomClassPriceHistoryEntityMapper;

    @Override
    public Optional<RoomClassPriceHistory> findById(String id) {
        return jpaRoomClassPriceHistoryEntityRepository.findById(UUID.fromString(id))
                .map(roomClassPriceHistoryEntityMapper::toDomain);
    }

    @Override
    public RoomClassPriceHistory save(RoomClassPriceHistory roomClassPriceHistory) {
        var roomClassPriceHistoryEntity = roomClassPriceHistoryEntityMapper.toEntity(roomClassPriceHistory);
        var savedRoomClassPriceHistoryEntity = jpaRoomClassPriceHistoryEntityRepository.save(roomClassPriceHistoryEntity);
        return roomClassPriceHistoryEntityMapper.toDomain(savedRoomClassPriceHistoryEntity);
    }

    @Override
    public Optional<RoomClassPriceHistory> findLatestValidFrom(String roomClassId) {
        return null;
    }
}
