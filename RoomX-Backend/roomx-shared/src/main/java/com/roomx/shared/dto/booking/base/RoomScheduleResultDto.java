package com.roomx.shared.dto.booking.base;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomScheduleResultDto{
    private LocalDate date;
    private boolean hasConflict;
    private String optimalRoomId;
    private List<String> alternativeRoomId;
}