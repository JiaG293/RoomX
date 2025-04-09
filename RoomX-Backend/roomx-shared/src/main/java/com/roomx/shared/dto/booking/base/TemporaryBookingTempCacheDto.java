package com.roomx.shared.dto.booking.base;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TemporaryBookingTempCacheDto{
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private UUID roomId;
    private String userId;
    private List<String> participants;
}
