package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.model.entity.EquipmentPriceHistory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface EquipmentPriceHistoryRepository {
    Optional<EquipmentPriceHistory> findById(String id);

    EquipmentPriceHistory save(EquipmentPriceHistory equipmentPriceHistory);

    boolean checkExistsEquipmentPriceHistoryId(String equipmentPriceHistoryId);

    Optional<EquipmentPriceHistory> findByEquipmentIdAndEffectiveDate(String equipmentId, String effectiveDate);

    boolean existsByEquipmentIdAndTimeRange(String equipmentId, Instant requestFrom, Instant requestEnd);

    List<EquipmentPriceHistory> findAllOverlappingPriceHistory(
            String equipmentId,
            Instant validFrom,
            Instant validEnd);

    Optional<EquipmentPriceHistory> findLatestValidFrom(String equipmentId);
}
