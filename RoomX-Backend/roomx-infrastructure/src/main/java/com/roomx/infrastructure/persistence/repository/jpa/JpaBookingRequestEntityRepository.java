package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.infrastructure.persistence.model.entity.BookingRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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




