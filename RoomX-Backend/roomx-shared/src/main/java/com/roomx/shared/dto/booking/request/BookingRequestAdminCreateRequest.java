package com.roomx.shared.dto.booking.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.roomx.shared.enums.ApprovalStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestAdminCreateRequest {
    private String roomId;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private int priority = 0;
    private String recurrenceType;
    private String description;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String daysOfWeek;
    private int interval;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<ServiceBookingRequest> services;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<EquipmentBookingRequest> equipments;

    private List<String> participants;
}
