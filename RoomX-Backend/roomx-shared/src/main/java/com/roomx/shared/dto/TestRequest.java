package com.roomx.shared.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestRequest {
    private int capacity;
    private String branchId;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String roomId;
    private String recurrenceType;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<String> participants;
    private String daysOfWeek;
    private int recurrenceInterval;
}
