package com.roomx.shared.dto.booking.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private int capacity;
    @NotNull(message = "valid.booking_request.user_create.not_null.recurrenceType")
    private String recurrenceType;
    @NotNull(message = "valid.booking_request.user_create.not_null.start_date")
    @FutureOrPresent(message = "valid.booking_request.user_create.future_or_present.start_date")
    private LocalDate startDate;
    @FutureOrPresent(message = "valid.booking_request.user_create.future_or_present.end_date")
    @NotNull(message = "valid.booking_request.user_create.not_null.end_date")
    private LocalDate endDate;
    @NotNull(message = "valid.booking_request.user_create.not_null.end_time")
    private LocalTime endTime;
    @NotNull(message = "valid.booking_request.user_create.not_null.start_time")
    private LocalTime startTime;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String title;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String description;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String daysOfWeek;
    private int recurrenceInterval;
//    @NotNull(message = "valid.booking_request.user_create.not_null.branchId")
    private String branchId;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String roomId;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<ServiceBookingRequest> services = new ArrayList<>();
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<EquipmentBookingRequest> equipments = new ArrayList<>();

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @Size(min = 2, message = "valid.booking_request.user_create.size.participants")
    private List<String> participants = new ArrayList<>();

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<DateRequestExceptionRequest> dateRequestExceptions;
}
