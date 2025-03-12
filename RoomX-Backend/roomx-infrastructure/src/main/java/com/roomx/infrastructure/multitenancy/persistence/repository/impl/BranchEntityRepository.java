package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.domain.repository.BranchRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.BranchEntityJpaMapper;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.BranchEntity;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaBranchEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BranchEntityRepository implements BranchRepository {
    private final JpaBranchEntityRepository jpaBranchEntityRepository;
    private final BranchEntityJpaMapper branchEntityJpaMapper;

    @Override
    public Optional<Branch> findById(String id) {
        return jpaBranchEntityRepository
                .findById(UUID.fromString(id))
                .map(branchEntityJpaMapper::toDomain);
    }

    @Override
    public List<Branch> searchBranchByNameOrBranchCode(String branchName, String branchCode) {
        return jpaBranchEntityRepository
                .findAllByNameOrBranchCode(branchName, branchCode)
                .stream()
                .map(branchEntityJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Branch> findByBranchCode(String branchCode) {
        return Optional.empty();
    }

    @Override
    public boolean checkBranchCodeExists(String branchCode) {
        return jpaBranchEntityRepository.existsByBranchCode(branchCode);
    }

    @Override
    public Branch save(Branch branch) {
        var branchEntity = branchEntityJpaMapper.toEntity(branch);
        var savedBranchEntity = jpaBranchEntityRepository.save(branchEntity);
        return branchEntityJpaMapper.toDomain(savedBranchEntity);
    }

    @Override
    public void delete(Branch branch) {
        jpaBranchEntityRepository.delete(branchEntityJpaMapper.toEntity(branch));
    }

    @Override
    public void deleteById(String id) {
        jpaBranchEntityRepository.deleteById(UUID.fromString(id));
    }
}
