package com.roomx.shared.dto.resource.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomClassDetailResponse {
    private BigDecimal equipmentsTotalPrice;
    private BigDecimal servicesTotalPrice;
    private BigDecimal totalPrice;

    private RoomClassResponse roomClass;

    private List<EquipmentRoomClassDetailResponse> equipments;
    private List<ServiceRoomClassDetailResponse> services;


}
