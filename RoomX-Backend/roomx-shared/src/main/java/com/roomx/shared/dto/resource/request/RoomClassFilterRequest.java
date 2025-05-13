package com.roomx.shared.dto.resource.request;

import java.math.BigDecimal;
import java.time.Instant;

public record RoomClassFilterRequest (
        String keyword,
        String searchBy,

        BigDecimal startPrice,
        BigDecimal endPrice,

        Integer capacity,

        String status
){
}
