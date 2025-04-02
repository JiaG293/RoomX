package com.roomx.infrastructure.persistence.repository.impl;

import com.roomx.domain.model.entity.EquipmentRequest;
import com.roomx.domain.model.vo.EquipmentRequestId;
import com.roomx.domain.repository.EquipmentRequestRepository;
import com.roomx.infrastructure.persistence.mapper.EquipmentRequestEntityIdMapper;
import com.roomx.infrastructure.persistence.mapper.EquipmentRequestEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaEquipmentRequestEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EquipmentRequestEntityRepository implements EquipmentRequestRepository {
    private final JpaEquipmentRequestEntityRepository jpaEquipmentRequestEntityRepository;
    private final EquipmentRequestEntityMapper equipmentRequestEntityMapper;
    private final EquipmentRequestEntityIdMapper equipmentRequestEntityIdMapper;

    @Override
    public Optional<EquipmentRequest> findById(EquipmentRequestId equipmentRequestId) {
        var equipmentRequestEntityId = equipmentRequestEntityIdMapper.toEntity(equipmentRequestId);
        return jpaEquipmentRequestEntityRepository
                .findById(equipmentRequestEntityId)
                .map(equipmentRequestEntityMapper::toDomain);
    }

    @Override
    public Optional<EquipmentRequest> findByEquipmentId(String equipmentId) {
        return jpaEquipmentRequestEntityRepository
                .findByEquipmentId(UUID.fromString(equipmentId))
                .map(equipmentRequestEntityMapper::toDomain);
    }

    @Override
    public Optional<EquipmentRequest> findByBookingRequestId(String bookingRequestId) {
        return jpaEquipmentRequestEntityRepository
                .findByBookingRequestId(UUID.fromString(bookingRequestId))
                .map(equipmentRequestEntityMapper::toDomain);
    }

    @Override
    public EquipmentRequest save(EquipmentRequest equipmentRequest) {
        var equipmentRequestEntity = equipmentRequestEntityMapper.toEntity(equipmentRequest);
        var savedEquipmentRequestEntity = jpaEquipmentRequestEntityRepository.save(equipmentRequestEntity);
        return equipmentRequestEntityMapper.toDomain(savedEquipmentRequestEntity);
    }

    @Override
    public List<EquipmentRequest> saveAll(List<EquipmentRequest> listEquipmentRequset) {
        var equipmentRequestEntityList = listEquipmentRequset.stream().map(equipmentRequestEntityMapper::toEntity).toList();
        var savedEquipmentRequestEntityList = jpaEquipmentRequestEntityRepository.saveAll(equipmentRequestEntityList);
        return savedEquipmentRequestEntityList.stream().map(equipmentRequestEntityMapper::toDomain).toList();
    }
}
