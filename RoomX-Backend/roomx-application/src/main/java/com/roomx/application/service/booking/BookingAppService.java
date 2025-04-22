package com.roomx.application.service.booking;

import com.roomx.application.mapper.*;
import com.roomx.domain.model.aggrerate.*;
import com.roomx.domain.model.entity.*;
import com.roomx.domain.model.vo.BookingParticipantId;
import com.roomx.domain.model.vo.EquipmentRequestId;
import com.roomx.domain.model.vo.ServiceRequestId;
import com.roomx.domain.service.BookingDomainService;
import com.roomx.domain.service.ConflictBookingResolutionDomainService;
import com.roomx.infrastructure.cache.redis.service.RoomCheckingCacheService;
import com.roomx.infrastructure.persistence.dto.BookingFilter;
import com.roomx.infrastructure.persistence.mapper.EquipmentRequestEntityMapper;
import com.roomx.infrastructure.persistence.mapper.ServiceRequestEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaBookingEntityRepository;
import com.roomx.infrastructure.persistence.service.ApprovalFormEntityService;
import com.roomx.infrastructure.persistence.service.BookingEntityService;
import com.roomx.infrastructure.security.oauth.RoleEvaluator;
import com.roomx.shared.dto.booking.base.RoomScheduleResultDto;
import com.roomx.shared.dto.booking.request.*;
import com.roomx.shared.dto.booking.response.*;
import com.roomx.application.service.resource.RoomAppService;
import com.roomx.shared.dto.resource.response.RoomResponse;
import com.roomx.shared.enums.ApprovalStatusType;
import com.roomx.domain.repository.*;
import com.roomx.infrastructure.persistence.mapper.BookingRequestEntityMapper;
import com.roomx.infrastructure.security.oauth.SecurityUtil;
import com.roomx.shared.enums.BookingStatusType;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingAppService {
    private final PlaceAppMapper placeAppMapper;
    private final ServiceAppMapper serviceAppMapper;
    private final EquipmentAppMapper equipmentAppMapper;
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
    private final UserAppMapper userAppMapper;
    private final BookingAppMapper bookingAppMapper;

    private final BookingRepository bookingRepository;
    private final RoomSchedulerAppService roomSchedulerAppService;
    private final BookingDomainService bookingDomainService;
    private final RoomClassPriceHistoryRepository roomClassPriceHistoryRepository;
    private final RoomClassRepository roomClassRepository;

    private final JpaBookingEntityRepository jpaBookingEntityRepository;
    private final ApprovalFormEntityService approvalFormEntityService;
    private final BookingEntityService bookingEntityService;
    private final BookingServiceRepository bookingServiceRepository;
    private final BookingEquipmentRepository bookingEquipmentRepository;
    private final BookingServiceAppMapper bookingServiceAppMapper;
    private final BookingEquipmentAppMapper bookingEquipmentAppMapper;
    private final BookingParticipantAppMapper bookingParticitipantAppMapper;
    private final RoleEvaluator roleEvaluator;
    private final DateRequestExceptionRepository dateRequestExceptionRepository;
    private final DateRequestExceptionAppMapper dateRequestExceptionAppMapper;
    private final RoomCheckingCacheService roomCheckingCacheService;
    private final ConflictBookingResolutionDomainService conflictBookingResolutionDomainService;
    private final PlaceRepository placeRepository;
    private final RoomAppMapper roomAppMapper;



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
    public Object checkingBookingRequest(CheckingBookingRequest request) {
        BookingRequest bookingRequestDomain = bookingRequestAppMapper.toDomainChecking(request);

        /*var bookingRequest = BookingRequest.builder()
                .daysOfWeek(request.getDaysOfWeek())
                .priority((short) request.getPriority())
                .capacity(request.getCapacity())
                .recurrenceInterval((short) request.getRecurrenceInterval())
                .endTime(request.getEndTime())
                .startTime(request.getStartTime())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .branchId(UUID.fromString(request.getBranchId()))
                .roomId(UUID.fromString(request.getRoomId()))
                .build();*/
        var listOccurrences = bookingRequestDomain.getOccurrences();
        List<DateRequestException> dateExceptions = bookingRequestDomain.getDateRequestExceptions() != null ? bookingRequestDomain.getDateRequestExceptions() : Collections.EMPTY_LIST;

        log.info("check time: {}", dateExceptions);
        List<RoomScheduleResultDto> response = roomSchedulerAppService.
                checkScheduleAndFindOptimalRoomSameRoomIdWithBranchOptionalV3(
                        request.getBranchId(),
                        listOccurrences,
                        dateExceptions,
                        bookingRequestDomain.getStartTime(),
                        bookingRequestDomain.getEndTime(),
                        bookingRequestDomain.getCapacity(),
                        bookingRequestDomain.getParticipants(),
                        10
                );
        return response;
    }

    @Transactional
    public BookingRequestResponse createBookingRequest(BookingRequestUserCreateRequest request) {
        var bookingRequestDomain = bookingRequestAppMapper.toDomainUser(request);
        log.info("check request: {}", bookingRequestDomain);

        log.info("check user id: {}", securityUtil.getCurrentUserId());

        bookingRequestDomain.setRequester(UUID.fromString(securityUtil.getCurrentUserId()));

        bookingRequestDomain.setEndDateApproval(Instant.now().plus(3, ChronoUnit.DAYS));
        bookingRequestDomain.setCreatedAt(Instant.now());
        bookingRequestDomain.setUpdatedAt(Instant.now());

        List<DateRequestException> dateExceptions = bookingRequestDomain.getDateRequestExceptions() != null ? bookingRequestDomain.getDateRequestExceptions() : Collections.EMPTY_LIST;

        log.info("check date: {}", dateExceptions);

        var result = roomSchedulerAppService.checkScheduleAndFindOptimalRoomSameRoomIdWithBranchOptionalV3(
                request.getBranchId(),
                bookingRequestDomain.getOccurrences(),
                dateExceptions,
                bookingRequestDomain.getStartTime(),
                bookingRequestDomain.getEndTime(),
                bookingRequestDomain.getCapacity(),
                bookingRequestDomain.getParticipants(),
                10
        );

        log.info("check result: {}", result.size());

        List<LocalDate> conflictedDates = result.stream()
                .filter(RoomScheduleResultDto::isHasConflict)
                .map(RoomScheduleResultDto::getDate)
                .toList();

        log.info("check conflictedDates: {}", conflictedDates);

        if (!conflictedDates.isEmpty()) {
            throw new AppException(ErrorCode.BOOKING_REQUEST_CONFLICT, result, conflictedDates);
        }


        var uniqueParticipants = bookingRequestDomain.getParticipants().stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .distinct()
                .toList();

        bookingRequestDomain.setParticipants(uniqueParticipants);

        var savedBookingRequest = bookingRequestRepository.save(bookingRequestDomain);


        if (!dateExceptions.isEmpty()) {
            dateExceptions.forEach(date -> date.setBookingRequestId(savedBookingRequest.getId()));

            dateRequestExceptionRepository.saveAll(dateExceptions);
            savedBookingRequest.setDateRequestExceptions(dateExceptions);

            log.info("log date: {}", savedBookingRequest);
//            roomCheckingCacheService.pushPendingBookingRequestToRedis();

        }


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


        log.info("services: {}", servicesDomain.size());

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

        log.info("equipments: {}", equipmentsDomain.size());

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
        var dateRequestExceptions = bookingRequestDomain.getDateRequestExceptions();

        var result = roomSchedulerAppService.checkScheduleAndFindOptimalRoomSameRoomIdWithBranchOptionalV3(
                branchId,
                listOccurrences,
                dateRequestExceptions,
                bookingRequestDomain.getStartTime(),
                bookingRequestDomain.getEndTime(),
                bookingRequestDomain.getCapacity(),
                bookingRequestDomain.getParticipants(),
                10
        );

        return result;
    }


    @PreAuthorize("@roleEvaluator.hasAnyRoleType('approve')")
    @Transactional
    public Object approveBooking(String bookingRequestId) {
        var approvalFormDomain = approvalFormRepository
                .findByBookingRequestIdLastStatusWithBookingRequest(bookingRequestId)
                .orElseThrow(() -> new AppException(ErrorCode.APPROVAL_FORM_NOT_FOUND));

        if (ApprovalStatusType.getListCantApproval().contains(approvalFormDomain.getStatus())) {
            throw new AppException(ErrorCode.BOOKING_APPROVE_CONFLICT, bookingRequestId);
        }

        String branchId = (approvalFormDomain.getBookingRequest().getBranchId() != null)
                ? approvalFormDomain.getBookingRequest().getBranchId().toString()
                : null;

        var bookingRequestDomain = approvalFormDomain.getBookingRequest();
        var listOccurrences = bookingRequestDomain.getOccurrences();
        var dateRequestExceptions = bookingRequestDomain.getDateRequestExceptions();

        log.info("dulieu: {}", dateRequestExceptions);
        var result = roomSchedulerAppService.checkScheduleAndFindOptimalRoomSameRoomIdWithBranchOptionalV3(
                branchId,
                listOccurrences,
                dateRequestExceptions,
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

            // Map để tra nhanh thời gian ghi đè
            var exceptionMap = dateRequestExceptions.stream()
                    .collect(Collectors.toMap(DateRequestException::getDate, e -> e));

            result.forEach(occurrence -> {
                log.info("room id {}", occurrence.getOptimalRoomId());
                var roomDomain = Room.builder().id(UUID.fromString(occurrence.getOptimalRoomId())).build();
                var totalPrice = roomRepository
                        .findPriceByIdAndValidTimestamp(
                                occurrence.getOptimalRoomId(),
                                bookingRequestDomain.getCreatedAt())
                        .orElse(BigDecimal.ZERO);
                var bookingDomainId = UUID.randomUUID();

                // Mặc định lấy thời gian từ booking request
                LocalTime meetingStart = bookingRequestDomain.getStartTime();
                LocalTime meetingEnd = bookingRequestDomain.getEndTime();

                // Ghi đè thời gian nếu có exception
                if (exceptionMap.containsKey(occurrence.getDate())) {
                    var exception = exceptionMap.get(occurrence.getDate());
                    if (exception.getStartTime() != null && exception.getEndTime() != null) {
                        meetingStart = exception.getStartTime();
                        meetingEnd = exception.getEndTime();
                        log.info("Override meeting time on {}: {} - {}", occurrence.getDate(), meetingStart, meetingEnd);
                    }
                }

                var bookingDomain = Booking.builder()
                        .id(bookingDomainId)
                        .bookingRequest(bookingRequestDomain)
                        .room(roomDomain)
                        .bookingCode(generateBookingCode(occurrence.getDate()))
                        .meetingStart(meetingStart)
                        .meetingEnd(meetingEnd)
                        .meetingDate(occurrence.getDate())
                        .totalPrice(totalPrice)
                        .status(BookingStatusType.SCHEDULED.toString())
                        .count(1)
                        .build();
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


    public Page<BookingResponse> filterSearchPageBookingAdmin(
            BookingFilterRequest filter,
            int page,
            int size,
            String sortBy,
            String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        if (size <= 0) {
            size = Integer.MAX_VALUE;
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        var bookingFilter = BookingFilter.builder()
                .keyword(filter.keyword())
                .searchBy(filter.searchBy())
                .roomId(filter.roomId())
                .fromTime(filter.fromTime())
                .toTime(filter.toTime())
                .fromMeetingDate(filter.fromMeetingDate())
                .toMeetingDate(filter.toMeetingDate())
                .fromTotalPrice(filter.fromTotalPrice())
                .toTotalPrice(filter.toTotalPrice())
                .status(filter.status())
                .fromTimestamp(filter.fromTimestamp())
                .toTimestamp(filter.toTimestamp())
                .build();

        var bookingDomainPage = bookingEntityService.filterSearchPageBooking(bookingFilter, pageable);

        return bookingDomainPage.map(bookingAppMapper::toResponse);
    }

    public Page<BookingMiniumResponse> getListPageBooking(
            BookingListRequest request,
            Integer page,
            Integer size,
            String sortBy,
            String direction) {
        if (size == -1) {
            size = Integer.MAX_VALUE;
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        LocalDate today = LocalDate.now();

        String status = (request.getStatus() != null && BookingStatusType.getList().contains(request.getStatus().toUpperCase())) ? request.getStatus().toUpperCase() : null;

        int currentYear = today.getYear();
        int selectedYear = (request.getYear() != null && request.getYear() > 0)
                ? request.getYear()
                : currentYear;

        int selectedMonth = (request.getMonth() != null && request.getMonth() >= 1 && request.getMonth() <= 12)
                ? request.getMonth()
                : today.getMonthValue();

        LocalDate startDate = LocalDate.of(selectedYear, selectedMonth, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        String currentUserId = securityUtil.getCurrentUserId();
        log.info("Date range: {} -> {} | Instant range: {} -> {}", startDate, endDate, startDate, endDate);

        Page<BookingMiniumResponse> bookingDomain;

        if (!roleEvaluator.hasAnyRoleType("approve") && (!request.isAdmin())) {
            bookingDomain = bookingEntityService.findBookingsByTimeRangeAndUserIdAndStatus(startDate, endDate, currentUserId, status, pageable);
        } else {
            bookingDomain = bookingEntityService.findBookingsByTimeRangeAndStatus(startDate, endDate, status, pageable);
        }


        if (bookingDomain == null) {
            return Page.empty(pageable);
        }

        return bookingDomain;
    }

    public Page<BookingRequestResponse> getListPageBookingRequestAdminApproval(
            BookingRequestApprovalRequest request,
            int page,
            int size,
            String sortBy,
            String direction) {

        if (size == -1) {
            size = Integer.MAX_VALUE;
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        LocalDate today = LocalDate.now();

        int currentYear = today.getYear();
        int selectedYear = (request.getYear() != null && request.getYear() > 0)
                ? request.getYear()
                : currentYear;

        int selectedMonth = (request.getMonth() != null && request.getMonth() >= 1 && request.getMonth() <= 12)
                ? request.getMonth()
                : today.getMonthValue();

        List<String> statusList = (request.getStatus() != null ? Arrays.stream(request.getStatus().split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .filter(s -> ApprovalStatusType.getList().contains(s))
                .collect(Collectors.toList()) : List.of());

        Boolean isAdmin = request.getIsAdmin() ? request.getIsAdmin() : false;

        LocalDate startDate = LocalDate.of(selectedYear, selectedMonth, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        ZoneId zoneId = ZoneId.systemDefault();
        Instant startInstant = startDate.atStartOfDay(zoneId).toInstant();
        Instant endInstant = endDate.atTime(LocalTime.MAX).atZone(zoneId).toInstant();

        log.info("Date range: {} -> {} | Instant range: {} -> {}", startDate, endDate, startInstant, endInstant);

        Page<ApprovalForm> approvalFormPage;

        if (!roleEvaluator.hasAnyRoleType("approve")) {
            if (isAdmin) {
                approvalFormPage = approvalFormEntityService.findAllByStatusAndTimeRangeWithBookingRequest(
                        statusList, startInstant, endInstant, null, pageable);
            } else {
                approvalFormPage = approvalFormEntityService.findAllByStatusAndTimeRangeWithBookingRequest(
                        statusList, startInstant, endInstant, securityUtil.getCurrentUserId(), pageable);
            }

        } else {
            approvalFormPage = approvalFormEntityService.findAllByStatusAndTimeRangeWithBookingRequest(
                    statusList, startInstant, endInstant, securityUtil.getCurrentUserId(), pageable);
        }

        return approvalFormPage.map(approvalForm ->
                bookingRequestAppMapper.toResponseFromApprovalForm(
                        approvalForm.getBookingRequest(), approvalForm));
    }


    public BookingDetailResponse getDetailBooking(String bookingId) {
        var bookingDomain = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOTFOUND, bookingId));

        var bookingDetailResponse = bookingAppMapper.toResponseDetail(bookingDomain);

        var requesterDomain = userRepository
                .findByIdAll(bookingDomain.getBookingRequest().getRequester().toString())
                .orElse(null);

        bookingDetailResponse.setRequester(userAppMapper.toResponse(requesterDomain));

        var participantsBooking = bookingParticipantRepository
                .findAllBookingId(bookingDomain.getId().toString())
                .stream().map(bookingParticitipantAppMapper::toResponse)
                .toList();
        var servicesBooking = bookingServiceRepository
                .findAllByBookingId(bookingDomain.getId().toString())
                .stream().map(BookingService::getService)
                .map(serviceAppMapper::toResponse)
                .toList();

        var equipmentsBooking = bookingEquipmentRepository
                .findAllByBookingId(bookingDomain.getId().toString())
                .stream().map(BookingEquipment::getEquipment)
                .map(equipmentAppMapper::toResponse)
                .toList();
        bookingDetailResponse.setEquipments(equipmentsBooking);
        bookingDetailResponse.setServices(servicesBooking);
        bookingDetailResponse.setParticipants(participantsBooking);
        return bookingDetailResponse;
    }



    /*public Page<BookingResponse> getListBooking(
            BookingRequestApprovalRequest request,
            int page,
            int size,
            String sortBy,
            String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        LocalDate today = LocalDate.now();

        int currentYear = today.getYear();
        int selectedYear = (request.getYear() != null && request.getYear() > 0) ? request.getYear() : currentYear;
        int selectedMonth = (request.getMonth() != null && request.getMonth() >= 1 && request.getMonth() <= 12) ? request.getMonth() : today.getMonthValue();

        LocalDate startDate = LocalDate.of(selectedYear, selectedMonth, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        ZoneId zoneId = ZoneId.systemDefault();
        Instant startInstant = startDate.atStartOfDay(zoneId).toInstant();
        Instant endInstant = endDate.atTime(LocalTime.MAX).atZone(zoneId).toInstant();

        log.info("Date range: {} -> {} | Instant range: {} -> {}", startDate, endDate, startInstant, endInstant);

        Page<ApprovalForm> approvalFormPage = null;
        if(!roleEvaluator.hasRole(RoleType.USER.toString())){
            approvalFormPage = approvalFormEntityService.findAllByLastStatusInAndTimeRangeWithBookingRequest(
                    ApprovalStatusType.getListCanApproval(), startInstant, endInstant, pageable);

        } else {
            approvalFormPage = approvalFormEntityService.findAllByLastStatusInAndTimeRangeAndRequesterWithBookingRequest(
                    ApprovalStatusType.getListCanApproval(),startInstant, endInstant, securityUtil.getCurrentUserId(), pageable);
        }
        Page<Booking> bookingDomainPage = bookingEntityService.filterSearchPageBookingWithUser(bookingFilter, pageable);


        return approvalFormPage.map(approvalForm ->
                bookingRequestAppMapper.toResponseFromApprovalForm(approvalForm.getBookingRequest(), approvalForm)
        );
    }*/


    public List<BookingRequestResponse> suggestApprovalOrder() {
        List<BookingRequest> pendingRequests = approvalFormRepository
                .findAllBookingRequestWithInStatus(ApprovalStatusType.getListCanApproval())
                .stream().map(ApprovalForm::getBookingRequest)
                .toList();
        var result = conflictBookingResolutionDomainService
                .suggestApprovalOrder(pendingRequests)
                .stream().map(bookingRequestAppMapper::toResponse).toList();

        return result;
    }


    private String generateBookingCode(LocalDate meetingDate) {
        return meetingDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + Long.toString(System.nanoTime(), 36).toUpperCase();
    }


    public BookingRequestDetailResponse getDetailBookingRequest(String bookingRequestId) {
        var bookingRequestDomain = bookingRequestRepository.findById(bookingRequestId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_REQUEST_NOT_FOUND, null, bookingRequestId));

        var bookingRequestDetailResponse = bookingRequestAppMapper.toResponseDetail(bookingRequestDomain);

        var approvalFormDomain = approvalFormRepository.findByBookingRequestIdLastStatus(bookingRequestId).orElse(null);
        if (approvalFormDomain != null) {
            bookingRequestDetailResponse.setApprovalStatus(approvalFormDomain.getStatus());
            bookingRequestDomain.setCreatedAt(approvalFormDomain.getCreatedAt());
            bookingRequestDomain.setUpdatedAt(approvalFormDomain.getUpdatedAt());
        }

        var requesterDomain = userRepository
                .findByIdAll(bookingRequestDomain.getRequester().toString())
                .orElse(null);

        bookingRequestDetailResponse.setRequester(userAppMapper.toResponse(requesterDomain));


        var servicesBooking = serviceRequestRepository
                .findAllByBookingRequestId(bookingRequestDomain.getId().toString())
                .stream()
                .map(serviceRequestAppMapper::toResponse)
                .toList();

        var equipmentsBooking = equipmentRequestRepository
                .findAllByBookingRequestId(bookingRequestDomain.getId().toString())
                .stream()
                .map(equipmentRequestAppMapper::toResponse)
                .toList();

        bookingRequestDetailResponse.setBranch(
                placeRepository.findById(bookingRequestDomain.getBranchId().toString())
                        .map(placeAppMapper::toResponse)
                        .orElse(null)
        );

        bookingRequestDetailResponse.setRoom(
                Optional.ofNullable(bookingRequestDomain.getRoomId())
                        .flatMap(roomId -> roomRepository.findById(roomId.toString()))
                        .map(roomAppMapper::toResponse)
                        .orElse(null)
        );

        bookingRequestDetailResponse.setEquipments(equipmentsBooking);
        bookingRequestDetailResponse.setServices(servicesBooking);
        return bookingRequestDetailResponse;
    }
}
