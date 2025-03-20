package com.roomx.domain.model.vo;

import java.util.Objects;
import java.util.UUID;

public record BookingParticipantId(UUID bookingId, UUID userId) {
    public BookingParticipantId {
        Objects.requireNonNull(bookingId, "ID bookingId không được null");
        Objects.requireNonNull(userId, "ID userId không được null");
    }
}
