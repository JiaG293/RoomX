package com.roomx.infrastructure.multitenancy.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.shared.base.filter.BranchFilter;
import com.roomx.infrastructure.multitenancy.persistence.mapper.BranchEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaBranchEntityRepository;
import com.roomx.infrastructure.multitenancy.persistence.repository.specification.BranchSpecification;
import com.roomx.infrastructure.multitenancy.persistence.service.BranchEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchEntityServiceImpl implements BranchEntityService {
    private final JpaBranchEntityRepository jpaBranchEntityRepository;
    private final BranchEntityMapper branchEntityMapper;


    @Override
    public Page<Branch> filterPageBranchs(BranchFilter filter, Pageable pageable, boolean typeCompare) {
        // use filter search like, AND
//        String method = "%";
//        boolean condition = typeCompare; // TRUE = OR || FALSE = AND
//        List<SearchCriteria> filters = Stream.of(
//                        new AbstractMap.SimpleEntry<>("name", filter.getName()),
//                        new AbstractMap.SimpleEntry<>("branchCode", filter.getBranchCode()),
//                        new AbstractMap.SimpleEntry<>("address", filter.getAddress()),
//                        new AbstractMap.SimpleEntry<>("phoneNumber", filter.getPhoneNumber()),
//                        new AbstractMap.SimpleEntry<>("email", filter.getEmail())
//                ).filter(entry -> entry.getValue() != null && !entry.getValue().toString().isBlank())
//                .map(entry -> new SearchCriteria(entry.getKey(), entry.getValue(), method, condition))
//                .toList();
//
//        log.info("Searching data: {}", filters);
//
//        Specification<BranchEntity> spec = new GenericSpecification<>(filters);

//        var branchEntityPage = jpaBranchEntityRepository.findAll(spec, pageable);


//        return branchEntityPage.map(branchEntityMapper::toDomain);
        return (Page<Branch>) new Branch();
    }

    @Override
    public Page<Branch> searchPageBranchByKeyword(String keyword, Pageable pageable) {
        return jpaBranchEntityRepository
                .searchPageBranchByKeyword(keyword, pageable)
                .map(branchEntityMapper::toDomain);
    }

    @Override
    public Page<Branch> searchFilterBranch(BranchFilter filter, Pageable pageable) {
        var spec = BranchSpecification.searchFilterBranch(filter);
        return jpaBranchEntityRepository.findAll(spec, pageable)
                .map(branchEntityMapper::toDomain);
    }


}
