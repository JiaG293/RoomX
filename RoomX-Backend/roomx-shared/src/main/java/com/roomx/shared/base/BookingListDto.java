package com.roomx.shared.base;


import com.roomx.shared.dto.booking.response.RoomBookingResponse;
import com.roomx.shared.dto.resource.response.PlaceNameResponse;
import com.roomx.shared.dto.resource.response.PlaceResponse;
import com.roomx.shared.dto.resource.response.RoomClassResponse;
import com.roomx.shared.dto.resource.response.RoomDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingListDto {
    private String id;
    private String title;
    private String description;
    private String bookingCode;
    private String roomId;
    private String roomCode;
    private String roomName;

    private String branchId;
    private String branchCode;
    private String branchName;

    private String buildingId;
    private String buildingCode;
    private String buildingName;

    private String floorId;
    private String floorCode;
    private String floorName;

    private String roomPreviousId;

    private LocalTime meetingStart;
    private LocalTime meetingEnd;
    private LocalDate meetingDate;
    private int count;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
}
