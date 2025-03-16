package com.roomx.application.dto.booking.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecurrenceAdminCreateRequest {
    private String recurrenceType;
    private int repeatCount;
    private LocalDate startDate;
    private LocalDate endDate;
}
