package com.roomx.infrastructure.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.repository.EquipmentRepository;
import com.roomx.infrastructure.persistence.mapper.EquipmentEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaEquipmentEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EquipmentEntityRepository implements EquipmentRepository {
    private final JpaEquipmentEntityRepository jpaEquipmentEntityRepository;
    private final EquipmentEntityMapper equipmentEntityMapper;

    @Override
    public Optional<Equipment> findById(String id) {
        return jpaEquipmentEntityRepository
                .findById(UUID.fromString(id))
                .map(equipmentEntityMapper::toDomain);
    }

    @Override
    public Optional<Equipment> findByEquipmentCode(String equipmentCode) {
        return jpaEquipmentEntityRepository
                .findByEquipmentCode(equipmentCode)
                .map(equipmentEntityMapper::toDomain);
    }

    @Override
    public Equipment save(Equipment equipment) {
        var equipmentEntity = equipmentEntityMapper.toEntity(equipment);
        var savedEquipmentEntity = jpaEquipmentEntityRepository.save(equipmentEntity);
        return equipmentEntityMapper.toDomain(savedEquipmentEntity);
    }

    @Override
    public void delete(Equipment equipment) {
        jpaEquipmentEntityRepository.delete(equipmentEntityMapper.toEntity(equipment));
    }

    @Override
    public void deleteById(String id) {
        jpaEquipmentEntityRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public List<Equipment> saveAll(List<Equipment> listEquipmentDeleted) {
        var listEquipmentEntity = listEquipmentDeleted.stream().map(equipmentEntityMapper::toEntity).toList();
        var savedEquipmentEntity = jpaEquipmentEntityRepository.saveAll(listEquipmentEntity);
        return savedEquipmentEntity.stream().map(equipmentEntityMapper::toDomain).toList();
    }

    @Override
    public Optional<Equipment> findByIdAndStatus(String equipmentId, String status) {
        return jpaEquipmentEntityRepository
                .findByIdAndStatus(UUID.fromString(equipmentId), status)
                .map(equipmentEntityMapper::toDomain);
    }
}
