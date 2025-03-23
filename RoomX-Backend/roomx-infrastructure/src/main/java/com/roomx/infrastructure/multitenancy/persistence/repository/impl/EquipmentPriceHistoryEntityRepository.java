package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.entity.EquipmentPriceHistory;
import com.roomx.domain.repository.EquipmentPriceHistoryRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.EquipmentPriceHistoryEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaEquipmentPriceHistoryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EquipmentPriceHistoryEntityRepository implements EquipmentPriceHistoryRepository {
    private final EquipmentPriceHistoryEntityMapper equipmentPriceHistoryEntityMapper;
    private final JpaEquipmentPriceHistoryEntityRepository jpaEquipmentPriceHistoryEntityRepository;

    @Override
    public Optional<EquipmentPriceHistory> findById(String id) {
        return jpaEquipmentPriceHistoryEntityRepository
                .findById(UUID.fromString(id))
                .map(equipmentPriceHistoryEntityMapper::toDomain);
    }

    @Override
    public EquipmentPriceHistory save(EquipmentPriceHistory equipmentPriceHistory) {
        var equipmentPriceHistoryEntity = equipmentPriceHistoryEntityMapper.toEntity(equipmentPriceHistory);
        var savedEquipmentPriceHistoryEntity = jpaEquipmentPriceHistoryEntityRepository.save(equipmentPriceHistoryEntity);
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
}
