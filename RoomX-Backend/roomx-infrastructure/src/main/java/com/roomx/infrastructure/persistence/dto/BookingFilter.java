package com.roomx.infrastructure.persistence.dto;


import com.roomx.shared.base.BaseFilter;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BookingFilter extends BaseFilter {
    private String keyword;
    private String searchBy;
    private String roomId;
    private LocalTime fromTime;
    private LocalTime toTime;
    private LocalDate fromMeetingDate;
    private LocalDate toMeetingDate;
    private BigDecimal fromTotalPrice;
    private BigDecimal toTotalPrice;
    private String status;
    private Instant fromTimestamp;
    private Instant toTimestamp;
}
