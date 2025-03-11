package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.Branch;

import java.util.Optional;

public interface BranchRepository {
    Optional<Branch> findById(String id);
    Optional<Branch> findByName(String name);
    void save(Branch branch);
    void delete(Branch branch);
    void deleteById(String id);
}
