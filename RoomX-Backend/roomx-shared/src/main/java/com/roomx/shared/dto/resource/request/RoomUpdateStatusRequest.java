package com.roomx.shared.dto.resource.request;

import jakarta.validation.constraints.NotNull;

public record RoomUpdateStatusRequest(
        @NotNull
        String status
){}
