package com.roomx.shared.dto.booking.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferBookingSlot {
    private String id;
    private String bookingRequestId;
    private String roomId;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
}
