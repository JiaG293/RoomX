package com.roomx.application.service.booking;

import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.model.entity.EquipmentRequest;
import com.roomx.domain.model.entity.ServiceRequest;
import com.roomx.domain.model.vo.EquipmentRequestId;
import com.roomx.domain.model.vo.ServiceRequestId;
import com.roomx.domain.service.BookingDomainService;
import com.roomx.infrastructure.multitenancy.persistence.mapper.EquipmentRequestEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.mapper.ServiceRequestEntityMapper;
import com.roomx.shared.dto.booking.request.ApprovalFormAdminCreateRequest;
import com.roomx.shared.dto.booking.request.BookingRequestAdminCreateRequest;
import com.roomx.shared.dto.booking.response.BookingRequestResponse;
import com.roomx.application.mapper.BookingRequestAppMapper;
import com.roomx.application.mapper.EquipmentRequestAppMapper;
import com.roomx.application.mapper.ServiceRequestAppMapper;
import com.roomx.application.service.resource.RoomAppService;
import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.shared.dto.booking.request.BookingRequestUserCreateRequest;
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

import java.awt.print.Book;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingAppService {
    private final EquipmentRequestEntityMapper equipmentRequestEntityMapper;
    private final ServiceRequestEntityMapper serviceRequestEntityMapper;
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
    private final ServiceRepository serviceRepository;
    private final EquipmentRepository equipmentRepository;
    private final EquipmentPriceHistoryRepository equipmentPriceHistoryRepository;
    private final ServicePriceHistoryRepository servicePriceHistoryRepository;

    private final BookingRepository bookingRepository;
    private final RoomSchedulerAppService roomSchedulerAppService;

    /*@Transactional
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
    }*/

    @Transactional
    public BookingRequestResponse createBookingRequest(BookingRequestUserCreateRequest request) {
        var bookingRequestDomain = bookingRequestAppMapper.toDomainUser(request);

        bookingRequestDomain.setRequester(UUID.fromString(securityUtil.getCurrentUserId()));
        bookingRequestDomain.setEndDateApproval(Instant.now().plus(3, ChronoUnit.DAYS));
        bookingRequestDomain.setCreatedAt(Instant.now());
        bookingRequestDomain.setUpdatedAt(Instant.now());

        var savedBookingRequest = bookingRequestRepository.save(bookingRequestDomain);
        var servicesDomain = request.getServices().stream()
                .map(service -> {
                    var serviceFind = serviceRepository.findById(service.getServiceId())
                            .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND, service.getServiceId()));
                    var serviceLastPrice = servicePriceHistoryRepository.findLatestValidFrom(serviceFind.getId().toString())
                            .orElse(null);
                    serviceFind.setPrice(serviceLastPrice);

                    return ServiceRequest.builder()
                            .bookingRequest(savedBookingRequest)
                            .service(serviceFind)
                            .quantity((short) service.getQuantity())
                            .id(new ServiceRequestId(savedBookingRequest.getId(), serviceFind.getId()))
                            .build();
                })
                .toList();


        var equipmentsDomain = request.getEquipments().stream()
                .map(equipment -> {
                    var equipmentFind = equipmentRepository.findById(equipment.getEquipmentId())
                            .orElseThrow(() -> new AppException(ErrorCode.EQUIPMENT_NOT_FOUND, equipment.getEquipmentId()));
                    var equipmentLastPrice = equipmentPriceHistoryRepository.findLatestValidFrom(equipmentFind.getId().toString())
                            .orElse(null);
                    equipmentFind.setPrice(equipmentLastPrice);

                    return EquipmentRequest.builder()
                            .bookingRequest(savedBookingRequest)
                            .equipment(equipmentFind)
                            .quantity((short) equipment.getQuantity())
                            .id(new EquipmentRequestId(savedBookingRequest.getId(), equipmentFind.getId()))
                            .build();
                })
                .toList();

        serviceRequestRepository.saveAll(servicesDomain);
        equipmentRequestRepository.saveAll(equipmentsDomain);

        savedBookingRequest.setServices(servicesDomain);
        savedBookingRequest.setEquipments(equipmentsDomain);
        return bookingRequestAppMapper.toResponse(savedBookingRequest);
    }


}
