package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.BookingRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface JpaBookingRequestEntityRepository extends JpaRepository<BookingRequestEntity, UUID>, JpaSpecificationExecutor<BookingRequestEntity> {



//    @Query("""
//            SELECT BookingRequestEntity
//            FROM BookingRequestEntity br
//            LEFT JOIN ApprovalFormEntity af ON br.id = af.bookingRequest
//                WHERE br.id = :uuid AND af.status = :approvalStatus
//                ORDER BY af.updatedAt DESC
//                LIMIT 1
//            """)
//    Optional<BookingRequestEntity> findByIdAndEndDateLastApprovalStatus(UUID uuid, String approvalStatus);


}




