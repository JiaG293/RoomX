package com.roomx.application.dto.resource.request;

import com.roomx.domain.model.aggrerate.RoomClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentRoomClassCreateRequest {
    private String equipmentId;
    private int quantity;
}
