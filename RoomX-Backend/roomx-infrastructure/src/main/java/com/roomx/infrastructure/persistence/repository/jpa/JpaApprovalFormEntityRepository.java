package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.infrastructure.persistence.model.entity.ApprovalFormEntity;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaApprovalFormEntityRepository extends JpaRepository<ApprovalFormEntity, UUID>, JpaSpecificationExecutor<ApprovalFormEntity> {

    /*@Query("""
            SELECT af
            FROM ApprovalFormEntity af
            WHERE af.bookingRequest.id = :bookingRequestId AND af.status = :status
            ORDER BY af.updatedAt DESC
            """)*/
    Optional<ApprovalFormEntity> findByBookingRequestIdAndStatusOrderByUpdatedAtDesc(UUID uuid, String status);

//    List<ApprovalFormEntity>findAllByStatusInAndUpdatedAtIsBetween(List<String> listStatusCanApproval, Instant startDate, Instant endDate);

    Page<ApprovalFormEntity> findAllByStatusInAndUpdatedAtIsBetween(List<String> listStatusCanApproval, Instant startDate, Instant endDate, Pageable pageable);

    Optional<ApprovalFormEntity> findFirstByBookingRequestIdOrderByUpdatedAtDesc(UUID bookingRequestId);

    Page<ApprovalFormEntity> findAllByStatusInAndBookingRequestRequesterAndUpdatedAtIsBetween(List<String> listStatusCanApproval, UUID requester, Instant startDate, Instant endDate, Pageable pageable);

    List<ApprovalFormEntity> findAllByStatusIn(List<String> status);
}
