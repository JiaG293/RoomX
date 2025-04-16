package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.infrastructure.persistence.model.entity.RoomEntity;
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

}
