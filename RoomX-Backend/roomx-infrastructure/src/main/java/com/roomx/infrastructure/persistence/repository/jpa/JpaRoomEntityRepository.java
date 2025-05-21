package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.infrastructure.persistence.model.entity.RoomEntity;
import com.roomx.infrastructure.persistence.model.projection.RoomCapacityProjection;
import com.roomx.infrastructure.persistence.model.projection.RoomProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaRoomEntityRepository extends JpaRepository<RoomEntity, UUID>, JpaSpecificationExecutor<RoomEntity> {

    boolean existsByRoomCode(String roomCode);


    List<RoomEntity> findAllByStatus(String status);

    List<RoomEntity> findAllByStatusIsAndRoomClassCapacityGreaterThanEqual(String status, int requiredCapacity);

    @EntityGraph(attributePaths = {"roomClass"})
    @Query("""
            SELECT r, pbg.parentId FROM RoomEntity r 
            LEFT JOIN FETCH PlaceEntity pfl ON r.place.id = pfl.id
            LEFT JOIN FETCH PlaceEntity pbg ON pfl.parentId= pbg.id
            WHERE r.status = :status AND pbg.parentId = :branchId 
            """)
    List<RoomEntity> findAllByStatusBranchId(@Param("status") String status, @Param("branchId") UUID branchId);

    List<RoomEntity> findAllByPlaceIdAndStatus(UUID placeId, String status);

    @Query(value = """
            SELECT a.total_price
            FROM room_class_price_history a
            JOIN room r ON r.room_class_id = a.room_class_id
            WHERE r.room_id = :roomId
            AND :timestamp >= a.valid_from
            AND (:timestamp <= a.valid_end OR a.valid_end IS NULL)
            ORDER BY a.valid_from DESC
            LIMIT 1
            """, nativeQuery = true)
    Optional<BigDecimal> findPriceByIdAndValidTimestamp(@Param("roomId") UUID roomId, @Param("timestamp") Instant timestamp);


    @Query(
            value = """
                    SELECT
                        r.room_id AS id, r.room_code AS roomCode, r.status AS status, r.description AS decription,
                        rc.room_class_id AS roomClassId, rc.room_class_code AS roomClassCode, rc.capacity AS capacity,
                        rp.total_price AS totalPrice, rp.base_price AS basePrice, rp.valid_from AS validFrom, rp.valid_end AS validEnd,
                        pf.place_id AS floorPlaceId,
                        pf.parent_id AS buildingPlaceId,
                        pbg.parent_id AS branchPlaceId,
                    
                        json_agg(DISTINCT jsonb_build_object(
                                'id', eq.equipment_id,
                                'equipmentCode', eq.equipment_code,
                                'name', eq.name,
                                'description', eq.description,
                                'brand', eq.brand,
                                'quantity', erc.quantity,
                                'price', ep.current_price
                        )) FILTER (WHERE eq.equipment_id IS NOT NULL) AS equipmentsJson,
                    
                        json_agg(DISTINCT jsonb_build_object(
                                'id', sv.service_id,
                                'serviceCode', sv.service_code,
                                'name', sv.name,
                                'note', sv.note,
                                'description', sv.description,
                                'quantity', src.quantity,
                                'price', sp.current_price
                        )) FILTER (WHERE sv.service_id IS NOT NULL) AS servicesJson,
                    
                    
                        ( SELECT array_agg(url ORDER BY image_order)
                            FROM image_url
                            WHERE entity_id = r.room_id
                        ) AS imageUrls
                    
                    FROM room r
                        LEFT JOIN place pf ON r.place_id = pf.place_id AND pf.place_type = 'FLOOR'
                        LEFT JOIN place pbg ON pf.parent_id = pbg.place_id AND pbg.place_type = 'BUILDING'
                        LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id
                    
                        LEFT JOIN LATERAL (
                           SELECT *
                           FROM room_class_price_history rp
                           WHERE rp.room_class_id = rc.room_class_id
                             AND rp.valid_from <= NOW()
                             AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
                           ORDER BY rp.valid_from DESC
                           LIMIT 1
                        ) rp ON TRUE
                    
                        LEFT JOIN equipment_room_class erc ON erc.room_class_id = rc.room_class_id
                        LEFT JOIN equipment eq ON eq.equipment_id = erc.equipment_id
                    
                        LEFT JOIN LATERAL (
                           SELECT eph.unit_price current_price
                           FROM equipment_price_history eph
                           WHERE eph.equipment_id = eq.equipment_id
                             AND eph.valid_from <= NOW()
                             AND (eph.valid_end IS NULL OR eph.valid_end > NOW())
                           ORDER BY eph.valid_from DESC
                           LIMIT 1
                        ) ep ON TRUE
                    
                        LEFT JOIN service_room_class src ON src.room_class_id = rc.room_class_id
                        LEFT JOIN service sv ON sv.service_id = src.service_id
                    
                        LEFT JOIN LATERAL (
                           SELECT sph.unit_price AS current_price
                           FROM service_price_history sph
                           WHERE sph.service_id = sv.service_id
                             AND sph.valid_from <= NOW()
                             AND (sph.valid_end IS NULL OR sph.valid_end > NOW())
                           ORDER BY sph.valid_from DESC
                           LIMIT 1
                        ) sp ON TRUE
                    
                    WHERE
                        (:branchId IS NULL OR pbg.parent_id = CAST(:branchId AS UUID))
                      AND (:buildingId IS NULL OR pf.parent_id = CAST(:buildingId AS UUID))
                      AND (:floorId IS NULL OR pf.place_id = CAST(:floorId AS UUID))
                      AND (:capacity IS NULL OR rc.capacity >= :capacity)
                      AND (
                        (:startPrice IS NULL AND :endPrice IS NULL)
                            OR (rp.total_price BETWEEN COALESCE(:startPrice, 0) AND COALESCE(:endPrice, 1e12))
                      )
                      AND (:status IS NULL OR r.status = :status)
                      AND (
                        :keyword IS NULL OR (
                            (:searchBy IS NULL AND (
                                r.room_code ILIKE '%' || :keyword || '%' OR
                                r.description ILIKE '%' || :keyword || '%' OR
                                rc.room_class_code ILIKE '%' || :keyword || '%' OR
                                r.room_id = CAST(:keyword AS UUID)
                            )) OR
                            (:searchBy = 'roomCode' AND r.room_code ILIKE '%' || :keyword || '%') OR
                            (:searchBy = 'description' AND r.description ILIKE '%' || :keyword || '%') OR
                            (:searchBy = 'roomClassCode' AND rc.room_class_code ILIKE '%' || :keyword || '%') OR
                            (:searchBy = 'id' AND r.room_id = CAST(:keyword AS UUID))
                        )
                    )
                    GROUP BY
                        r.room_id, r.room_code, r.status, r.description,
                        rc.room_class_id, rc.room_class_code, rc.capacity,
                        rp.total_price, rp.base_price, rp.valid_from, rp.valid_end,
                        pf.place_id, pf.parent_id,
                        pbg.parent_id
                    ORDER BY
                    CASE WHEN :#{#pageable.sort.isEmpty()} THEN NULL ELSE NULL END
                    """,
            countQuery = """
                    SELECT COUNT(DISTINCT r.room_id)
                    FROM room r
                        LEFT JOIN place pf ON r.place_id = pf.place_id AND pf.place_type = 'FLOOR'
                        LEFT JOIN place pbg ON pf.parent_id = pbg.place_id AND pbg.place_type = 'BUILDING'
                        LEFT JOIN room_class rc ON rc.room_class_id = r.room_class_id
                    
                        LEFT JOIN LATERAL (
                           SELECT *
                           FROM room_class_price_history rp
                           WHERE rp.room_class_id = rc.room_class_id
                             AND rp.valid_from <= NOW()
                             AND (rp.valid_end IS NULL OR rp.valid_end > NOW())
                           ORDER BY rp.valid_from DESC
                           LIMIT 1
                        ) rp ON TRUE
                    
                    WHERE
                        (:branchId IS NULL OR pbg.parent_id = CAST(:branchId AS UUID))
                      AND (:buildingId IS NULL OR pf.parent_id = CAST(:buildingId AS UUID))
                      AND (:floorId IS NULL OR pf.place_id = CAST(:floorId AS UUID))
                      AND (:capacity IS NULL OR rc.capacity >= :capacity)
                      AND (
                        (:startPrice IS NULL AND :endPrice IS NULL)
                            OR (rp.total_price BETWEEN COALESCE(:startPrice, 0) AND COALESCE(:endPrice, 1e12))
                      )
                      AND (:status IS NULL OR r.status = :status)
                      AND (
                        :keyword IS NULL OR (
                            (:searchBy IS NULL AND (
                                r.room_code ILIKE '%' || :keyword || '%' OR
                                r.description ILIKE '%' || :keyword || '%' OR
                                rc.room_class_code ILIKE '%' || :keyword || '%' OR
                                r.room_id = CAST(:keyword AS UUID)
                            )) OR
                            (:searchBy = 'roomCode' AND r.room_code ILIKE '%' || :keyword || '%') OR
                            (:searchBy = 'description' AND r.description ILIKE '%' || :keyword || '%') OR
                            (:searchBy = 'roomClassCode' AND rc.room_class_code ILIKE '%' || :keyword || '%') OR
                            (:searchBy = 'id' AND r.room_id = CAST(:keyword AS UUID))
                        )
                    )
                    """,
            nativeQuery = true
    )
    Page<RoomProjection> findRoomsWithFilters(
            @Param("status") String status,
            @Param("startPrice") BigDecimal startPrice,
            @Param("endPrice") BigDecimal endPrice,
            @Param("branchId") String branchId,
            @Param("buildingId") String buildingId,
            @Param("floorId") String floorId,
            @Param("capacity") Integer capacity,
            @Param("searchBy") String searchBy,
            @Param("keyword") String keyword,
            Pageable pageable
    );


    @Query(value = """
            SELECT 
                MAX(rc.capacity) AS max,
                MIN(rc.capacity) AS min
            FROM room r
            LEFT JOIN room_class rc ON r.room_class_id = rc.room_class_id
            WHERE r.status = 'AVAILABLE'
            """,
            nativeQuery = true
    )
    RoomCapacityProjection findMaxCapacity();
}
