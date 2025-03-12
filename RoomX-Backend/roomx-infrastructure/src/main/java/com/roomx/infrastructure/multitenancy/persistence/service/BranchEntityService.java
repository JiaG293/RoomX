package com.roomx.infrastructure.multitenancy.persistence.service;


import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.infrastructure.multitenancy.persistence.dto.BranchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BranchEntityService {
    public Page<Branch> filterPageBranchs(BranchFilter filter, Pageable pageable, boolean typeCompare);
}
