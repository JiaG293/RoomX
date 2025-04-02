package com.roomx.infrastructure.persistence.repository.jpa;


import com.roomx.infrastructure.persistence.model.entity.BookingParticipantEntity;
import com.roomx.infrastructure.persistence.model.ids.BookingParticipantEntityId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaBookingParticipantEntityRepository extends JpaRepository<BookingParticipantEntity, BookingParticipantEntityId> {
    List<BookingParticipantEntity> findAllByBookingId(UUID bookingId);
    List<BookingParticipantEntity> findAllByUserId(UUID userId);
}
