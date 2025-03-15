package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.entity.ServiceRoomClass;
import com.roomx.domain.model.vo.ServiceRoomClassId;
import com.roomx.domain.repository.ServiceRoomClassRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.ServiceRoomClassEntityIdMapper;
import com.roomx.infrastructure.multitenancy.persistence.mapper.ServiceRoomClassEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaRoomClassEntityRepository;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaServiceRoomClassEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ServiceRoomClassEntityRepository implements ServiceRoomClassRepository {
    private final JpaServiceRoomClassEntityRepository jpaServiceRoomClassEntityRepository;
    private final ServiceRoomClassEntityMapper serviceRoomClassEntityMapper;
    private final ServiceRoomClassEntityIdMapper serviceRoomClassEntityIdMapper;

    @Override
    public ServiceRoomClass save(ServiceRoomClass serviceRoomClass) {
        var serviceRoomClassEntity = serviceRoomClassEntityMapper.toEntity(serviceRoomClass);
        var savedServiceRoomClassEntity = jpaServiceRoomClassEntityRepository.save(serviceRoomClassEntity);
        return serviceRoomClassEntityMapper.toDomain(savedServiceRoomClassEntity);

    }

    @Override
    public List<ServiceRoomClass> saveAll(List<ServiceRoomClass> listServiceRoomClass) {
        var serviceRoomClassEntityList = listServiceRoomClass.stream()
                .map(serviceRoomClassEntityMapper::toEntity)
                .collect(Collectors.toList());

        var savedServiceRoomClassEntityList = jpaServiceRoomClassEntityRepository
                .saveAll(serviceRoomClassEntityList);

        return savedServiceRoomClassEntityList
                .stream().map(serviceRoomClassEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkExistsByServiceRoomClassId(ServiceRoomClassId id) {
        var serviceRoomClassEntityId = serviceRoomClassEntityIdMapper.toEntity(id);
        return jpaServiceRoomClassEntityRepository.existsById(serviceRoomClassEntityId);
    }

    @Override
    public List<ServiceRoomClass> findAllByRoomClassId(String roomClassId) {
        return jpaServiceRoomClassEntityRepository
                .findAllByRoomClassId(UUID.fromString(roomClassId))
                .stream().map(serviceRoomClassEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
