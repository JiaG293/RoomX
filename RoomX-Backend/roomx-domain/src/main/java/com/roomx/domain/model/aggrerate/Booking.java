package com.roomx.domain.model.aggrerate;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
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
    private Instant meetingStart;
    private Instant meetingEnd;
    private int count;
    private BigDecimal totalPrice;
    private Instant createdAt;
    private Instant updatedAt;
}
