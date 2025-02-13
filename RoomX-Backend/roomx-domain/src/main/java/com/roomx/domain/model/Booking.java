package com.roomx.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private UUID bookingId;
    private Meeting meeting;
    private UUID bookedBy; // EmployeeId
    private OffsetDateTime bookingDate;
    private String status; // E.g., PENDING, CONFIRMED, CANCELLED
    private String notes;
}
