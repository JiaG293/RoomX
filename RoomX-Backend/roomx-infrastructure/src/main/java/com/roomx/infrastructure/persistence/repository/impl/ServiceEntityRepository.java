package com.roomx.infrastructure.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.Service;
import com.roomx.domain.repository.ServiceRepository;
import com.roomx.infrastructure.persistence.mapper.ServiceEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaServiceEntityRepository;
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
    private final ServiceEntityMapper serviceEntityMapper;


    @Override
    public Optional<Service> findById(String id) {
        return jpaServiceEntityRepository.findById(UUID.fromString(id)).map(serviceEntityMapper::toDomain);
    }

    @Override
    public Service save(Service service) {
        var serviceEntity = serviceEntityMapper.toEntity(service);
        var savedServiceEntity = jpaServiceEntityRepository.save(serviceEntity);
        return serviceEntityMapper.toDomain(savedServiceEntity);
    }

    @Override
    public void delete(Service service) {
        jpaServiceEntityRepository.delete(serviceEntityMapper.toEntity(service));
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
        return jpaServiceEntityRepository.findByName(serviceName).map(serviceEntityMapper::toDomain);
    }

    @Override
    public boolean checkServiceCodeIsExists(String serviceCode) {
        return jpaServiceEntityRepository.existsByServiceCode(serviceCode);
    }

    @Override
    public Optional<Service> findByServiceCode(String serviceCode) {
        return jpaServiceEntityRepository.findByServiceCode(serviceCode)
                .map(serviceEntityMapper::toDomain);
    }

    @Override
    public Optional<Service> findByIdAndStatus(String serviceId, String status) {
        return jpaServiceEntityRepository
                .findByIdAndStatus(UUID.fromString(serviceId), status)
                .map(serviceEntityMapper::toDomain);
    }

    @Override
    public List<Service> saveAll(List<Service> services) {
        var serviceEntityList = services.stream().map(serviceEntityMapper::toEntity).toList();
        var savedServiceEntityList = jpaServiceEntityRepository.saveAll(serviceEntityList);
        return savedServiceEntityList.stream().map(serviceEntityMapper::toDomain).toList();
    }


}
