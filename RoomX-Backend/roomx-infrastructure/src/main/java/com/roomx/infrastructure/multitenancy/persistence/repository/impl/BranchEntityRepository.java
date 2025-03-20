package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.domain.repository.BranchRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.BranchEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaBranchEntityRepository;
import com.roomx.shared.enums.DeleteStatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BranchEntityRepository implements BranchRepository {
    private final JpaBranchEntityRepository jpaBranchEntityRepository;
    private final BranchEntityMapper branchEntityMapper;

    @Override
    public Optional<Branch> findById(String id) {
        return jpaBranchEntityRepository
                .findByIdAndStatus(UUID.fromString(id), DeleteStatusType.getDefaultString())
                .map(branchEntityMapper::toDomain);
    }

    @Override
    public List<Branch> searchBranchByNameOrBranchCode(String branchName, String branchCode) {
        return jpaBranchEntityRepository
                .findAllByNameOrBranchCode(branchName, branchCode)
                .stream()
                .map(branchEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Branch> findByBranchCode(String branchCode) {
        return Optional.empty();
    }

    @Override
    public boolean checkBranchCodeExists(String branchCode) {
        return jpaBranchEntityRepository.existsByBranchCodeAndStatus(branchCode, DeleteStatusType.getDefaultString());
    }

    @Override
    public Branch save(Branch branch) {
        var branchEntity = branchEntityMapper.toEntity(branch);
        var savedBranchEntity = jpaBranchEntityRepository.save(branchEntity);
        return branchEntityMapper.toDomain(savedBranchEntity);
    }

    @Override
    public void delete(Branch branch) {
        jpaBranchEntityRepository.delete(branchEntityMapper.toEntity(branch));
    }

    @Override
    public void deleteById(String id) {
        jpaBranchEntityRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public List<Branch> findAll() {
        return jpaBranchEntityRepository.findAll()
                .stream().map(branchEntityMapper::toDomain)
                .toList();
    }
}
