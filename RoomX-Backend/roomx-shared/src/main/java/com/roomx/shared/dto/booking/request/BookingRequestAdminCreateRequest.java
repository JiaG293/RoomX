package com.roomx.shared.dto.booking.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.roomx.shared.enums.ApprovalStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestAdminCreateRequest {
    private String roomId;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String approvalStatus = ApprovalStatusType.APPROVED.toString();
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private int priority = 0;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<RecurrenceAdminCreateRequest> recurrences = new ArrayList<>();
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<ServiceBookingRequest> serviceRequests = new ArrayList<>();
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<EquipmentBookingRequest> equipmentRequests = new ArrayList<>();
}
