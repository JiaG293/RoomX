package com.roomx.domain.model.vo;

import java.util.Objects;
import java.util.UUID;

public record ServiceRoomClassId(UUID roomClassId, UUID serviceId) {
    public ServiceRoomClassId(UUID roomClassId, UUID serviceId) {
        this.roomClassId = Objects.requireNonNull(roomClassId, "ID loại phòng không được null");
        this.serviceId = Objects.requireNonNull(serviceId, "ID dịch vụ không được null");
    }
}
