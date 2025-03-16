package com.roomx.application.dto.resource.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailResponse {
    private String id;
    private String roomCode;
    private RoomClassResponse roomClass;
    private PlaceResponse place;
    private String description;
    private String status;
    private BigDecimal totalPrice;
}
