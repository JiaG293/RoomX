package com.roomx.infrastructure.multitenancy.persistence.service;


import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.shared.base.filter.BranchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BranchEntityService {
    Page<Branch> filterPageBranchs(BranchFilter filter, Pageable pageable, boolean typeCompare);
    Page<Branch> searchPageBranchByKeyword(String keyword, Pageable pageable);
    Page<Branch> searchFilterBranch(BranchFilter filter, Pageable pageable);
}
