package com.roomx.shared.dto.booking.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntervalEvent {
    private LocalTime start;
    private LocalTime end;
}
