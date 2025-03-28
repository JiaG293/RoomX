package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.BookingRequest;

import java.util.Optional;
import java.util.UUID;

public interface BookingRequestRepository {
    Optional<BookingRequest> findById(String id);
    BookingRequest save(BookingRequest bookingRequest);
//    Optional<BookingRequest> findByIdAndApprovalStatus(String id, String approvalStatus);
}
