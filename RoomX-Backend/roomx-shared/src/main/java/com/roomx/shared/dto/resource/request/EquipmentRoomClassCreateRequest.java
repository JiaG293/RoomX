package com.roomx.shared.dto.resource.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentRoomClassCreateRequest {
    private String equipmentId;
    private int quantity;
}
