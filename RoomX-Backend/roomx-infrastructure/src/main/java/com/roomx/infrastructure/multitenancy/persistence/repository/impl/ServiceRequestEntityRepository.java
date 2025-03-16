package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.entity.ServiceRequest;
import com.roomx.domain.model.vo.ServiceRequestId;
import com.roomx.domain.repository.ServiceRequestRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.ServiceRequestEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.mapper.ServiceRequestEntityIdMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaServiceRequestEntityRepository;
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
public class ServiceRequestEntityRepository implements ServiceRequestRepository {
    private final JpaServiceRequestEntityRepository jpaServiceRequestEntityRepository;
    private final ServiceRequestEntityMapper serviceRequestEntityMapper;
    private final ServiceRequestEntityIdMapper serviceRequestEntityIdMapper;

    @Override
    public Optional<ServiceRequest> findById(ServiceRequestId serviceRequestId) {
        var serviceRequestEntityId = serviceRequestEntityIdMapper.toEntity(serviceRequestId);

        return jpaServiceRequestEntityRepository.findById(serviceRequestEntityId)
                .map(serviceRequestEntityMapper::toDomain);
    }

    @Override
    public Optional<ServiceRequest> findByServiceId(String serviceId) {
        return jpaServiceRequestEntityRepository
                .findByServiceId(UUID.fromString(serviceId))
                .map(serviceRequestEntityMapper::toDomain);
    }

    @Override
    public Optional<ServiceRequest> findByBookingRequestId(String bookingRequestId) {
        return jpaServiceRequestEntityRepository
                .findByBookingRequestId(UUID.fromString(bookingRequestId))
                .map(serviceRequestEntityMapper::toDomain);
    }

    @Override
    public ServiceRequest save(ServiceRequest serviceRequest) {
        var serviceRequestEntity = serviceRequestEntityMapper.toEntity(serviceRequest);
        var savedServiceRequestEntity = jpaServiceRequestEntityRepository.save(serviceRequestEntity);
        return serviceRequestEntityMapper.toDomain(savedServiceRequestEntity);
    }

    @Override
    public List<ServiceRequest> saveAll(List<ServiceRequest> listServiceRequset) {
        var serviceRequestEntityList = listServiceRequset.stream()
                .map(serviceRequestEntityMapper::toEntity)
                .collect(Collectors.toList());

        var savedServiceRequestEntityList = jpaServiceRequestEntityRepository
                .saveAll(serviceRequestEntityList);

        return savedServiceRequestEntityList
                .stream().map(serviceRequestEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
