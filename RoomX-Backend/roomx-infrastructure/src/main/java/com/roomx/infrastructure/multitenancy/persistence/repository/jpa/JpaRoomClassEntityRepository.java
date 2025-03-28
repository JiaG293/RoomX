package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.RoomClassEntity;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


public interface JpaRoomClassEntityRepository extends JpaRepository<RoomClassEntity, UUID>, JpaSpecificationExecutor<RoomClassEntity> {
    boolean existsByRoomClassCode(String roomClassCode);

    Optional<RoomClassEntity> findByRoomClassCode(String roomClassCode);

    Optional<RoomClassEntity> findByIdAndStatus(UUID roomClassId, String status);
}
