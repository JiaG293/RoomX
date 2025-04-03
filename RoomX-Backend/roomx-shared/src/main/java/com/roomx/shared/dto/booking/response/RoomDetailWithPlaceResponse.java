package com.roomx.shared.dto.booking.response;

import com.roomx.shared.dto.resource.response.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDetailWithPlaceResponse {
    private String id;
    private String roomCode;
    private PlaceNameResponse floor;
    private PlaceNameResponse building;
    private PlaceNameResponse branch;
    private RoomClassResponse roomClass;
    private PlaceResponse place;
    private BigDecimal totalPrice;
}
