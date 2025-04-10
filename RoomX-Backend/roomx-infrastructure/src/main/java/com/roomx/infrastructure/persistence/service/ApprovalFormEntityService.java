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
}
