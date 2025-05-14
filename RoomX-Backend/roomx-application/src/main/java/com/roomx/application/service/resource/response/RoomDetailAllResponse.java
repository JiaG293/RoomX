package com.roomx.application.service.resource.response;

import com.roomx.shared.dto.resource.response.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDetailAllResponse {
    private String id;
    private String roomCode;
    private PlaceResponse place;
    private String description;
    private String status;
    private List<String> imageUrls;
    private RoomClassResponse roomClass;
    private BigDecimal totalPrice;

    private BigDecimal equipmentsTotalPrice;
    private List<EquipmentRoomClassDetailResponse> equipments;

    private BigDecimal servicesTotalPrice;
    private List<ServiceRoomClassDetailResponse> services;
}
