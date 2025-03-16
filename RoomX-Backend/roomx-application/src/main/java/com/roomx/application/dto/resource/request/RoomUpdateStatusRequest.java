package com.roomx.application.dto.resource.request;

import jakarta.validation.constraints.NotNull;

public record RoomUpdateStatusRequest(
        @NotNull
        String status
){}
