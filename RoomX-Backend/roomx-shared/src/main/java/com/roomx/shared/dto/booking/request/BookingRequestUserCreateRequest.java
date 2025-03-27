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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestUserCreateRequest {
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private int priority = 0;
    private int capacity;
    private String recurrenceType;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String daysOfWeek;
    private int recurrenceInterval;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<ServiceBookingRequest> services = new ArrayList<>();
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<EquipmentBookingRequest> equipments = new ArrayList<>();

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<String> participants = new ArrayList<>();
}
