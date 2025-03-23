package com.roomx.domain.model.aggrerate;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Booking {
    private UUID id;
    private String bookingCode;
    private BookingRequest bookingRequest;
    private Room room;
    private Room previousRoom;
    private LocalTime meetingStart;
    private LocalTime meetingEnd;
    private LocalDate meetingDate;
    private int count;
    private BigDecimal totalPrice;
    private Instant createdAt;
    private Instant updatedAt;
}
