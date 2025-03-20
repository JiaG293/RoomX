package com.roomx.domain.model.vo;

import java.util.Objects;
import java.util.UUID;

public record BookingServiceId(UUID bookingId, UUID serviceId) {
    public BookingServiceId {
        Objects.requireNonNull(bookingId, "ID bookingId không được null");
        Objects.requireNonNull(serviceId, "ID serviceId không được null");
    }
}
