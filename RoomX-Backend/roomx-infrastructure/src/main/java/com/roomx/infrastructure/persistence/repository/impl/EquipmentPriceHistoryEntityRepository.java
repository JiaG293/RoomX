package com.roomx.infrastructure.persistence.repository.impl;

import com.roomx.domain.model.entity.EquipmentPriceHistory;
import com.roomx.domain.repository.EquipmentPriceHistoryRepository;
import com.roomx.infrastructure.persistence.mapper.EquipmentEntityMapper;
import com.roomx.infrastructure.persistence.mapper.EquipmentPriceHistoryEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaEquipmentPriceHistoryEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EquipmentPriceHistoryEntityRepository implements EquipmentPriceHistoryRepository {
    private final EquipmentPriceHistoryEntityMapper equipmentPriceHistoryEntityMapper;
    private final JpaEquipmentPriceHistoryEntityRepository jpaEquipmentPriceHistoryEntityRepository;
    private final EquipmentEntityMapper equipmentEntityMapper;

    @Override
    public Optional<EquipmentPriceHistory> findById(String id) {
        return jpaEquipmentPriceHistoryEntityRepository
                .findById(UUID.fromString(id))
                .map(equipmentPriceHistoryEntityMapper::toDomain);
    }

    @Override
    public EquipmentPriceHistory save(EquipmentPriceHistory equipmentPriceHistory) {
        log.info("gia tri: {}", equipmentPriceHistory.isActive());
        var equipmentPriceHistoryEntity = equipmentPriceHistoryEntityMapper.toEntity(equipmentPriceHistory);
        log.info("gia tri 2: {}", equipmentPriceHistoryEntity.isActive());
        var savedEquipmentPriceHistoryEntity = jpaEquipmentPriceHistoryEntityRepository.save(equipmentPriceHistoryEntity);
        log.info("gia tri sau: {}", savedEquipmentPriceHistoryEntity.isActive());
        return equipmentPriceHistoryEntityMapper.toDomain(savedEquipmentPriceHistoryEntity);
    }

    @Override
    public boolean checkExistsEquipmentPriceHistoryId(String equipmentPriceHistoryId) {
        return jpaEquipmentPriceHistoryEntityRepository
                .existsById(UUID.fromString(equipmentPriceHistoryId));
    }

    @Override
    public Optional<EquipmentPriceHistory> findByEquipmentIdAndEffectiveDate(String equipmentId, String effectiveDate) {
        return Optional.empty();
    }

    @Override
    public boolean existsByEquipmentIdAndTimeRange(String equipmentId, Instant requestFrom, Instant requestEnd) {
        return jpaEquipmentPriceHistoryEntityRepository.existsByEquipmentAndTimeRange(UUID.fromString(equipmentId), requestFrom, requestEnd);
    }

    @Override
    public List<EquipmentPriceHistory> findAllOverlappingPriceHistory(String equipmentId, Instant validFrom, Instant validEnd) {
        return jpaEquipmentPriceHistoryEntityRepository.findAllOverlappingPriceHistory(
                        UUID.fromString(equipmentId),
                        validFrom,
                        validEnd)
                .stream().map(equipmentPriceHistoryEntityMapper::toDomain).toList();
    }

    @Override
    public Optional<EquipmentPriceHistory> findLatestValidFrom(String equipmentId) {
        return jpaEquipmentPriceHistoryEntityRepository
                .findLatestValidFrom(UUID.fromString(equipmentId))
                .map(equipmentPriceHistoryEntityMapper::toDomain);
    }
}
