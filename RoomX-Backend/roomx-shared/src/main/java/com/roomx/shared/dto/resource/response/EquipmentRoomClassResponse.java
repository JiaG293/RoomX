package com.roomx.shared.dto.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentRoomClassResponse {
    private String roomClassId;
    private String equipmentId;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal totalPrice;
}
