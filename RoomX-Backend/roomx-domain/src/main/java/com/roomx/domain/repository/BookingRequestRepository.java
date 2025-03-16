package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.BookingRequest;

import java.util.Optional;

public interface BookingRequestRepository {
    Optional<BookingRequest> findById(String id);
    BookingRequest save(BookingRequest bookingRequest);
}
