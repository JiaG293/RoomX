package com.roomx.infrastructure.persistence.repository.impl;

import com.roomx.domain.model.entity.EquipmentRoomClass;
import com.roomx.domain.model.vo.EquipmentRoomClassId;
import com.roomx.domain.repository.EquipmentRoomClassRepository;
import com.roomx.infrastructure.persistence.mapper.EquipmentRoomClassEntityIdMapper;
import com.roomx.infrastructure.persistence.mapper.EquipmentRoomClassEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaEquipmentRoomClassEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EquipmentRoomClassEntityRepository implements EquipmentRoomClassRepository {
    private final JpaEquipmentRoomClassEntityRepository jpaEquipmentRoomClassEntityRepository;
    private final EquipmentRoomClassEntityMapper equipmentRoomClassEntityMapper;
    private final EquipmentRoomClassEntityIdMapper equipmentRoomClassEntityIdMapper;

    @Override
    public boolean checkExistsByEquipmentRoomClassId(EquipmentRoomClassId id) {
        var equipmentRoomClassEntityId = equipmentRoomClassEntityIdMapper.toEntity(id);
        return jpaEquipmentRoomClassEntityRepository.existsById(equipmentRoomClassEntityId);
    }

    @Override
    public EquipmentRoomClass save(EquipmentRoomClass equipmentRoomClass) {
        var equipmentRoomClassEntity = equipmentRoomClassEntityMapper.toEntity(equipmentRoomClass);
        var savedEquipmentRoomClassEntity = jpaEquipmentRoomClassEntityRepository.save(equipmentRoomClassEntity);
        return equipmentRoomClassEntityMapper.toDomain(savedEquipmentRoomClassEntity);
    }

    @Override
    public List<EquipmentRoomClass> saveAll(List<EquipmentRoomClass> listEquipmentRoomClass) {
        var equipmentRoomClassEntityList = listEquipmentRoomClass.stream()
                .map(equipmentRoomClassEntityMapper::toEntity)
                .collect(Collectors.toList());

        var savedEquipmentRoomClassEntityList = jpaEquipmentRoomClassEntityRepository
                .saveAll(equipmentRoomClassEntityList);

        return savedEquipmentRoomClassEntityList
                .stream().map(equipmentRoomClassEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<EquipmentRoomClass> findAllByRoomClassId(String roomClassId) {
        return jpaEquipmentRoomClassEntityRepository
                .findAllByRoomClassId(UUID.fromString(roomClassId))
                .stream().map(equipmentRoomClassEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
