package com.roomx.shared.dto.booking.response;

import com.roomx.shared.dto.resource.response.RoomResponse;
import com.roomx.shared.dto.user.response.UserResponse;
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
