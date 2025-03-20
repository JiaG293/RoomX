package com.roomx.application.service.booking;

import com.roomx.shared.dto.booking.request.ApprovalFormAdminCreateRequest;
import com.roomx.shared.dto.booking.request.BookingRequestAdminCreateRequest;
import com.roomx.shared.dto.booking.response.BookingRequestResponse;
import com.roomx.application.mapper.BookingRequestAppMapper;
import com.roomx.application.mapper.EquipmentRequestAppMapper;
import com.roomx.application.mapper.ServiceRequestAppMapper;
import com.roomx.application.service.resource.RoomAppService;
import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.shared.enums.ApprovalStatusType;
import com.roomx.domain.repository.*;
import com.roomx.infrastructure.multitenancy.persistence.mapper.BookingRequestEntityMapper;
import com.roomx.infrastructure.multitenancy.security.oauth.SecurityUtil;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingAppService {
    private final BookingRequestEntityMapper bookingRequestEntityMapper;
    private final BookingRequestRepository bookingRequestRepository;
    private final BookingRequestAppMapper bookingRequestAppMapper;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RoomAppService roomAppService;
    private final SecurityUtil securityUtil;
    private final ServiceRequestAppMapper serviceRequestAppMapper;
    private final EquipmentRequestAppMapper equipmentRequestAppMapper;
    private final ServiceRequestRepository serviceRequestRepository;
    private final EquipmentRequestRepository equipmentRequestRepository;
    private final ServiceRequestAppService serviceRequestAppService;
    private final ApprovalFormAppService approvalFormAppService;

    @Transactional
    public BookingRequestResponse adminCreateBooking(BookingRequestAdminCreateRequest request) {


        var roomDomain = roomAppService.getRoomFree(request.getRoomId());

        var userRequestDomain = userRepository.findById(UUID.fromString(securityUtil.getCurrentUserId()), true)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var statusApproval = request.getApprovalStatus() == null || request.getApprovalStatus().isEmpty()
                ? ApprovalStatusType.APPROVED.toString() : ApprovalStatusType.APPROVED.toString();

        var priority = request.getApprovalStatus().isEmpty()
                ? 0 : 1;


        var bookingRequestDomain = BookingRequest.builder()
                .room(roomDomain)
                .requester(userRequestDomain)
                .approvalStatus(statusApproval)
                .priority((short) priority)
                .build();
        bookingRequestDomain = bookingRequestRepository.save(bookingRequestDomain);
        var approverRequest = ApprovalFormAdminCreateRequest.builder()
                .bookingRequestId(bookingRequestDomain.getId().toString())
                .build();


        var createApprovalFormDomainStatusApproved = approvalFormAppService.assignApprover(approverRequest);

        var serviceBookingRequest = serviceRequestAppService.createAll(bookingRequestDomain.getId().toString(), request.getServiceRequests());

        return bookingRequestAppMapper.toResponse(bookingRequestDomain);
    }

}
