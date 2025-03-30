package com.roomx.domain.repository;



import com.roomx.domain.model.aggrerate.ApprovalForm;

import java.util.Optional;

public interface ApprovalFormRepository {
    Optional<ApprovalForm> findById(String id);
    ApprovalForm save(ApprovalForm approvalForm);
    Optional<ApprovalForm> findByBookingRequestIdAndLastStatusWithBookingRequest(String bookingRequestId, String status);

}
