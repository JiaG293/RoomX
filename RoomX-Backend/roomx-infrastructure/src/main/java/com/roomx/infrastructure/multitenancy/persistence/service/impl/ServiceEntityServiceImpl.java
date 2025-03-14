package com.roomx.infrastructure.multitenancy.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Branch;
import com.roomx.domain.model.aggrerate.Service;
import com.roomx.infrastructure.multitenancy.persistence.dto.BranchFilter;
import com.roomx.infrastructure.multitenancy.persistence.dto.ServiceFilter;
import com.roomx.infrastructure.multitenancy.persistence.mapper.BranchEntityJpaMapper;
import com.roomx.infrastructure.multitenancy.persistence.mapper.ServiceEntityJpaMapper;
import com.roomx.infrastructure.multitenancy.persistence.model.base.GenericSpecification;
import com.roomx.infrastructure.multitenancy.persistence.model.base.SearchCriteria;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.BranchEntity;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.ServiceEntity;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaBranchEntityRepository;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaServiceEntityRepository;
import com.roomx.infrastructure.multitenancy.persistence.service.ServiceEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceEntityServiceImpl implements ServiceEntityService {
    private final JpaServiceEntityRepository jpaServiceEntityRepository;
    private final ServiceEntityJpaMapper serviceEntityJpaMapper;

    @Override
    public Page<Service> filterPageServices(ServiceFilter filter, Pageable pageable, boolean typeCompare) {

        // use filter search like, AND
        String method = "%";
        boolean condition = typeCompare; // TRUE = OR || FALSE = AND
        List<SearchCriteria> filters = Stream.of(
                        new AbstractMap.SimpleEntry<>("name", filter.getName()),
                        new AbstractMap.SimpleEntry<>("description", filter.getDescription()),
                        new AbstractMap.SimpleEntry<>("note", filter.getNote()),
                        new AbstractMap.SimpleEntry<>("unitPrice", filter.getUnitPrice()),
                        new AbstractMap.SimpleEntry<>("createdAt", filter.getCreatedAt()),
                        new AbstractMap.SimpleEntry<>("updatedAt", filter.getUpdatedAt())
                ).filter(entry -> entry.getValue() != null && !entry.getValue().toString().isBlank())
                .map(entry -> new SearchCriteria(entry.getKey(), entry.getValue(), method, condition))
                .toList();

        log.info("Searching data: {}", filters);

        Specification<ServiceEntity> spec = new GenericSpecification<>(filters);

        var serviceEntityPage = jpaServiceEntityRepository.findAll(spec, pageable);


        return serviceEntityPage.map(serviceEntityJpaMapper::toDomain);
    }


}
