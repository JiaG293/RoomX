package com.roomx.infrastructure.multitenancy.persistence.dto;



import com.roomx.shared.base.BaseFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EquipmentFilter extends BaseFilter {
    private String brand;

    private BigDecimal fromPrice;
    private BigDecimal toPrice;

    private Instant validPriceFrom;
    private Instant validPriceEnd;

    private String status;
}
