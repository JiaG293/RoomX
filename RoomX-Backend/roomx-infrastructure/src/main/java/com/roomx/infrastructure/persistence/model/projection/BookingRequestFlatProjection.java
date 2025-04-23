package com.roomx.infrastructure.persistence.model.projection;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface BookingRequestFlatProjection {
    UUID getBookingRequestId();
    String getTitle();
    String getDescription();
    String getRecurrenceType();
    LocalDate getStartDate();
    LocalDate getEndDate();
    LocalTime getStartTime();
    LocalTime getEndTime();
    String getDaysOfWeek();
    Integer getRecurrenceInterval();
    Integer getCapacity();
    Integer getPriority();
    String getApprovalStatus();
    Instant getCreatedAt();
    Instant getUpdatedAt();

    UUID getRoomId();
    String getRoomName();
    String getRoomCode();
    UUID getBranchId();
    String getBranchName();
    String getBranchCode();
    UUID getBuildingId();
    String getBuildingName();
    String getBuildingCode();
    UUID getFloorId();
    String getFloorName();
    String getFloorCode();
}
