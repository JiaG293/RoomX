package com.roomx.infrastructure.persistence.repository.jpa;


import com.roomx.infrastructure.persistence.model.entity.ServiceRoomClassEntity;
import com.roomx.infrastructure.persistence.model.ids.ServiceRoomClassEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface JpaServiceRoomClassEntityRepository extends JpaRepository<ServiceRoomClassEntity, ServiceRoomClassEntityId> {
    List<ServiceRoomClassEntity> findAllByRoomClassId(UUID roomClassId);
}
