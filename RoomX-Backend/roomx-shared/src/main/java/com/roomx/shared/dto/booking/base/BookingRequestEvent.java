package com.roomx.shared.dto.booking.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestEvent {
    private LocalDateTime timestamp;
    private String type;
    private UUID bookingRequestId;
}
