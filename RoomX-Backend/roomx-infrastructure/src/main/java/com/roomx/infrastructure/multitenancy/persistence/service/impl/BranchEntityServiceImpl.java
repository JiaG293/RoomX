package com.roomx.infrastructure.multitenancy.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.infrastructure.multitenancy.persistence.dto.BranchFilter;
import com.roomx.infrastructure.multitenancy.persistence.mapper.BranchEntityJpaMapper;
import com.roomx.infrastructure.multitenancy.persistence.model.base.GenericSpecification;
import com.roomx.infrastructure.multitenancy.persistence.model.base.SearchCriteria;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.BranchEntity;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.UserEntity;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaBranchEntityRepository;
import com.roomx.infrastructure.multitenancy.persistence.service.BranchEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchEntityServiceImpl implements BranchEntityService {
    private final JpaBranchEntityRepository jpaBranchEntityRepository;
    private final BranchEntityJpaMapper branchEntityJpaMapper;



    @Override
    public Page<Branch> filterPageBranchs(BranchFilter filter, Pageable pageable, boolean typeCompare) {

        // use filter search like, AND
        String method = "%";
        boolean condition = typeCompare; // TRUE = OR || FALSE = AND
        List<SearchCriteria> filters = Stream.of(
                        new AbstractMap.SimpleEntry<>("name", filter.getName()),
                        new AbstractMap.SimpleEntry<>("branchCode", filter.getBranchCode()),
                        new AbstractMap.SimpleEntry<>("address", filter.getAddress()),
                        new AbstractMap.SimpleEntry<>("phoneNumber", filter.getPhoneNumber()),
                        new AbstractMap.SimpleEntry<>("email", filter.getEmail())
                ).filter(entry -> entry.getValue() != null && !entry.getValue().toString().isBlank())
                .map(entry -> new SearchCriteria(entry.getKey(), entry.getValue(), method, condition))
                .toList();

        log.info("Searching data: {}", filters);

        Specification<BranchEntity> spec = new GenericSpecification<>(filters);

        var branchEntityPage = jpaBranchEntityRepository.findAll(spec, pageable);


        return branchEntityPage.map(branchEntityJpaMapper::toDomain);
    }
}
