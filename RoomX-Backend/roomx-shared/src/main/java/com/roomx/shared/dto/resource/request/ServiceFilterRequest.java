package com.roomx.shared.dto.resource.request;

import java.math.BigDecimal;
import java.time.Instant;

public record ServiceFilterRequest(
        String keyword,
        String searchBy,


        BigDecimal fromPrice,
        BigDecimal toPrice,

        Instant validPriceFrom,
        Instant validPriceEnd,

        String status
) {
}
