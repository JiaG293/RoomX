package com.roomx.domain.model.vo;

import java.util.Objects;
import java.util.UUID;

public record EquipmentRoomClassId(UUID roomClassId, UUID equipmentId) {
    public EquipmentRoomClassId(UUID roomClassId, UUID equipmentId) {
        this.roomClassId = Objects.requireNonNull(roomClassId, "ID loại phòng không được null");
        this.equipmentId = Objects.requireNonNull(equipmentId, "ID thiết bị không được null");
    }
}
