package com.roomx.shared.dto.booking.base;

import java.time.LocalDate;

public record RoomScheduleResultDto(LocalDate date, boolean hasConflict, String optimalRoomId, String alternativeRoomId) {}