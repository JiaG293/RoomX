package com.roomx.domain.model.vo;


import java.util.Objects;
import java.util.UUID;


public record BookingRequestParticipantId(UUID bookingRequestId, UUID userId) {

    public BookingRequestParticipantId {
        Objects.requireNonNull(bookingRequestId, "ID đơn đặt không được null");
        Objects.requireNonNull(userId, "ID người dùng không được null");
    }
}
