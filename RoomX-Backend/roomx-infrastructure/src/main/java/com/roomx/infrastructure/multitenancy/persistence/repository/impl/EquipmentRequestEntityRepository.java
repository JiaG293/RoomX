package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.entity.EquipmentRequest;
import com.roomx.domain.model.vo.EquipmentRequestId;
import com.roomx.domain.repository.EquipmentRequestRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.EquipmentRequestEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.mapper.EquipmentRequestIdMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaEquipmentRequestEntityRepository;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaRecurrenceEntityRepository;
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
    private final EquipmentRequestIdMapper equipmentRequestIdMapper;

    @Override
    public Optional<EquipmentRequest> findById(EquipmentRequestId equipmentRequestId) {
        var equipmentRequestEntityId = equipmentRequestIdMapper.toEntity(equipmentRequestId);
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
        return null;
    }

    @Override
    public List<EquipmentRequest> saveAll(List<EquipmentRequest> listEquipmentRequset) {
        return List.of();
    }
}
