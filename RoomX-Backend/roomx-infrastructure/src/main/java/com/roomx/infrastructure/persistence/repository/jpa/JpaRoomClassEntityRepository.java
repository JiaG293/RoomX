package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.infrastructure.persistence.model.entity.RoomClassEntity;
import com.roomx.infrastructure.persistence.model.projection.RoomClassProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


public interface JpaRoomClassEntityRepository extends JpaRepository<RoomClassEntity, UUID>, JpaSpecificationExecutor<RoomClassEntity> {
    boolean existsByRoomClassCode(String roomClassCode);

    Optional<RoomClassEntity> findByRoomClassCode(String roomClassCode);

    Optional<RoomClassEntity> findByIdAndStatus(UUID roomClassId, String status);


    @Query(
            value = """
                    WITH latest_price AS (
                        SELECT room_class_id, MAX(valid_from) AS valid_from
                        FROM room_class_price_history
                        WHERE valid_from <= CURRENT_TIMESTAMP
                        GROUP BY room_class_id
                    ),
                         price_with_latest AS (
                             SELECT rcph.*
                             FROM room_class_price_history rcph
                                      JOIN latest_price lp
                                           ON rcph.room_class_id = lp.room_class_id AND rcph.valid_from = lp.valid_from
                         ),
                         services AS (
                             SELECT room_class_id,
                                    JSON_AGG(JSON_BUILD_OBJECT(
                                            'id', src.service_id,
                                            'quantity', src.quantity,
                                            'name', s.name
                                             )) AS services
                             FROM service_room_class src
                                      JOIN service s ON src.service_id = s.service_id
                             GROUP BY src.room_class_id
                         ),
                         equipment AS (
                             SELECT erc.room_class_id,
                                    JSON_AGG(JSON_BUILD_OBJECT(
                                            'id', erc.equipment_id,
                                            'quantity', erc.quantity,
                                            'name', e.name
                                             )) AS equipments
                             FROM equipment_room_class erc
                                      JOIN equipment e ON erc.equipment_id = e.equipment_id
                             GROUP BY erc.room_class_id
                         )
                    
                    SELECT
                        rc.room_class_id AS id,
                        rc.room_class_code AS roomClassCode,
                        rc.status AS status,
                        rc.capacity AS capacity,
                        rcph.total_price AS totalPrice,
                        rcph.valid_from AS validFrom,
                        rcph.valid_end AS validEnd,
                        rcph.base_price AS basePrice,
                        s.services AS jsonServices,
                        e.equipments AS jsonEquipments
                    FROM room_class rc
                             LEFT JOIN price_with_latest rcph ON rc.room_class_id = rcph.room_class_id
                             LEFT JOIN services s ON rc.room_class_id = s.room_class_id
                             LEFT JOIN equipment e ON rc.room_class_id = e.room_class_id
                    WHERE
                        (:keyword IS NULL OR (
                            (
                                (:searchBy IS NULL OR :searchBy = 'id')
                                    AND rc.room_class_id::text ILIKE '%' || :keyword || '%'
                                )
                                OR (
                                (:searchBy IS NULL OR :searchBy = 'code')
                                    AND rc.room_class_code ILIKE '%' || :keyword || '%'
                                )
                                OR (
                                (:searchBy IS NULL OR :searchBy = 'serviceName')
                                    AND EXISTS (
                                    SELECT 1
                                    FROM jsonb_array_elements(s.services::jsonb) AS service
                                    WHERE service->>'name' ILIKE '%' || :keyword || '%'
                                )
                                )
                                OR (
                                (:searchBy IS NULL OR :searchBy = 'equipmentName')
                                    AND EXISTS (
                                    SELECT 1
                                    FROM jsonb_array_elements(e.equipments::jsonb) AS equipment
                                    WHERE equipment->>'name' ILIKE '%' || :keyword || '%'
                                )
                                )
                            ))
                    AND (:startPrice IS NULL OR COALESCE(rcph.total_price, 0) >=  CAST(:startPrice AS NUMERIC))
                    AND (:endPrice IS NULL OR COALESCE(rcph.total_price, 0) <= CAST(:endPrice AS NUMERIC))
                    AND (:capacity IS NULL OR rc.capacity <= CAST(:capacity AS INTEGER))
                    AND (:status IS NULL OR rc.status = COALESCE( :status, 'ACTIVE'))
                    """,
            countQuery = """
        WITH latest_price AS (
            SELECT room_class_id, MAX(valid_from) AS valid_from
            FROM room_class_price_history
            WHERE valid_from <= CURRENT_TIMESTAMP
            GROUP BY room_class_id
        ),
        price_with_latest AS (
            SELECT rcph.*
            FROM room_class_price_history rcph
            JOIN latest_price lp
              ON rcph.room_class_id = lp.room_class_id AND rcph.valid_from = lp.valid_from
        ),
        services AS (
            SELECT room_class_id,
                   JSON_AGG(JSON_BUILD_OBJECT(
                       'id', src.service_id,
                       'quantity', src.quantity,
                       'name', s.name
                   )) AS services
            FROM service_room_class src
            JOIN service s ON src.service_id = s.service_id
            GROUP BY src.room_class_id
        ),
        equipment AS (
            SELECT erc.room_class_id,
                   JSON_AGG(JSON_BUILD_OBJECT(
                       'id', erc.equipment_id,
                       'quantity', erc.quantity,
                       'name', e.name
                   )) AS equipments
            FROM equipment_room_class erc
            JOIN equipment e ON erc.equipment_id = e.equipment_id
            GROUP BY erc.room_class_id
        )

        SELECT COUNT(*)
        FROM room_class rc
        LEFT JOIN price_with_latest rcph ON rc.room_class_id = rcph.room_class_id
        LEFT JOIN services s ON rc.room_class_id = s.room_class_id
        LEFT JOIN equipment e ON rc.room_class_id = e.room_class_id
        WHERE
            (:keyword IS NULL OR (
                (:searchBy = 'id' AND rc.room_class_id::text ILIKE '%' || :keyword || '%')
                OR (:searchBy = 'code' AND rc.room_class_code ILIKE '%' || :keyword || '%')
                OR (:searchBy = 'serviceName' AND EXISTS (
                    SELECT 1
                    FROM jsonb_array_elements(s.services::jsonb) AS service
                    WHERE service->>'name' ILIKE '%' || :keyword || '%'
                ))
                OR (:searchBy = 'equipmentName' AND EXISTS (
                    SELECT 1
                    FROM jsonb_array_elements(e.equipments::jsonb) AS equipment
                    WHERE equipment->>'name' ILIKE '%' || :keyword || '%'
                ))
            ))
            AND (:startPrice IS NULL OR COALESCE(rcph.total_price, 0) >=  CAST(:startPrice AS NUMERIC))
            AND (:endPrice IS NULL OR COALESCE(rcph.total_price, 0) <= CAST(:endPrice AS NUMERIC))
            AND (:capacity IS NULL OR rc.capacity <= CAST(:capacity AS INTEGER))
            AND (:status IS NULL OR rc.status = COALESCE( :status, 'ACTIVE'))
    """,
            nativeQuery = true)
    Page<RoomClassProjection> filterSearchRoomClass(
            @Param("keyword") String keyword,
            @Param("searchBy") String searchBy,
            @Param("status") String status,
            @Param("capacity") Integer capacity,
            @Param("startPrice") BigDecimal startPrice,
            @Param("endPrice") BigDecimal endPrice,
            Pageable pageable
    );
}
