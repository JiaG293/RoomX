package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.BookingRequest;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Recurrence {
    private UUID id;
    private BookingRequest bookingRequest;
    private String recurrenceType;
    private Short repeatCount;
    private LocalDate startDate;
    private LocalDate endDate;




}
