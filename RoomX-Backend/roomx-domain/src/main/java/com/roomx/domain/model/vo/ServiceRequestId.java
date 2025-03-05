package com.roomx.domain.model.vo;

import java.util.Objects;
import java.util.UUID;

public record ServiceRequestId(UUID bookingRequestId, UUID serviceId) {
    public ServiceRequestId(UUID bookingRequestId, UUID serviceId) {
        this.bookingRequestId = Objects.requireNonNull(bookingRequestId, "ID đơn yêu cầu không được null");
        this.serviceId = Objects.requireNonNull(serviceId, "ID dịch vụ không được null");
    }
}
