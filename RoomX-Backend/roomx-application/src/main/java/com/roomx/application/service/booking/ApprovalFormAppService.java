package com.roomx.application.service.booking;

import com.roomx.shared.dto.booking.request.ApprovalFormAdminCreateRequest;
import com.roomx.application.mapper.ApprovalFormAppMapper;
import com.roomx.application.service.user.UserAppService;
import com.roomx.domain.model.aggrerate.ApprovalForm;
import com.roomx.shared.enums.ApprovalStatusType;
import com.roomx.shared.enums.RoleType;
import com.roomx.domain.repository.ApprovalFormRepository;
import com.roomx.domain.repository.BookingRequestRepository;
import com.roomx.infrastructure.multitenancy.security.oauth.RoleEvaluator;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApprovalFormAppService {
    private final ApprovalFormAppMapper approvalFormAppMapper;
    private final ApprovalFormRepository approvalFormRepository;
    private final UserAppService userAppService;
    private final RoleEvaluator roleEvaluator;
    private final BookingRequestRepository bookingRequestRepository;

    @Transactional
    public ApprovalForm assignApprover(ApprovalFormAdminCreateRequest request) {
        var approver = userAppService.findApproverWithFree(); // Not finish random user impl logic after

        var status = ApprovalStatusType.PENDING.toString();

        boolean checkRoleRequester = roleEvaluator.hasAnyRole(List.of(RoleType.OWNER.toString(), RoleType.ADMIN.toString(), RoleType.APPROVER.toString()));

        if (checkRoleRequester) {
            status = ApprovalStatusType.APPROVED.toString();
        }

        var bookingRequest = bookingRequestRepository.findById(request.getBookingRequestId())
                .orElseThrow(()-> new AppException(ErrorCode.BOOKING_REQUEST_NOT_FOUND, request.getBookingRequestId()));

        var approvalFormDomain = ApprovalForm.builder()
//                .approver(approver)
                .status(status)
                .bookingRequest(bookingRequest)
                .note(request.getNote())
                .build();
        return approvalFormRepository.save(approvalFormDomain);
    }
}
