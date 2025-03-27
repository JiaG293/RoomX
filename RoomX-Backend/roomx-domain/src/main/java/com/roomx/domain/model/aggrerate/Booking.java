package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.entity.BookingParticipant;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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

    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();

    private List<BookingParticipant> participants;


}
