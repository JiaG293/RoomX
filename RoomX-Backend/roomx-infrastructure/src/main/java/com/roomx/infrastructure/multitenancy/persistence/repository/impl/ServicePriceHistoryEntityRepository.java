package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.entity.ServicePriceHistory;
import com.roomx.domain.repository.ServicePriceHistoryRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.ServicePriceHistoryEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaServicePriceHistoryEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ServicePriceHistoryEntityRepository implements ServicePriceHistoryRepository {
    private final JpaServicePriceHistoryEntityRepository jpaServicePriceHistoryEntityRepository;
    private final ServicePriceHistoryEntityMapper servicePriceHistoryEntityMapper;

    @Override
    public Optional<ServicePriceHistory> findById(String id) {
        return jpaServicePriceHistoryEntityRepository
                .findById(UUID.fromString(id))
                .map(servicePriceHistoryEntityMapper::toDomain);
    }

    @Override
    public ServicePriceHistory save(ServicePriceHistory servicePriceHistory) {
        var servicePriceHistoryEntity = servicePriceHistoryEntityMapper.toEntity(servicePriceHistory);
        var savedServicePriceHistoryEntity = jpaServicePriceHistoryEntityRepository.save(servicePriceHistoryEntity);
        return servicePriceHistoryEntityMapper.toDomain(savedServicePriceHistoryEntity);
    }

    @Override
    public Optional<ServicePriceHistory> findLatestValidFrom(String serviceId) {
        return jpaServicePriceHistoryEntityRepository
                .findLatestValidFrom(UUID.fromString(serviceId))
                .map(servicePriceHistoryEntityMapper::toDomain);
    }
}
