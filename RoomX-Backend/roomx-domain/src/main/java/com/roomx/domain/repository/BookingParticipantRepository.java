package com.roomx.domain.repository;

import com.roomx.domain.model.entity.BookingParticipant;
import com.roomx.domain.model.vo.BookingParticipantId;

import java.util.Collection;
import java.util.List;

public interface BookingParticipantRepository {
    BookingParticipant save(BookingParticipant bookingParticipant);
    List<BookingParticipant> saveAll(List<BookingParticipant> bookingParticipants);

    boolean checkExistsByBookingParticipantId(BookingParticipantId id);
    List<BookingParticipant> findAllByUserId(String useId);

    List<BookingParticipant> findAllBookingId(String bookingId);
}
