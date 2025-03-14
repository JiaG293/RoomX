package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.RoomClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaRoomClassEntityRepository extends JpaRepository<RoomClassEntity, UUID> {
}
