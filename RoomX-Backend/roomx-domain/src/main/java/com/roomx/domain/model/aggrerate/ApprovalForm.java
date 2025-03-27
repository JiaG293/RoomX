package com.roomx.domain.model.aggrerate;

import com.roomx.shared.enums.ApprovalStatusType;
import lombok.*;

import java.time.Instant;
import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class ApprovalForm {
    private UUID id;
    private UUID approver;
    private BookingRequest bookingRequest;

    @Builder.Default
    private String status = ApprovalStatusType.PENDING.toString();
    private String note;
    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();



}
