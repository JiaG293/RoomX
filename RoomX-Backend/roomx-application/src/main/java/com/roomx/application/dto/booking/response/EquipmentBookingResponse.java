package com.roomx.application.dto.booking.response;

import com.roomx.application.dto.resource.response.EquipmentResponse;
import com.roomx.application.dto.resource.response.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentBookingResponse {
    private String id;
    private EquipmentResponse equipment;
    private BigDecimal unitPrice;
    private int quantity;
}
