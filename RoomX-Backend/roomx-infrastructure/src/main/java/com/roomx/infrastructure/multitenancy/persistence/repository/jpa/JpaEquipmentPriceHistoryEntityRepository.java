package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.model.entity.EquipmentPriceHistory;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.EquipmentEntity;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.EquipmentPriceHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface JpaEquipmentPriceHistoryEntityRepository extends JpaRepository<EquipmentPriceHistoryEntity, UUID> {

    @Query("""
                SELECT COUNT(e) > 0
                        FROM EquipmentPriceHistoryEntity e
                        WHERE e.equipment.id = :equipmentId
                        AND (
                            (e.validFrom < :validEnd AND e.validEnd > :validFrom)
                        )
            """)
    boolean existsByEquipmentAndTimeRange(
            @Param("equipmentId") UUID equipmentId,
            @Param("validFrom") Instant requestFrom,
            @Param("validEnd") Instant requestEnd);

    @Query("""
                SELECT e FROM EquipmentPriceHistoryEntity e
                WHERE e.equipment.id = :equipmentId
                AND (
                    (e.validFrom < :validEnd AND e.validEnd > :validFrom)
                )
            """)
    List<EquipmentPriceHistoryEntity> findAllOverlappingPriceHistory(UUID equipmentId, Instant validFrom, Instant validEnd);

    @Query("""
                SELECT e FROM EquipmentPriceHistoryEntity e 
                WHERE e.equipment.id = :equipmentId 
                ORDER BY e.validFrom DESC 
                LIMIT 1
            """)
    Optional<EquipmentPriceHistoryEntity> findLatestValidFrom(@Param("equipmentId") UUID equipmentId);
}
