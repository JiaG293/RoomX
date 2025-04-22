package com.roomx.shared.dto.booking.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalFormRejectRequest {
    @NotNull(message = "valid.approval_form.not_null")
    private String note;
}
