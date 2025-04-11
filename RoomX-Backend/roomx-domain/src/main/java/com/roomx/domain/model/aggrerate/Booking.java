package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.entity.BookingParticipant;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Booking {
    private UUID id;
    private String bookingCode;
    private String title;
    private String description;
    private BookingRequest bookingRequest;
    private Room room;
    private Room previousRoom;
    private LocalTime meetingStart;
    private LocalTime meetingEnd;
    private LocalDate meetingDate;
    private int count;
    private BigDecimal totalPrice;
    private String status;

    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();

    @Builder.Default
    private List<BookingParticipant> participants = new ArrayList<>();



    public String getPlaceDetail(){
        return  " - " + room.getRoomCode();
    }

}
