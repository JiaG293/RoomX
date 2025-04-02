package com.roomx.shared.dto.booking.request;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

public record BookingFilterRequest(
        String keyword,
        String searchBy,
        String roomId,
        LocalTime fromTime,
        LocalTime toTime,
        LocalDate fromMeetingDate,
        LocalDate toMeetingDate,
        BigDecimal fromTotalPrice,
        BigDecimal toTotalPrice,
        String status,
        Instant fromTimestamp,
        Instant toTimestamp
) {
}
