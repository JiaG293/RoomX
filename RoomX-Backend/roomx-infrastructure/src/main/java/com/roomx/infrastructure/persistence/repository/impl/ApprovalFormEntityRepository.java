package com.roomx.infrastructure.persistence.repository.impl;


import com.roomx.domain.model.aggrerate.ApprovalForm;
import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.repository.ApprovalFormRepository;
import com.roomx.infrastructure.persistence.mapper.ApprovalFormEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaApprovalFormEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ApprovalFormEntityRepository implements ApprovalFormRepository {
    private final JpaApprovalFormEntityRepository jpaApprovalFormEntityRepository;
    private final ApprovalFormEntityMapper approvalFormEntityMapper;


    @Override
    public Optional<ApprovalForm> findById(String id) {
        return jpaApprovalFormEntityRepository
                .findById(UUID.fromString(id))
                .map(approvalFormEntityMapper::toDomain);
    }

    @Override
    public ApprovalForm save(ApprovalForm approvalForm) {
        var approvalFormEntity = approvalFormEntityMapper.toEntity(approvalForm);
        var savedApprovalFormEntity = jpaApprovalFormEntityRepository.save(approvalFormEntity);
        return approvalFormEntityMapper.toDomain(savedApprovalFormEntity);
    }

    @Override
    public Optional<ApprovalForm> findByBookingRequestIdAndLastStatusWithBookingRequest(String bookingRequestId, String status) {
        return jpaApprovalFormEntityRepository
                .findByBookingRequestIdAndStatusOrderByUpdatedAtDesc(UUID.fromString(bookingRequestId), status)
                .map(approvalFormEntityMapper::toDomain);
    }

    @Override
    public Optional<ApprovalForm> findByBookingRequestIdLastStatusWithBookingRequest(String bookingRequestId) {
        return jpaApprovalFormEntityRepository
                .findFirstByBookingRequestIdOrderByUpdatedAtDesc(UUID.fromString(bookingRequestId))
                .map(approvalFormEntityMapper::toDomain);
    }

    @Override
    public List<ApprovalForm> findAllBookingRequestWithInStatus(List<String> status) {
        return jpaApprovalFormEntityRepository
                .findAllByStatusIn(status)
                .stream().map(approvalFormEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<ApprovalForm> findByBookingRequestIdLastStatus(String bookingRequestId) {
        return jpaApprovalFormEntityRepository
                .findTopByBookingRequestIdOrderByUpdatedAtDesc(UUID.fromString(bookingRequestId))
                .map(approvalFormEntityMapper::toDomainMin);
    }

    @Override
    public List<ApprovalForm> findAllByStatusAndTimeRangeWithBookingRequest(List<String> listStatus, Instant startDate, Instant endDate, String requester) {
        return jpaApprovalFormEntityRepository
                .findAllByListStatusAndTimeRangeAndRequesterNoPage(
                        listStatus,
                        startDate,
                        endDate,
                        requester
                ).stream().map(data -> ApprovalForm.builder()
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
                ).toList();
    }


}
