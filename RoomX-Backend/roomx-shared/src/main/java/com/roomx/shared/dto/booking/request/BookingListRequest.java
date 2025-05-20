package com.roomx.shared.dto.booking.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingListRequest {
    private Integer month;
    private Integer year;
    private String status;
    private Boolean isAdmin = false;

}
