package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.infrastructure.persistence.model.entity.ApprovalFormEntity;
import com.roomx.infrastructure.persistence.model.projection.BookingApprovalRequestProjection;
import com.roomx.infrastructure.persistence.model.projection.BookingRequestFlatProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    Page<ApprovalFormEntity> findAllByStatusInAndUpdatedAtIsBetweenAndStatus(List<String> listStatusCanApproval, Instant startDate, Instant endDate, String status, Pageable pageable);

    Optional<ApprovalFormEntity> findFirstByBookingRequestIdOrderByUpdatedAtDesc(UUID bookingRequestId);

    Page<ApprovalFormEntity> findAllByStatusInAndBookingRequestRequesterAndUpdatedAtIsBetween(List<String> listStatusCanApproval, UUID requester, Instant startDate, Instant endDate, Pageable pageable);

    Page<ApprovalFormEntity> findAllByStatusInAndBookingRequestRequesterAndUpdatedAtIsBetweenAndStatus(List<String> listStatusCanApproval, UUID requester, Instant startDate, Instant endDate, String status, Pageable pageable);


    List<ApprovalFormEntity> findAllByStatusIn(List<String> status);


    /*@Query(
            value = """
                    SELECT 
                        af.approval_form_id AS approvalFormId,
                        af.status AS status,
                        af.updated_at AS updatedAt,
                    
                        br.booking_request_id AS bookingRequestId,
                        br.room_id AS roomId,
                        br.branch_id AS branchId,
                    
                        dre.date_booking_id AS dateRequestExceptionId,
                        dre.date AS exceptionDate,
                        dre.start_time AS exceptionStartTime,
                        dre.end_time AS exceptionEndTime
                    
                    FROM approval_form af
                    JOIN booking_request br ON af.booking_request_id = br.booking_request_id
                    LEFT JOIN date_request_exception dre ON dre.booking_request_id = br.booking_request_id
                    
                    WHERE af.status IN (:listStatusCanApproval)
                      AND af.updated_at BETWEEN :startDate AND :endDate
                    
                    ORDER BY af.updated_at DESC
                    """,
            countQuery = """
                    SELECT COUNT(DISTINCT af.approval_form_id)
                    FROM approval_form af
                    WHERE af.status IN (:statuses)
                      AND af.updated_at BETWEEN :startDate AND :endDate
                    """,
            nativeQuery = true
    )
    Page<ApprovalFormProjection> findAllByStatusAndDateRange(
            @Param("listStatusCanApproval") List<String> listStatusCanApproval,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable
    );*/

    @Query(value = """
    SELECT 
        br.booking_request_id,
        br.title,
        br.description,
        br.recurrence_type,
        br.start_date,
        br.end_date,
        br.start_time,
        br.end_time,
        br.days_of_week,
        br.recurrence_interval,
        br.capacity,
        br.priority,
        af.status AS approval_status,
        af.created_at,  
        af.updated_at,

        r.room_id,
        CONCAT(bd.code, f.code, r.room_code) AS room_name, 
        r.room_code as room_code,

        br.branch_id,  
        b.name as branch_name,
        b.code as branch_code,

        bd.place_id,
        bd.name as building_name,
        bd.code as building_code,

        f.place_id,
        f.name as floor_name,
        f.code as floor_code

    FROM approval_form af
    JOIN booking_request br ON br.booking_request_id = af.booking_request_id
    JOIN room r ON r.room_id = br.room_id
    LEFT JOIN place b ON b.place_id = br.branch_id AND b.place_type = 'BRANCH'
    LEFT JOIN place bd ON bd.place_id = r.place_id AND bd.place_type = 'BUILDING' AND bd.parent_id = b.place_id  -- Phân biệt với parent_id
    LEFT JOIN place f ON f.place_id = r.place_id AND f.place_type = 'FLOOR' AND f.parent_id = bd.place_id  -- Phân biệt với parent_id

    WHERE af.status IN :statuses
      AND af.updated_at BETWEEN :startDate AND :endDate
    ORDER BY af.updated_at DESC
""", countQuery = """
    SELECT COUNT(*)
    FROM approval_form af
    JOIN booking_request br ON br.booking_request_id = af.booking_request_id
    JOIN room r ON r.room_id = br.room_id
    LEFT JOIN place b ON b.place_id = br.branch_id AND b.place_type = 'BRANCH'
    LEFT JOIN place bd ON bd.place_id = r.place_id AND bd.place_type = 'BUILDING' AND bd.parent_id = b.place_id  -- Phân biệt với parent_id
    LEFT JOIN place f ON f.place_id = r.place_id AND f.place_type = 'FLOOR' AND f.parent_id = bd.place_id  -- Phân biệt với parent_id

    WHERE af.status IN :statuses
      AND af.updated_at BETWEEN :startDate AND :endDate
""", nativeQuery = true)
    Page<BookingRequestFlatProjection> findBookingRequestApprovalsNative(
            @Param("statuses") List<String> statuses,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable
    );




    @Query(
            value = """
        SELECT 
            a.created_at,
            a.updated_at,
            a.status,
            a.approver,
            b.booking_request_id,
            b.priority,
            b.days_of_week,
            b.start_time,
            b.end_time,
            b.end_date,
            b.start_date,
            b.recurrence_interval,
            b.recurrence_type,
            b.capacity,
            b.requester,
            b.branch_id,
            b.room_id,
            b.title,
            b.description
        FROM (
            SELECT DISTINCT ON (booking_request_id) *
            FROM approval_form
            WHERE updated_at BETWEEN :startDate AND :endDate
            AND (:listStatus IS NULL OR status IN :listStatus)
            ORDER BY booking_request_id, updated_at DESC
        ) a
        JOIN booking_request b ON a.booking_request_id = b.booking_request_id
        WHERE (:requester IS NULL OR b.requester = CAST(:requester AS UUID))
        ORDER BY b.created_at DESC
        """,
            countQuery = """
        SELECT COUNT(*) FROM (
            SELECT DISTINCT ON (booking_request_id) *
            FROM approval_form
            WHERE updated_at BETWEEN :startDate AND :endDate
            AND (:listStatus IS NULL OR status IN :listStatus)
        ) a
        JOIN booking_request b ON a.booking_request_id = b.booking_request_id
       WHERE (:requester IS NULL OR b.requester = CAST(:requester AS UUID))
        """,
            nativeQuery = true
    )
    Page<BookingApprovalRequestProjection> findAllByListStatusAndTimeRangeWithBookingRequest(
            @Param("listStatus") List<String> listStatus,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            @Param("requester") String requester,
            Pageable pageable
    );


    Optional<ApprovalFormEntity> findByBookingRequestIdOrderByUpdatedAtDesc(UUID bookingRequestId);
}
