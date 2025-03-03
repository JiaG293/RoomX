package com.roomx.domain.model.vo;


import java.util.Objects;
import java.util.UUID;

public record EquipmentRequestId(UUID bookingRequestId, UUID equipmentId) {

    public EquipmentRequestId {
        Objects.requireNonNull(bookingRequestId, "ID đơn yêu cầu không được null");
        Objects.requireNonNull(equipmentId, "ID thiết bị không được null");
    }
}



