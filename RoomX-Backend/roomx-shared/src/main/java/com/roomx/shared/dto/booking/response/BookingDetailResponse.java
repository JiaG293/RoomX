package com.roomx.shared.dto.booking.response;

import com.roomx.shared.dto.resource.response.EquipmentResponse;
import com.roomx.shared.dto.resource.response.RoomResponse;
import com.roomx.shared.dto.resource.response.ServiceResponse;
import com.roomx.shared.dto.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDetailResponse {
    private String id;
    private String bookingCode;
    private String title;
    private String description;
    private LocalTime meetingStart;
    private LocalTime meetingEnd;
    private LocalDate meetingDate;
    private int count;
    private BigDecimal totalPrice;
    private String status;
    private RoomResponse room;
    private RoomResponse previousRoom;

    private String bookingRequestId;
    private UserResponse requester;

    private List<ServiceResponse> services;
    private List<EquipmentResponse> equipments;
    private List<BookingParticipantResponse> participants;
}
