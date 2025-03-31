package com.roomx.shared.dto.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceHistoryResponse {
    private String id;
    private BigDecimal unitPrice;
    private Instant validFrom;
    private Instant validEnd;
}
