package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.enums.RoomStatusType;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Room {
    private UUID id;
    private String roomCode;
    @Builder.Default
    private String status = RoomStatusType.AVAILABLE.toString();
    private String description;
    private Place place;
    private RoomClass roomClass;

    public String getRoomName(){
        return place.getBuilding() + " " + place.getFloor() + "." + roomCode;
    }

    public BigDecimal getTotalPrice(){
        return roomClass.getTotalPrice();
    }

}
