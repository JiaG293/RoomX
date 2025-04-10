package com.roomx.infrastructure.persistence.model.projection;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface BookingRequestFlatProjection {
    UUID bookingRequestId();
    String title();
    String description();
    String recurrenceType();
    LocalDate startDate();
    LocalDate endDate();
    LocalTime startTime();
    LocalTime endTime();
    String daysOfWeek();
    Integer recurrenceInterval();
    Integer capacity();
    Integer priority();
    String approvalStatus();
    Instant createdAt();
    Instant updatedAt();

    UUID roomId();
    String roomName();
    String roomCode();
    UUID branchId();
    String branchName();
    String branchCode();
    UUID buildingId();
    String buildingName();
    String buildingCode();
    UUID floorId();
    String floorName();
    String floorCode();
}
