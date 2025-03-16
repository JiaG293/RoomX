package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.ApprovalFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaApprovalFormEntityRepository extends JpaRepository<ApprovalFormEntity, UUID>, JpaSpecificationExecutor<ApprovalFormEntity> {
}
