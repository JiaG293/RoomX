package com.roomx.shared.event;

import com.roomx.shared.enums.EmailTemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproveBookingEvent {
    private String id;
    private String bookingRequestId;
    private String ownerId;
    private List<String> participants;
    private String tenantId;

}
