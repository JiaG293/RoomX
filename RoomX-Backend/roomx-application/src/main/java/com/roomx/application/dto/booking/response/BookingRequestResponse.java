package com.roomx.application.dto.booking.response;

import com.roomx.application.dto.resource.response.EquipmentResponse;
import com.roomx.application.dto.resource.response.RoomResponse;
import com.roomx.application.dto.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestResponse {
    private String id;
    private RoomResponse room;
    private String approvalStatus;
    private UserResponse userResponse;
    private int priority;
    private List<ServiceBookingReponse> services;
    private List<EquipmentBookingResponse> equipment;
    private List<RecurrenceResponse> recurrences;

}
