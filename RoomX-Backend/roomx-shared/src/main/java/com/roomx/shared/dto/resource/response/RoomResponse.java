package com.roomx.shared.dto.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private String id;
    private String roomCode;
    private String roomClassId;
    private String placeId;
    private String description;
    private String status;
    private BigDecimal totalPrice;

}
