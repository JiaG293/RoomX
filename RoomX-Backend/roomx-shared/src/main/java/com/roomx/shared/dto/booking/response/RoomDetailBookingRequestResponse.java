package com.roomx.shared.dto.booking.response;

import com.roomx.shared.dto.resource.response.PlaceNameResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailBookingRequestResponse {
    private String id;
    private String name;
    private String code;
    private PlaceNameResponse branch;
    private PlaceNameResponse building;
    private PlaceNameResponse floor;

}
