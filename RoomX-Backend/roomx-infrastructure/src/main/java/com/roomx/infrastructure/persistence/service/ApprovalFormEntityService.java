package com.roomx.infrastructure.persistence.service;

import com.roomx.domain.model.aggrerate.ApprovalForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

public interface ApprovalFormEntityService {
    Page<ApprovalForm> findAllByLastStatusInAndTimeRangeWithBookingRequest(List<String> listStatusCanApproval, Instant startDate, Instant endDate, Pageable pageable);
}
