package com.roomx.domain.repository;

import com.google.api.gax.paging.Page;
import com.roomx.domain.model.aggrerate.ApprovalForm;
import com.roomx.domain.model.aggrerate.BookingRequest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ApprovalFormRepository {
    Optional<ApprovalForm> findById(String id);
    ApprovalForm save(ApprovalForm approvalForm);
    Optional<ApprovalForm> findByBookingRequestIdAndLastStatusWithBookingRequest(String bookingRequestId, String status);

    Optional<ApprovalForm> findByBookingRequestIdLastStatusWithBookingRequest(String bookingRequestId);

    List<ApprovalForm> findAllBookingRequestWithInStatus(List<String> status);
    Optional<ApprovalForm> findByBookingRequestIdLastStatus(String bookingRequestId);

    List<ApprovalForm> findAllByStatusAndTimeRangeWithBookingRequest(List<String> listStatus, Instant startDate, Instant endDate, String requester);

}
