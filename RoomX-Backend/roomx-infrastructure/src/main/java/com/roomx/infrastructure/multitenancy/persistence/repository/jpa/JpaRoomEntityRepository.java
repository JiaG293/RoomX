package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.domain.model.aggrerate.Room;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaRoomEntityRepository extends JpaRepository<RoomEntity, UUID>, JpaSpecificationExecutor<RoomEntity> {

    boolean existsByRoomCode(String roomCode);


    List<RoomEntity> findAllByStatus(String status);

    List<RoomEntity> findAllByStatusIsAndRoomClassCapacityGreaterThanEqual(String status, int requiredCapacity);

    @Query("""
            SELECT r FROM RoomEntity r 
            LEFT JOIN FETCH r.place
            WHERE r.status = :status AND r.place.branch.id = :branchId
            """)
    List<RoomEntity> findAllByStatusBranchId(@Param("status") String status, @Param("branchId") UUID branchId);
}
