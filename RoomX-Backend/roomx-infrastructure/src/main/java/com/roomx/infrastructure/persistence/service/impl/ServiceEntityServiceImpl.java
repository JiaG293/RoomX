package com.roomx.infrastructure.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Service;
import com.roomx.infrastructure.persistence.dto.ServiceFilter;
import com.roomx.infrastructure.persistence.mapper.ServiceEntityMapper;

import com.roomx.infrastructure.persistence.repository.jpa.JpaServiceEntityRepository;

import com.roomx.infrastructure.persistence.repository.specification.ServiceSpecification;
import com.roomx.infrastructure.persistence.service.ServiceEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceEntityServiceImpl implements ServiceEntityService {
    private final JpaServiceEntityRepository jpaServiceEntityRepository;
    private final ServiceEntityMapper serviceEntityMapper;

    @Override
    public Page<Service> searchFilterService(ServiceFilter filter, Pageable pageable) {
        var spec = ServiceSpecification.searchFilterService(filter);
        return jpaServiceEntityRepository.findAll(spec, pageable)
                .map(serviceEntityMapper::toDomainSpec);
    }


}
