package com.roomx.infrastructure.persistence.model.projection;

import java.util.UUID;

public interface RoomClassProjection {
    UUID getId();
    String getRoomClassCode();
    Integer getCapacity();
    String getStatus();
    PriceRoomClassProjection getPrice();

}

