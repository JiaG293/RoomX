package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.domain.model.aggrerate.Room;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaRoomEntityRepository extends JpaRepository<RoomEntity, UUID>, JpaSpecificationExecutor<RoomEntity> {

    boolean existsByRoomCode(String roomCode);


    List<RoomEntity> findAllByStatus(String status);

    List<RoomEntity> findAllByStatusIsAndRoomClassCapacityGreaterThanEqual(String status, int requiredCapacity);
}
