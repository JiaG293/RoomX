package com.roomx.shared.dto.resource.request;

import java.math.BigDecimal;
import java.time.Instant;

public record EquipmentFilterRequest(
        String keyword,
        String searchBy,

        String brand,

        BigDecimal fromPrice,
        BigDecimal toPrice,

        Instant validPriceFrom,
        Instant validPriceEnd,

        String status
) {
}
