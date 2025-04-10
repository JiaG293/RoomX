package com.roomx.domain.model.entity;

import com.roomx.shared.enums.DeleteStatusType;
import com.roomx.shared.enums.PlaceType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class DateRequestException {
    private UUID id;
    private UUID bookingRequestId;
    private UUID roomId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public DateRequestException(LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
    }
}
