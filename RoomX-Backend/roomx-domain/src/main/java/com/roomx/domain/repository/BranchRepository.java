package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchRepository {
    Optional<Branch> findById(String id);
    List<Branch> searchBranchByNameOrBranchCode(String branchName, String branchCode);
    Optional<Branch> findByBranchCode(String branchCode);
    boolean checkBranchCodeExists(String branchCode);
    Branch save(Branch branch);
    void delete(Branch branch);
    void deleteById(String id);
}
