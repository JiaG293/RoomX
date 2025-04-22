package com.roomx.infrastructure.persistence.model.projection;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;



public interface BookingApprovalRequestProjection {
    UUID getBookingRequestId();
    String getTitle();
    String getDescription();
    String getRecurrenceType();
    LocalDate getStartDate();
    LocalDate getEndDate();
    LocalTime getStartTime();
    LocalTime getEndTime();
    String getDaysOfWeek();
    Short getRecurrenceInterval();
    Integer getCapacity();
    Short getPriority();
    UUID getBranchId();
    UUID getRoomId();
    UUID getRequester();


    UUID getApprover();
    String getStatus();
    Instant getCreatedAt();
    Instant getUpdatedAt();



}
