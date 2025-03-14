package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.Service;
import com.roomx.domain.repository.ServiceRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.ServiceEntityJpaMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaServiceEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ServiceEntityRepository implements ServiceRepository {
    private final JpaServiceEntityRepository jpaServiceEntityRepository;
    private final ServiceEntityJpaMapper serviceEntityJpaMapper;


    @Override
    public Optional<Service> findById(String id) {
        return jpaServiceEntityRepository.findById(UUID.fromString(id)).map(serviceEntityJpaMapper::toDomain);
    }

    @Override
    public Service save(Service service) {
        var serviceEntity = serviceEntityJpaMapper.toEntity(service);
        var savedServiceEntity = jpaServiceEntityRepository.save(serviceEntity);
        return serviceEntityJpaMapper.toDomain(savedServiceEntity);
    }

    @Override
    public void delete(Service service) {
        jpaServiceEntityRepository.delete(serviceEntityJpaMapper.toEntity(service));
    }

    @Override
    public void deleteById(String id) {
        jpaServiceEntityRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public boolean checkServiceNameExists(String serviceName) {
        return jpaServiceEntityRepository.existsByName(serviceName);
    }

    @Override
    public Optional<Service> findByServiceName(String serviceName) {
        return jpaServiceEntityRepository.findByName(serviceName).map(serviceEntityJpaMapper::toDomain);
    }


}
