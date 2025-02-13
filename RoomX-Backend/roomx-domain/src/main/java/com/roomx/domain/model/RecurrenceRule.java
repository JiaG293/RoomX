package com.roomx.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecurrenceRule {
    private String frequency; // Daily, Weekly, Monthly, etc.
    private String interval;
    private List<String> daysOfWeek; // For weekly recurrence
    private Integer dayOfMonth; // For monthly recurrence
    private Integer monthOfYear;
    private OffsetDateTime endDate; // Optional
    private Integer occurrences; // Optional
}
