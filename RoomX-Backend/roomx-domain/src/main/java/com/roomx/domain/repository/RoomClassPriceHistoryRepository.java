package com.roomx.domain.repository;

import com.roomx.domain.model.entity.RoomClassPriceHistory;
import com.roomx.shared.dto.resource.base.RoomClassPriceCalculateDto;


import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

public interface RoomClassPriceHistoryRepository {
    Optional<RoomClassPriceHistory> findById(String id);

    RoomClassPriceHistory save(RoomClassPriceHistory roomClassPriceHistory);

    Optional<RoomClassPriceHistory> findLatestValidFrom(String roomClassId);

    RoomClassPriceCalculateDto calculateTotalPrice(String roomClassId);

    Optional<BigDecimal> findPriceByRoomClassIdValidTime(String roomClassId, Instant timestamp);
}
