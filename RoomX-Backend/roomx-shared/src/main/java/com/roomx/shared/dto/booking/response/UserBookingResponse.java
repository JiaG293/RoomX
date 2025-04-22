package com.roomx.shared.dto.booking.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBookingResponse {
    private String id;
    private String userCode;
    private String email;
    private String firstName;
    private String lastName;
}
