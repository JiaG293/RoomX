package com.roomx.shared.dto.booking.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingResponse {
    private String id;
    private String bookingCode;
    private String bookingRequestId;
    private String roomId;
    private String previousRoomId;
    private LocalTime meetingStart;
    private LocalTime meetingEnd;
    private LocalDate meetingDate;
    private int count;
    private BigDecimal totalPrice;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;

}
