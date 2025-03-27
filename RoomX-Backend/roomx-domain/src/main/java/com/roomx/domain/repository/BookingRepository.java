package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.model.aggrerate.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    Optional<Booking> findById(String bookingId);
    Optional<Booking> findByIdAndStatus(String bookingId, String status);
    List<Booking> findAllByStatus(String status);
    Booking save(Booking booking);
    List<Booking> save(List<Booking> bookings);
//    List<Booking> findAllByRoomIdsAndMeetingDate(List<String> roomIds, LocalDate date);

    List<Booking> findAllByMeetingDateAndRoomId(LocalDate date, String roomId);
}
