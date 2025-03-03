package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.BookingRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Recurrence {
    private UUID id;
    private BookingRequest bookingRequest;
    private String recurrenceType;
    private Short repeatCount;
    private LocalDate startDate;
    private LocalDate endDate;




}
