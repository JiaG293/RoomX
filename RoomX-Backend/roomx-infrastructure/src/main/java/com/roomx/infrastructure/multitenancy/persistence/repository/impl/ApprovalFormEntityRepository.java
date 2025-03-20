package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.ApprovalForm;
import com.roomx.domain.repository.ApprovalFormRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.ApprovalFormEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaApprovalFormEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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
}
