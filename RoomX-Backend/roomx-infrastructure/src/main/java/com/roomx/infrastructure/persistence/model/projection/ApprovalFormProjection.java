package com.roomx.infrastructure.persistence.model.projection;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface ApprovalFormProjection {
    UUID getApprovalFormId();
    String getStatus();
    Instant getUpdatedAt();

    UUID getBookingRequestId();
    UUID getRoomId();
    UUID getBranchId();

    UUID getDateRequestExceptionId();
    LocalDate getExceptionDate();
    LocalTime getExceptionStartTime();
    LocalTime getExceptionEndTime();
}
