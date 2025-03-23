package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.RoomClassPriceHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


public interface JpaRoomClassPriceHistoryEntityRepository extends JpaRepository<RoomClassPriceHistoryEntity, UUID> {
}
