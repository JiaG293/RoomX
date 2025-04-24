package com.roomx.shared.dto.resource.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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
    private List<String> imageUrls;
}
