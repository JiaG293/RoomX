package com.roomx.domain.repository;

import com.roomx.domain.model.entity.ApprovalForm;

import java.util.Optional;

public interface ApprovalFormRepository {
    Optional<ApprovalForm> findById(String id);
    ApprovalForm save(ApprovalForm approvalForm);

}
