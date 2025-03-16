package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.model.enums.ApprovalStatusType;
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
    private User approver;
    private BookingRequest bookingRequest;

    @Builder.Default
    private String status = ApprovalStatusType.PENDING.toString();
    private String note;
    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();



}
