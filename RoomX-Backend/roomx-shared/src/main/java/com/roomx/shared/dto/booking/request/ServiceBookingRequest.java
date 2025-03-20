package com.roomx.shared.dto.booking.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceBookingRequest {
    private String serviceId;
    private int quantity;
}
