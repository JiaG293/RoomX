package com.roomx.infrastructure.persistence.service.impl;

import com.roomx.domain.model.entity.ServicePriceHistory;
import com.roomx.infrastructure.persistence.dto.ServiceFilter;
import com.roomx.infrastructure.persistence.mapper.ServicePriceHistoryEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaServicePriceHistoryEntityRepository;
import com.roomx.infrastructure.persistence.service.ServicePriceHistoryEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicePriceHistoryServiceImpl implements ServicePriceHistoryEntityService {
    private final JpaServicePriceHistoryEntityRepository jpaServicePriceHistoryEntityRepository;
    private final ServicePriceHistoryEntityMapper servicePriceHistoryEntityMapper;

    @Override
    public Page<ServicePriceHistory> searchFilterService(ServiceFilter filter, Pageable pageable) {
//        var spec = ServiceSpecification.searchFilterService(filter);
//        return jpaServicePriceHistoryEntityRepository.findAll(spec, pageable)
//                .map(servicePriceHistoryEntityMapper::toDomain);
        return null;
    }
}
