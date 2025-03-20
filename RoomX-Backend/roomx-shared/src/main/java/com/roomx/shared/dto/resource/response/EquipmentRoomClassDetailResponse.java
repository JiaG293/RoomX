package com.roomx.shared.dto.resource.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RoomClassResponse roomClass;
    private EquipmentResponse equipment;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal totalPrice;
}
