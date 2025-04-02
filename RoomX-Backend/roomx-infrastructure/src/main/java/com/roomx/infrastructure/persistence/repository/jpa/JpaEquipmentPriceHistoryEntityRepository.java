package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.infrastructure.persistence.model.entity.EquipmentPriceHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
