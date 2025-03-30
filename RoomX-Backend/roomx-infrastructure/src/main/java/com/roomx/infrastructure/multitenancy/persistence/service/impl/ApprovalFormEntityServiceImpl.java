package com.roomx.infrastructure.multitenancy.persistence.service.impl;

import com.roomx.domain.model.aggrerate.ApprovalForm;
import com.roomx.infrastructure.multitenancy.persistence.mapper.ApprovalFormEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaApprovalFormEntityRepository;
import com.roomx.infrastructure.multitenancy.persistence.service.ApprovalFormEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ApprovalFormEntityServiceImpl implements ApprovalFormEntityService {

    private final JpaApprovalFormEntityRepository jpaApprovalFormEntityRepository;
    private final ApprovalFormEntityMapper approvalFormEntityMapper;

    @Override
    public Page<ApprovalForm> findAllByLastStatusInAndTimeRangeWithBookingRequest(List<String> listStatusCanApproval, Instant startDate, Instant endDate, Pageable pageable) {
        return
                jpaApprovalFormEntityRepository.findAllByStatusInAndUpdatedAtIsBetween(
                        listStatusCanApproval,
                        startDate,
                        endDate,
                        pageable).map(approvalFormEntityMapper::toDomain);
    }
}
