package com.roomx.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private String timezone;
    private RecurrenceRule recurrenceRule; // Optional
}
