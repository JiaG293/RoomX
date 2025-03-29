package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;


import com.roomx.infrastructure.multitenancy.persistence.model.entity.BookingEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
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
}
