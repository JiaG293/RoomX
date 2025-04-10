package com.roomx.shared.dto.booking.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateRequestExceptionResponse {
    private UUID roomId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
