package com.roomx.application.dto.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentRoomClassDetailResponse {
    private RoomClassResponse roomClass;
    private EquipmentResponse equipment;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal totalPrice;
}
