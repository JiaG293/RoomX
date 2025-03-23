package com.roomx.domain.repository;

import com.roomx.domain.model.entity.EquipmentPriceHistory;

import java.util.Optional;

public interface EquipmentPriceHistoryRepository {
    Optional<EquipmentPriceHistory> findById(String id);
    EquipmentPriceHistory save(EquipmentPriceHistory equipmentPriceHistory);
    boolean checkExistsEquipmentPriceHistoryId(String equipmentPriceHistoryId);
    Optional<EquipmentPriceHistory> findByEquipmentIdAndEffectiveDate(String equipmentId, String effectiveDate);

}
