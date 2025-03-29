package com.roomx.shared.dto.booking.base;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomScheduleResultDto{
    private LocalDate date;
    private boolean hasConflict;
    private String optimalRoomId;
    private SuggestedTimeSlotDto suggestedTimeSlots;
}