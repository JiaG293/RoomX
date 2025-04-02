package com.roomx.infrastructure.persistence.dto;

import com.roomx.shared.base.BaseFilter;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ServiceFilter extends BaseFilter {
    private BigDecimal fromPrice;
    private BigDecimal toPrice;
    private Instant validPriceFrom;
    private Instant validPriceEnd;

    private String status;
}
