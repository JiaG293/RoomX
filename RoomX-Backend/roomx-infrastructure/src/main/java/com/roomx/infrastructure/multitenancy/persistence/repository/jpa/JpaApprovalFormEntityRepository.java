package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.ApprovalFormEntity;
import io.micrometer.observation.ObservationFilter;
import org.hibernate.sql.model.jdbc.OptionalTableUpdateOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaApprovalFormEntityRepository extends JpaRepository<ApprovalFormEntity, UUID>, JpaSpecificationExecutor<ApprovalFormEntity> {

    /*@Query("""
            SELECT af
            FROM ApprovalFormEntity af
            WHERE af.bookingRequest.id = :bookingRequestId AND af.status = :status
            ORDER BY af.updatedAt DESC
            """)*/
    Optional<ApprovalFormEntity> findByBookingRequestIdAndStatusOrderByUpdatedAtDesc( UUID uuid, String status);

}
