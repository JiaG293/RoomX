package com.roomx.application.service.booking;

import com.roomx.application.mapper.BookingAppMapper;
import com.roomx.domain.model.aggrerate.*;
import com.roomx.domain.model.entity.BookingParticipant;
import com.roomx.domain.model.entity.EquipmentRequest;
import com.roomx.domain.model.entity.RoomClassPriceHistory;
import com.roomx.domain.model.entity.ServiceRequest;
import com.roomx.domain.model.vo.BookingParticipantId;
import com.roomx.domain.model.vo.EquipmentRequestId;
import com.roomx.domain.model.vo.ServiceRequestId;
import com.roomx.domain.service.BookingDomainService;
import com.roomx.infrastructure.multitenancy.persistence.dto.BookingFilter;
import com.roomx.infrastructure.multitenancy.persistence.dto.BranchFilter;
import com.roomx.infrastructure.multitenancy.persistence.mapper.BookingEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.mapper.EquipmentRequestEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.mapper.ServiceRequestEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaBookingEntityRepository;
import com.roomx.infrastructure.multitenancy.persistence.service.ApprovalFormEntityService;
import com.roomx.infrastructure.multitenancy.persistence.service.BookingEntityService;
import com.roomx.infrastructure.multitenancy.persistence.service.PageableQueryService;
import com.roomx.shared.dto.TestRequest;
import com.roomx.shared.dto.booking.base.RoomScheduleResultDto;
import com.roomx.shared.dto.booking.request.ApprovalFormAdminCreateRequest;
import com.roomx.shared.dto.booking.request.BookingQueryRequest;
import com.roomx.shared.dto.booking.request.BookingRequestAdminCreateRequest;
import com.roomx.shared.dto.booking.response.BookingRequestResponse;
import com.roomx.application.mapper.BookingRequestAppMapper;
import com.roomx.application.mapper.EquipmentRequestAppMapper;
import com.roomx.application.mapper.ServiceRequestAppMapper;
import com.roomx.application.service.resource.RoomAppService;
import com.roomx.shared.dto.booking.request.BookingRequestUserCreateRequest;
import com.roomx.shared.dto.booking.response.BookingResponse;
import com.roomx.shared.dto.resource.request.BranchQueryRequest;
import com.roomx.shared.dto.resource.response.BranchResponse;
import com.roomx.shared.enums.ApprovalStatusType;
import com.roomx.domain.repository.*;
import com.roomx.infrastructure.multitenancy.persistence.mapper.BookingRequestEntityMapper;
import com.roomx.infrastructure.multitenancy.security.oauth.SecurityUtil;
import com.roomx.shared.enums.BookingStatusType;
import com.roomx.shared.enums.DeleteStatusType;
import com.roomx.shared.enums.RoomStatusType;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;
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
    private final BookingParticipantRepository bookingParticipantRepository;
    private final ApprovalFormRepository approvalFormRepository;

    private final BookingRepository bookingRepository;
    private final RoomSchedulerAppService roomSchedulerAppService;
    private final BookingDomainService bookingDomainService;
    private final RoomClassPriceHistoryRepository roomClassPriceHistoryRepository;
    private final RoomClassRepository roomClassRepository;
    private final PageableQueryService<BookingFilter, Booking> bookingPageableQueryService;

    private final JpaBookingEntityRepository jpaBookingEntityRepository;
    private final BookingAppMapper bookingAppMapper;
    private final ApprovalFormEntityService approvalFormEntityService;



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
    private static final Logger logger = LoggerFactory.getLogger(BookingAppService.class);

    @Transactional
    public BookingRequestResponse createBookingRequest(BookingRequestUserCreateRequest request) {
        var bookingRequestDomain = bookingRequestAppMapper.toDomainUser(request);

        bookingRequestDomain.setRequester(UUID.fromString(securityUtil.getCurrentUserId()));
        bookingRequestDomain.setEndDateApproval(Instant.now().plus(3, ChronoUnit.DAYS));
        bookingRequestDomain.setCreatedAt(Instant.now());
        bookingRequestDomain.setUpdatedAt(Instant.now());


        var savedBookingRequest = bookingRequestRepository.save(bookingRequestDomain);
        log.info("Booking Request ID after save: {}", savedBookingRequest.getId());

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

        var approvalFormDomain = approvalFormAppService.createApprovalFormPending(savedBookingRequest, null);

        var response = bookingRequestAppMapper.toResponse(savedBookingRequest);
        response.setApprovalStatus(approvalFormDomain.getStatus());

        return response;
    }

    //    @PreAuthorize("roleEvaluator.hasAnyRole('ADMIN')")
    public Object checkRoomSuitable(String bookingRequestId) {
        var approvalFormDomain = approvalFormRepository
                .findByBookingRequestIdAndLastStatusWithBookingRequest(bookingRequestId, ApprovalStatusType.PENDING.toString())
                .orElseThrow(() -> new AppException(ErrorCode.APPROVAL_FORM_NOT_FOUND));

        String branchId = (approvalFormDomain.getBookingRequest().getBranchId() != null)
                ? approvalFormDomain.getBookingRequest().getBranchId().toString()
                : null;
        var bookingRequestDomain = approvalFormDomain.getBookingRequest();
        var listOccurrences = bookingRequestDomain.getOccurrences();
        var result = roomSchedulerAppService.checkScheduleAndFindOptimalRoomSameRoomIdWithBranchOptional(
                bookingRequestId,
                branchId,
                listOccurrences,
                bookingRequestDomain.getStartTime(),
                bookingRequestDomain.getEndTime(),
                bookingRequestDomain.getCapacity(),
                bookingRequestDomain.getParticipants(),
                10
        );
        return result;

    }


    @Transactional
    public Object approveBooking(String bookingRequestId) {
        var approvalFormDomain = approvalFormRepository
                .findByBookingRequestIdAndLastStatusWithBookingRequest(bookingRequestId, ApprovalStatusType.PENDING.toString())
                .orElseThrow(() -> new AppException(ErrorCode.APPROVAL_FORM_NOT_FOUND));

        String branchId = (approvalFormDomain.getBookingRequest().getBranchId() != null)
                ? approvalFormDomain.getBookingRequest().getBranchId().toString()
                : null;

        var bookingRequestDomain = approvalFormDomain.getBookingRequest();
        var listOccurrences = bookingRequestDomain.getOccurrences();
        var result = roomSchedulerAppService.checkScheduleAndFindOptimalRoomSameRoomIdWithBranchOptional(
                bookingRequestId,
                branchId,
                listOccurrences,
                bookingRequestDomain.getStartTime(),
                bookingRequestDomain.getEndTime(),
                bookingRequestDomain.getCapacity(),
                bookingRequestDomain.getParticipants(),
                10
        );

        var dateConflictList = result.stream()
                .filter(dateMeeting -> dateMeeting.isHasConflict() && dateMeeting.getOptimalRoomId() != null)
                .map(RoomScheduleResultDto::getDate)
                .toList();

        var bookingDomainList = new ArrayList<Booking>();
        if (dateConflictList.isEmpty()) {
            result.forEach(occurrence -> {
                var roomDomain = Room.builder().id(UUID.fromString(occurrence.getOptimalRoomId())).build();
                var totalPrice = roomRepository
                        .findPriceByIdAndValidTimestamp(
                                occurrence.getOptimalRoomId(),
                                bookingRequestDomain.getCreatedAt())
                        .orElse(BigDecimal.ZERO);
                var bookingDomainId = UUID.randomUUID();

                var bookingDomain = Booking.builder()
                        .id(bookingDomainId)
                        .bookingRequest(bookingRequestDomain)
                        .room(roomDomain)
                        .bookingCode(generateBookingCode(occurrence.getDate()))
                        .meetingStart(bookingRequestDomain.getStartTime())
                        .meetingEnd(bookingRequestDomain.getEndTime())
                        .meetingDate(occurrence.getDate())
                        .totalPrice(totalPrice)
                        .status(BookingStatusType.SCHEDULED.toString())
                        .count(1)
                        .build();
                ;
                bookingDomainList.add(bookingRepository.save(bookingDomain));


                var bookingParticpantsDomainList = new ArrayList<BookingParticipant>();
                bookingRequestDomain.getParticipants().forEach(participant -> {
                    var userIdDomain = userRepository.findByEmailCustom(participant)
                            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED, participant));
                    var bookingParticipant = BookingParticipant.builder()
                            .id(new BookingParticipantId(bookingDomain.getId(), userIdDomain))
                            .user(User.builder().id(userIdDomain).build())
                            .booking(bookingDomain)
                            .build();
                    bookingParticpantsDomainList.add(bookingParticipant);

                });
                bookingParticipantRepository.saveAll(bookingParticpantsDomainList);
            });

        } else {
            throw new AppException(ErrorCode.BOOKING_CONFLICT, dateConflictList);
        }

        approvalFormRepository.save(ApprovalForm.builder()
                .approver(UUID.fromString(securityUtil.getCurrentUserId()))
                .bookingRequest(bookingRequestDomain)
                .note("")
                .status(ApprovalStatusType.APPROVED.toString())
                .build());

        return bookingDomainList.stream()
                .map(booking -> Map.of(
                        "meetingDate", booking.getMeetingDate(),
                        "id", booking.getId(),
                        "roomId", booking.getRoom().getId(),
                        "place", booking.getPlaceDetail(),
                        "capacity", booking.getBookingRequest().getCapacity(),
                        "totalPrice", booking.getTotalPrice(),
                        "participants", booking.getBookingRequest().getParticipants()
                ))
                .toList();
    }


    public Page<BookingResponse> filterPageBookingUser(
            BookingQueryRequest filterRequest,
            int page,
            int size,
            String sortBy,
            String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        var bookingFilter = BookingFilter.builder()
                .id(filterRequest.getId())
                .bookingCode(filterRequest.getId())
                .bookingRequestId(filterRequest.getBookingRequestId())
                .meetingDate(filterRequest.getMeetingDate())
                .meetingStart(filterRequest.getMeetingStart())
                .meetingEnd(filterRequest.getMeetingEnd())
                .status(BookingStatusType.SCHEDULED.toString())
                .roomId(filterRequest.getRoomId())
                .previousRoomId(filterRequest.getPreviousRoomId())
                .totalPrice(filterRequest.getTotalPrice())
                .updatedAt(filterRequest.getUpdatedAt())
                .createdAt(filterRequest.getCreatedAt())
                .build();

        var bookingDomainPage = bookingPageableQueryService.filterPageBranchs(bookingFilter, pageable, filterRequest.isTypeCompare());

        return bookingDomainPage.map(bookingAppMapper::toResponse);
    }

    public Page<BookingResponse> filterPageBookingAdmin(
            BookingQueryRequest filterRequest,
            int page,
            int size,
            String sortBy,
            String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        var bookingFilter = BookingFilter.builder()
                .id(filterRequest.getId())
                .bookingCode(filterRequest.getId())
                .bookingRequestId(filterRequest.getBookingRequestId())
                .meetingDate(filterRequest.getMeetingDate())
                .meetingStart(filterRequest.getMeetingStart())
                .meetingEnd(filterRequest.getMeetingEnd())
                .status(filterRequest.getStatus())
                .roomId(filterRequest.getRoomId())
                .previousRoomId(filterRequest.getPreviousRoomId())
                .totalPrice(filterRequest.getTotalPrice())
                .updatedAt(filterRequest.getUpdatedAt())
                .createdAt(filterRequest.getCreatedAt())
                .build();

        var bookingDomainPage = bookingPageableQueryService.filterPageBranchs(bookingFilter, pageable, filterRequest.isTypeCompare());

        return bookingDomainPage.map(bookingAppMapper::toResponse);
    }


    public Page<BookingRequestResponse> getListPageBookingRequestAdminApproval(String byType, Integer value, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        LocalDate today = LocalDate.now();
        LocalDate startDate = today;
        LocalDate endDate = today;

        switch (byType.toLowerCase()) {
            case "week" -> {
                int weekNumber = (value != null && value > 0) ? value : today.get(WeekFields.of(Locale.getDefault()).weekOfYear());
                startDate = LocalDate.of(today.getYear(), 1, 1)
                        .with(WeekFields.of(Locale.getDefault()).weekOfYear(), weekNumber)
                        .with(DayOfWeek.MONDAY);
                endDate = startDate.with(DayOfWeek.SUNDAY);
            }
            case "month" -> {
                int month = (value != null && value >= 1 && value <= 12) ? value : today.getMonthValue();
                startDate = today.withMonth(month).withDayOfMonth(1);
                endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            }
            case "year" -> {
                int year = (value != null && value > 0) ? value : today.getYear();
                startDate = today.withYear(year).withDayOfYear(1);
                endDate = startDate.withDayOfYear(startDate.lengthOfYear());
            }
            default -> {
                startDate = today;
                endDate = today;
            }
        }

        ZoneId zoneId = ZoneId.systemDefault();


        Instant startInstant = startDate.atStartOfDay(zoneId).toInstant();
        Instant endInstant = endDate.atTime(LocalTime.MAX).atZone(zoneId).toInstant();

        log.info("date la: {} -> {} insant: {} -> {}", startDate, endDate, startInstant, endInstant);
        Page<ApprovalForm> approvalFormPage =
                approvalFormEntityService.findAllByLastStatusInAndTimeRangeWithBookingRequest(
                        ApprovalStatusType.getListCanApproval(), startInstant, endInstant, pageable);

        return approvalFormPage.map(approvalForm ->

                bookingRequestAppMapper.toResponseFromApprovalForm(approvalForm.getBookingRequest(), approvalForm)
        );
    }


    @Transactional
    public Object test() {

        return true;

    }


    private String generateBookingCode(LocalDate meetingDate) {
        return meetingDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + Long.toString(System.nanoTime(), 36).toUpperCase();
    }


}
