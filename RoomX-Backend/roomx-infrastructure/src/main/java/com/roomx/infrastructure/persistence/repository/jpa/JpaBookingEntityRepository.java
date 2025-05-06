package com.roomx.infrastructure.persistence.repository.jpa;


import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.infrastructure.persistence.model.dto.BookingDto;
import com.roomx.infrastructure.persistence.model.entity.BookingEntity;
import com.roomx.infrastructure.persistence.model.projection.BookingProjection;
import com.roomx.shared.base.BookingListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaBookingEntityRepository extends JpaRepository<BookingEntity, UUID>, JpaSpecificationExecutor<BookingEntity> {

    Optional<BookingEntity> findByIdAndStatus(UUID id, String status);

    List<BookingEntity> findAllByStatus(String status);

//    @Query("SELECT b FROM BookingEntity b WHERE b.roomId IN :roomIds AND b.meetingDate = :date")
//    List<BookingEntity> findAllByRoomIdsAndMeetingDate(@Param("roomIds") List<UUID> roomIds, @Param("date") LocalDate date);

    List<BookingEntity> findAllByMeetingDateAndRoomId(LocalDate date, UUID room);

    List<BookingEntity> findAllByMeetingDate(LocalDate date);


    List<BookingEntity> findAllByMeetingDateAndStatusIn(LocalDate date, List<String> listAccept);

    List<BookingEntity> findAllByMeetingDateAndStatusInAndBookingRequestId(LocalDate date, List<String> listAccept, UUID bookingRequestId);


    @Query(value = """
                               SELECT
                                   b.booking_id AS id,
                                   b.title,
                                   b.description,
                                   b.booking_code,
            
                                   r.room_id,
                                   r.room_code,
                                   concat(bl.code, '.', p.code, r.room_code) AS room_name,
            
                                   br.place_id AS branch_id,
                                   br.code AS branch_code,
                                   br.name AS branch_name,
            
                                   bl.place_id AS building_id,
                                   bl.code AS building_code,
                                   bl.name AS building_name,
            
                                   p.place_id AS floor_id,
                                   p.code AS floor_code,
                                   p.name AS floor_name,
            
                                   pr.room_id AS room_previous_id,
            
                                   b.meeting_start,
                                   b.meeting_end,
                                   b.meeting_date,
                                   b.count,
                                   b.status,
                                   b.created_at,
                                   b.updated_at
                        FROM booking b
                                 JOIN
                             booking_participant bp ON b.booking_id = bp.booking_id
                                 JOIN 
                             "user" u ON bp.user_id = u.user_id
                                 JOIN
                             room r ON b.room_id = r.room_id
                                 JOIN
                             place p ON r.place_id = p.place_id -- Đây là place_id của floor
                                 LEFT JOIN
                             room pr ON b.previous_room_id = pr.room_id
                                 -- Join để lấy thông tin branch và building từ parent_id của place
                                 LEFT JOIN
                             place bl ON p.parent_id = bl.place_id AND bl.place_type = 'BUILDING'
                                LEFT JOIN
                             place br ON bl.parent_id = br.place_id AND br.place_type = 'BRANCH'
                        WHERE b.meeting_date BETWEEN :startDate AND :endDate
                             AND bp.user_id = :userId
            """,
            countQuery = """
                    SELECT COUNT(b.booking_id) FROM booking b
                    JOIN booking_participant bp ON b.booking_id = bp.booking_id
                    JOIN \"user\" u ON bp.user_id = u.user_id
                    WHERE b.meeting_date BETWEEN :startDate AND :endDate
                    AND bp.user_id = :userId
                    """,
            nativeQuery = true)
    Page<BookingProjection> findAllByMeetingDateBetweenAndUserId(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("userId") UUID userId,
            Pageable pageable);


    @Query(value = """
                               SELECT
                                   b.booking_id AS id,
                                   b.title,
                                   b.description,
                                   b.booking_code,
            
                                   r.room_id,
                                   r.room_code,
                                   concat(bl.code, '.', p.code, r.room_code) AS room_name,
            
                                   br.place_id AS branch_id,
                                   br.code AS branch_code,
                                   br.name AS branch_name,
            
                                   bl.place_id AS building_id,
                                   bl.code AS building_code,
                                   bl.name AS building_name,
            
                                   p.place_id AS floor_id,
                                   p.code AS floor_code,
                                   p.name AS floor_name,
            
                                   pr.room_id AS room_previous_id,
            
                                   b.meeting_start,
                                   b.meeting_end,
                                   b.meeting_date,
                                   b.count,
                                   b.status,
                                   b.created_at,
                                   b.updated_at
                        FROM booking b
                                 JOIN
                             room r ON b.room_id = r.room_id
                                 JOIN
                             place p ON r.place_id = p.place_id 
                                 LEFT JOIN
                             room pr ON b.previous_room_id = pr.room_id
                                 LEFT JOIN
                             place bl ON p.parent_id = bl.place_id AND bl.place_type = 'BUILDING'
                                LEFT JOIN
                             place br ON bl.parent_id = br.place_id AND br.place_type = 'BRANCH'
                        WHERE b.meeting_date BETWEEN :startDate AND :endDate
            """,
            countQuery = """
                    SELECT COUNT(b.booking_id) FROM booking b
                    WHERE b.meeting_date BETWEEN :startDate AND :endDate
                    """,
            nativeQuery = true)
    Page<BookingProjection> findAllByMeetingDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);

    List<BookingEntity> findAllByMeetingDateInAndStatusIn(List<LocalDate> occurrences, List<String> listAccept);

    @Query(value = """
                               SELECT
                                   b.booking_id AS id,
                                   b.title,
                                   b.description,
                                   b.booking_code,
            
                                   r.room_id,
                                   r.room_code,
                                   concat(bl.code, '.', p.code, r.room_code) AS room_name,
            
                                   br.place_id AS branch_id,
                                   br.code AS branch_code,
                                   br.name AS branch_name,
            
                                   bl.place_id AS building_id,
                                   bl.code AS building_code,
                                   bl.name AS building_name,
            
                                   p.place_id AS floor_id,
                                   p.code AS floor_code,
                                   p.name AS floor_name,
            
                                   pr.room_id AS room_previous_id,
            
                                   b.meeting_start,
                                   b.meeting_end,
                                   b.meeting_date,
                                   b.count,
                                   b.status,
                                   b.created_at,
                                   b.updated_at
                        FROM booking b
                                 JOIN
                             booking_participant bp ON b.booking_id = bp.booking_id
                                 JOIN 
                             "user" u ON bp.user_id = u.user_id
                                 JOIN
                             room r ON b.room_id = r.room_id
                                 JOIN
                             place p ON r.place_id = p.place_id -- Đây là place_id của floor
                                 LEFT JOIN
                             room pr ON b.previous_room_id = pr.room_id
                                 -- Join để lấy thông tin branch và building từ parent_id của place
                                 LEFT JOIN
                             place bl ON p.parent_id = bl.place_id AND bl.place_type = 'BUILDING'
                                LEFT JOIN
                             place br ON bl.parent_id = br.place_id AND br.place_type = 'BRANCH'
                        WHERE b.meeting_date BETWEEN :startDate AND :endDate
                             AND bp.user_id = :userId
                             AND (:status IS NULL OR b.status = :status)
            """,
            countQuery = """
                    SELECT COUNT(b.booking_id) FROM booking b
                    JOIN booking_participant bp ON b.booking_id = bp.booking_id
                    JOIN \"user\" u ON bp.user_id = u.user_id
                    WHERE b.meeting_date BETWEEN :startDate AND :endDate
                    AND bp.user_id = :userId
                    AND (:status IS NULL OR b.status = :status)
                    """,
            nativeQuery = true)
    Page<BookingProjection> findAllByMeetingDateBetweenAndUserIdAndStatus(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("userId") UUID userId,
            @Param("status") String status,
            Pageable pageable);


    @Query(value = """
                               SELECT
                                   b.booking_id AS id,
                                   b.title,
                                   b.description,
                                   b.booking_code,
            
                                   r.room_id,
                                   r.room_code,
                                   concat(bl.code, '.', p.code, r.room_code) AS room_name,
            
                                   br.place_id AS branch_id,
                                   br.code AS branch_code,
                                   br.name AS branch_name,
            
                                   bl.place_id AS building_id,
                                   bl.code AS building_code,
                                   bl.name AS building_name,
            
                                   p.place_id AS floor_id,
                                   p.code AS floor_code,
                                   p.name AS floor_name,
            
                                   pr.room_id AS room_previous_id,
            
                                   b.meeting_start,
                                   b.meeting_end,
                                   b.meeting_date,
                                   b.count,
                                   b.status,
                                   b.created_at,
                                   b.updated_at
                        FROM booking b
                                 JOIN
                             room r ON b.room_id = r.room_id
                                 JOIN
                             place p ON r.place_id = p.place_id 
                                 LEFT JOIN
                             room pr ON b.previous_room_id = pr.room_id
                                 LEFT JOIN
                             place bl ON p.parent_id = bl.place_id AND bl.place_type = 'BUILDING'
                                LEFT JOIN
                             place br ON bl.parent_id = br.place_id AND br.place_type = 'BRANCH'
                        WHERE b.meeting_date BETWEEN :startDate AND :endDate
                        AND (:status IS NULL OR b.status = :status)
            """,
            countQuery = """
                    SELECT COUNT(b.booking_id) FROM booking b
                    WHERE b.meeting_date BETWEEN :startDate AND :endDate
                    AND (:status IS NULL OR b.status = :status)
                    """,
            nativeQuery = true)
    Page<BookingProjection> findAllByMeetingDateBetweenAndStatus(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("status") String status,
            Pageable pageable);


    @Query(value = """
                               SELECT
                                   b.booking_id AS id,
                                   b.title,
                                   b.description,
                                   b.booking_code,
            
                                   r.room_id,
                                   r.room_code,
                                   concat(bl.code, '.', p.code, r.room_code) AS room_name,
            
                                   br.place_id AS branch_id,
                                   br.code AS branch_code,
                                   br.name AS branch_name,
            
                                   bl.place_id AS building_id,
                                   bl.code AS building_code,
                                   bl.name AS building_name,
            
                                   p.place_id AS floor_id,
                                   p.code AS floor_code,
                                   p.name AS floor_name,
            
                                   pr.room_id AS room_previous_id,
            
                                   b.meeting_start,
                                   b.meeting_end,
                                   b.meeting_date,
                                   b.count,
                                   b.status,
                                   b.created_at,
                                   b.updated_at
                        FROM booking b
                                 JOIN
                             room r ON b.room_id = r.room_id
                                 JOIN
                             place p ON r.place_id = p.place_id 
                                 LEFT JOIN
                             room pr ON b.previous_room_id = pr.room_id
                                 LEFT JOIN
                             place bl ON p.parent_id = bl.place_id AND bl.place_type = 'BUILDING'
                                LEFT JOIN
                             place br ON bl.parent_id = br.place_id AND br.place_type = 'BRANCH'
                        WHERE b.meeting_date BETWEEN :startDate AND :endDate
                         AND (:statusList IS NULL OR b.status IN (:statusList))
            """,
            countQuery = """
                    SELECT COUNT(b.booking_id) FROM booking b
                    WHERE b.meeting_date BETWEEN :startDate AND :endDate
                    AND (:statusList IS NULL OR b.status IN (:statusList))
                    """,
            nativeQuery = true)
    Page<BookingProjection> findAllByMeetingDateBetweenAndStatusList(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("statusList") List<String> statusList,
            Pageable pageable);
}
