package com.roomx.application.dto.resource.response;

import com.roomx.domain.model.entity.EquipmentRoomClass;
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
    private RoomClassResponse roomClass;
    private List<EquipmentRoomClassDetailResponse> equipments;
    private List<ServiceRoomClassDetailResponse> services;
    private BigDecimal totalPrice;
}
