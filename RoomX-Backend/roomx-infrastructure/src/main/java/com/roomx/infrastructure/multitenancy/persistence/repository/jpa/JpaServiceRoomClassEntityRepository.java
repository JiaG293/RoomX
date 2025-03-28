package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;


import com.roomx.infrastructure.multitenancy.persistence.model.entity.ServiceRoomClassEntity;
import com.roomx.infrastructure.multitenancy.persistence.model.ids.ServiceRoomClassEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


public interface JpaServiceRoomClassEntityRepository extends JpaRepository<ServiceRoomClassEntity, ServiceRoomClassEntityId> {
    List<ServiceRoomClassEntity> findAllByRoomClassId(UUID roomClassId);
}
