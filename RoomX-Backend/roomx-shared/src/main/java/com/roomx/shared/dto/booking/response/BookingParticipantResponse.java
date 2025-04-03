package com.roomx.shared.dto.booking.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingParticipantResponse {
    private String participantId;
    private String userCode;
    private String email;
}
