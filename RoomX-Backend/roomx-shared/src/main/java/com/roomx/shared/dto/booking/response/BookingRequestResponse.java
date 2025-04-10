package com.roomx.shared.dto.booking.response;


import com.roomx.shared.dto.booking.request.DateRequestExceptionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestResponse {
    private String id;
    private int priority;
    private int capacity;
    private List<ServiceRequestResponse> services;
    private List<EquipmentRequestResponse> equipments;
    private String title;
    private String description;
    private String recurrenceType;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String daysOfWeek;
    private Integer recurrenceInterval;
    private String branchId;
    private String roomId;
    private String approvalStatus;
    private Instant createdAt;
    private Instant updatedAt;
    private List<DateRequestExceptionResponse> dateRequestExceptions;

}
