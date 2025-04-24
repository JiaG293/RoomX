package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.infrastructure.persistence.model.entity.RoomClassPriceHistoryEntity;

import com.roomx.shared.dto.resource.base.RoomClassPriceCalculateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


public interface JpaRoomClassPriceHistoryEntityRepository extends JpaRepository<RoomClassPriceHistoryEntity, UUID> {

    @Query("""
                SELECT r FROM RoomClassPriceHistoryEntity r 
                WHERE r.roomClass.id = :roomClassId
                ORDER BY r.validFrom DESC 
                LIMIT 1
            """)
    Optional<RoomClassPriceHistoryEntity> findLatestValidFrom(@Param("roomClassId") UUID roomClassId);

    @Query(value = """
                SELECT 
                    rc.room_class_id AS roomClassId, 
                    rc.room_class_code AS roomClassCode,
                    COALESCE(MAX(rcp.base_price), 0) AS basePrice,
                    COALESCE(SUM(sph.unit_price * src.quantity), 0) AS totalServicePrice,
                    COALESCE(SUM(eph.unit_price * erc.quantity), 0) AS totalEquipmentPrice,
                    COALESCE(MAX(rcp.base_price), 0) + 
                    COALESCE(SUM(sph.unit_price * src.quantity), 0) + 
                    COALESCE(SUM(eph.unit_price * erc.quantity), 0) AS totalPrice
                FROM room_class rc
                LEFT JOIN room_class_price_history rcp 
                    ON rcp.room_class_id = rc.room_class_id
                LEFT JOIN service_room_class src 
                    ON src.room_class_id = rc.room_class_id
                LEFT JOIN service_price_history sph 
                    ON sph.service_id = src.service_id
                LEFT JOIN equipment_room_class erc 
                    ON erc.room_class_id = rc.room_class_id
                LEFT JOIN equipment_price_history eph 
                    ON eph.equipment_id = erc.equipment_id
                WHERE rc.room_class_id = :roomClassId
                GROUP BY rc.room_class_id, rc.room_class_code
            """, nativeQuery = true)
    RoomClassPriceCalculateDto calculateTotalPrice(UUID roomClassId);


    @Query(value = """
            SELECT a.total_price
            FROM room_class_price_history a
            WHERE a.room_class_id = :roomClassId
            AND :timestamp >= a.valid_from
            AND (:timestamp <= a.valid_end OR a.valid_end IS NULL)
            ORDER BY a.valid_from DESC
            LIMIT 1
            """, nativeQuery = true)
    Optional<BigDecimal> findPriceByRoomClassIdValidTime(@Param("roomClassId") UUID roomClassId, @Param("timestamp") String timestamp);
}
