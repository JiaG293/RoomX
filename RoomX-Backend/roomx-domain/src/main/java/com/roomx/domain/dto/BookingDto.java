package com.roomx.domain.dto;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.aggrerate.Room;
import com.roomx.domain.model.entity.BookingParticipant;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record BookingDto(
        UUID id,
        String bookingCode,
        BookingRequest bookingRequest,
        Room room,
        Room previousRoom,
        LocalTime meetingStart,
        LocalTime meetingEnd,
        LocalDate meetingDate,
        BigDecimal totalPrice,
        List<String> participants
) {
}
