package com.roomx.infrastructure.persistence.service.impl;

import com.roomx.domain.model.aggrerate.ApprovalForm;
import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.infrastructure.persistence.mapper.ApprovalFormEntityMapper;
import com.roomx.infrastructure.persistence.model.projection.ApprovalFormProjection;
import com.roomx.infrastructure.persistence.model.projection.BookingRequestFlatProjection;
import com.roomx.infrastructure.persistence.repository.jpa.JpaApprovalFormEntityRepository;
import com.roomx.infrastructure.persistence.service.ApprovalFormEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ApprovalFormEntityServiceImpl implements ApprovalFormEntityService {

    private final JpaApprovalFormEntityRepository jpaApprovalFormEntityRepository;
    private final ApprovalFormEntityMapper approvalFormEntityMapper;

    @Override
    public Page<BookingRequestFlatProjection> findAllByLastStatusAndDateRange(List<String> listStatusCanApproval, Instant startDate, Instant endDate, Pageable pageable) {
        return
                jpaApprovalFormEntityRepository.findBookingRequestApprovalsNative(
                        listStatusCanApproval,
                        startDate,
                        endDate,
                        pageable);
    }

    @Override
    public Page<ApprovalForm> findAllByLastStatusInAndTimeRangeWithBookingRequest(List<String> listStatusCanApproval, Instant startDate, Instant endDate, Pageable pageable) {
        return
                jpaApprovalFormEntityRepository.findAllByStatusInAndUpdatedAtIsBetween(
                        listStatusCanApproval,
                        startDate,
                        endDate,
                        pageable).map(approvalFormEntityMapper::toDomain);
    }

    @Override
    public Page<ApprovalForm> findAllByLastStatusInAndTimeRangeAndRequesterWithBookingRequest(List<String> listStatusCanApproval, Instant startDate, Instant endDate, String requester, Pageable pageable) {
        return
                jpaApprovalFormEntityRepository.findAllByStatusInAndBookingRequestRequesterAndUpdatedAtIsBetween(
                        listStatusCanApproval,
                        UUID.fromString(requester),
                        startDate,
                        endDate,
                        pageable).map(approvalFormEntityMapper::toDomain);
    }


    @Override
    public Page<ApprovalForm> findAllByLastStatusInAndTimeRangeAndStatusWithBookingRequest(List<String> listStatusCanApproval, Instant startDate, Instant endDate, String status, Pageable pageable) {
        return
                jpaApprovalFormEntityRepository.findAllByStatusInAndUpdatedAtIsBetweenAndStatus(
                        listStatusCanApproval,
                        startDate,
                        endDate,
                        status,
                        pageable).map(approvalFormEntityMapper::toDomain);
    }

    @Override
    public Page<ApprovalForm> findAllByLastStatusInAndTimeRangeAndRequesterAndStatusWithBookingRequest(List<String> listStatusCanApproval, Instant startDate, Instant endDate, String requester, String status, Pageable pageable) {
        return
                jpaApprovalFormEntityRepository.findAllByStatusInAndBookingRequestRequesterAndUpdatedAtIsBetweenAndStatus(
                        listStatusCanApproval,
                        UUID.fromString(requester),
                        startDate,
                        endDate,
                        status,
                        pageable).map(approvalFormEntityMapper::toDomain);
    }


    @Override
    public Page<ApprovalForm> findAllByStatusAndTimeRangeWithBookingRequest(List<String> listStatus, Instant startDate, Instant endDate, String requester, Pageable pageable) {
        return
                jpaApprovalFormEntityRepository.findAllByListStatusAndTimeRangeWithBookingRequest(
                                listStatus,
                                startDate,
                                endDate,
                                requester,
                                pageable)
                        .map(data -> ApprovalForm.builder()
                                .approver(data.getApprover())
                                .status(data.getStatus())
                                .createdAt(data.getCreatedAt())
                                .updatedAt(data.getUpdatedAt())
                                .bookingRequest(
                                        BookingRequest.builder()
                                                .id(data.getBookingRequestId())
                                                .priority(data.getPriority())
                                                .daysOfWeek(data.getDaysOfWeek())
                                                .startTime(data.getStartTime())
                                                .approvalStatus(data.getStatus())
                                                .endTime(data.getEndTime())
                                                .endDate(data.getEndDate())
                                                .startDate(data.getStartDate())
                                                .recurrenceInterval(data.getRecurrenceInterval())
                                                .recurrenceType(data.getRecurrenceType())
                                                .capacity(data.getCapacity())
                                                .requester(data.getRequester())
                                                .branchId(data.getBranchId())
                                                .roomId(data.getRoomId())
                                                .title(data.getTitle())
                                                .description(data.getDescription())
                                                .build()
                                )
                                .build()
                        );
    }

}
