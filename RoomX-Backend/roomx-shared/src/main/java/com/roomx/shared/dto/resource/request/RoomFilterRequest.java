package com.roomx.shared.dto.resource.request;

import java.math.BigDecimal;
import java.time.Instant;

public record RoomFilterRequest(
        String keyword,
        String searchBy,

        BigDecimal startPrice,
        BigDecimal endPrice,

        String branchId,
        String buildingId,
        String floorId,
        Integer capacity,

        String status

) {
}
