package com.roomx.shared.dto.booking.response;

import com.roomx.shared.dto.resource.response.*;
import com.roomx.shared.dto.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDetailResponse {
    private String id;
    private int priority;
    private int capacity;

    private String title;
    private String description;
    private String recurrenceType;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String daysOfWeek;
    private Integer recurrenceInterval;
    private PlaceResponse branch;
    private RoomResponse room;
    private String approvalStatus;
    private Instant createdAt;
    private Instant updatedAt;

    private UserResponse requester;
    private List<ServiceRequestResponse> services;
    private List<EquipmentRequestResponse> equipments;
    private List<String> participants;
}
