package com.roomx.shared.dto.booking.response;


import com.roomx.shared.dto.resource.response.PlaceNameResponse;
import com.roomx.shared.dto.resource.response.PlaceResponse;
import com.roomx.shared.dto.resource.response.RoomDetailResponse;
import com.roomx.shared.dto.resource.response.RoomResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingMiniumResponse {
    private UUID id;
    private String title;
    private String description;
    private String bookingCode;
    private RoomBookingResponse room;
    private PlaceNameResponse branch;
    private PlaceNameResponse building;
    private PlaceNameResponse floor;
    private UUID previousRoom;
    private LocalTime meetingStart;
    private LocalTime meetingEnd;
    private LocalDate meetingDate;
    private int count;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;

}

