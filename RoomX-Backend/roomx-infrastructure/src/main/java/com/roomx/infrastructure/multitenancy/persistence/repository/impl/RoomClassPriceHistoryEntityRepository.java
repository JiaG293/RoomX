package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.entity.RoomClassPriceHistory;
import com.roomx.domain.repository.RoomClassPriceHistoryRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.RoomClassPriceHistoryEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaRoomClassPriceHistoryEntityRepository;
import com.roomx.shared.dto.resource.base.RoomClassPriceCalculateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
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
        return jpaRoomClassPriceHistoryEntityRepository
                .findLatestValidFrom(UUID.fromString(roomClassId))
                .map(roomClassPriceHistoryEntityMapper::toDomain);
    }

    @Override
    public RoomClassPriceCalculateDto calculateTotalPrice(String roomClassId) {
        return jpaRoomClassPriceHistoryEntityRepository
                .calculateTotalPrice(UUID.fromString(roomClassId));
    }

    @Override
    public Optional<BigDecimal> findPriceByRoomClassIdValidTime(String roomClassId, Instant timestamp) {
        return jpaRoomClassPriceHistoryEntityRepository
                .findPriceByRoomClassIdValidTime(UUID.fromString(roomClassId), timestamp.toString());
    }
}
