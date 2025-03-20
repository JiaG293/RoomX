package com.roomx.domain.model.vo;

import java.util.Objects;
import java.util.UUID;

public record BookingEquipmentId(UUID bookingId, UUID equipmentId) {
    public BookingEquipmentId {
        Objects.requireNonNull(bookingId, "ID bookingId không được null");
        Objects.requireNonNull(equipmentId, "ID equipmentId không được null");
    }
}
