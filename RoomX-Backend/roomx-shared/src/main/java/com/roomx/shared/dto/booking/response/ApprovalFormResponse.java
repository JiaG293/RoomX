package com.roomx.shared.dto.booking.response;

import com.roomx.shared.enums.ApprovalStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalFormResponse {
    private UUID id;
    private UUID approver;
    private UUID bookingRequestId;

    private String status;
    private String note;
    private Instant createdAt;
    private Instant updatedAt;
}
