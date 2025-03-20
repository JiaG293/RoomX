package com.roomx.shared.dto.booking.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentBookingRequest {
    private String equipmentId;
    private int quantity;
}
