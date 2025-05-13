package com.roomx.infrastructure.persistence.dto;

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
public class RoomClassFilter extends BaseFilter {
    private String keyword;
    private String searchBy;
    private Integer capacity;
    private String status;
    private BigDecimal startPrice;
    private BigDecimal endPrice;
}
