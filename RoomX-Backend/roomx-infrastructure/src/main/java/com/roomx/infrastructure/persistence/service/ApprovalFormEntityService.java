package com.roomx.infrastructure.persistence.service;

import com.roomx.domain.model.aggrerate.ApprovalForm;
import com.roomx.infrastructure.persistence.model.projection.ApprovalFormProjection;
import com.roomx.infrastructure.persistence.model.projection.BookingRequestFlatProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

public interface ApprovalFormEntityService {
    Page<ApprovalForm> findAllByLastStatusInAndTimeRangeWithBookingRequest(List<String> listStatusCanApproval, Instant startDate, Instant endDate, Pageable pageable);
    Page<ApprovalForm> findAllByLastStatusInAndTimeRangeAndRequesterWithBookingRequest(List<String> listStatusCanApproval, Instant startDate, Instant endDate, String requester, Pageable pageable);
    Page<BookingRequestFlatProjection> findAllByLastStatusAndDateRange(List<String> listStatusCanApproval, Instant startDate, Instant endDate, Pageable pageable);

    Page<ApprovalForm> findAllByLastStatusInAndTimeRangeAndStatusWithBookingRequest(List<String> listStatusCanApproval, Instant startDate, Instant endDate, String status, Pageable pageable);
    Page<ApprovalForm> findAllByLastStatusInAndTimeRangeAndRequesterAndStatusWithBookingRequest(List<String> listStatusCanApproval, Instant startDate, Instant endDate, String requester, String status, Pageable pageable);

    Page<ApprovalForm> findAllByStatusAndTimeRangeWithBookingRequest(List<String> listStatus, Instant startDate, Instant endDate, String requester, Pageable pageable);

}
