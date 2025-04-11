package com.roomx.infrastructure.persistence.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDto {
    public UUID id;
    public String title;
    public String description;
    public String bookingCode;

    public UUID roomId;
    public String roomCode;
    public String roomName;

    public UUID branchId;
    public String branchCode;
    public String branchName;

    public UUID buildingId;
    public String buildingCode;
    public String buildingName;

    public UUID floorId;
    public String floorCode;
    public String floorName;

    public String roomPreviousId;

    public LocalTime meetingStart;
    public LocalTime meetingEnd;
    public LocalDate meetingDate;
    public int count;
    public String status;
    public Instant createdAt;
    public Instant updatedAt;
}
