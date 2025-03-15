package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;


import com.roomx.infrastructure.multitenancy.persistence.model.entity.EquipmentRoomClassEntity;
import com.roomx.infrastructure.multitenancy.persistence.model.ids.EquipmentRoomClassEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaEquipmentRoomClassEntityRepository extends JpaRepository<EquipmentRoomClassEntity, EquipmentRoomClassEntityId> {
    List<EquipmentRoomClassEntity> findAllByRoomClassId(UUID roomClassId);
}
