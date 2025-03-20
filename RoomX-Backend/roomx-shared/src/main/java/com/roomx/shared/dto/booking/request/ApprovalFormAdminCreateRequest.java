package com.roomx.shared.dto.booking.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalFormAdminCreateRequest {
    private String bookingRequestId;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String status;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String note;
}
