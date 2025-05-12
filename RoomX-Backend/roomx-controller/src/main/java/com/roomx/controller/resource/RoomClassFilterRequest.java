package com.roomx.controller.resource;

import java.math.BigDecimal;
import java.time.Instant;

public record RoomClassFilterRequest (
        String keyword,
        String searchBy,

        String groupType,
        String branchId,

        BigDecimal startPrice,
        BigDecimal endPrice,

        Instant startPriceHistory,
        Instant endPriceHistory,

        Integer capacity,

        String status
){
}

