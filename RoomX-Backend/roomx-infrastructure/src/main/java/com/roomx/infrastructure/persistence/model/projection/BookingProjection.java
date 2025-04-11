package com.roomx.infrastructure.persistence.model.projection;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface BookingProjection {
    UUID getId();
    String getTitle();
    String getDescription();
    String getBookingCode();
    UUID getRoomId();

    String getRoomCode();
    String getRoomName();

    UUID getBranchId();
    String getBranchCode();
    String getBranchName();

    UUID getBuildingId();
    String getBuildingCode();
    String getBuildingName();

    UUID getFloorId();
    String getFloorCode();
    String getFloorName();

    UUID getRoomPreviousId();

    LocalTime getMeetingStart();
    LocalTime getMeetingEnd();
    LocalDate getMeetingDate();
    Integer getCount();
    String getStatus();
    Instant getCreatedAt();
    Instant getUpdatedAt();
}
