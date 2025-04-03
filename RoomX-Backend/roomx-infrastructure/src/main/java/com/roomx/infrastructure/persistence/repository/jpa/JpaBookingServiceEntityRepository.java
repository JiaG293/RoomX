package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.infrastructure.persistence.model.entity.BookingServiceEntity;
import com.roomx.infrastructure.persistence.model.ids.BookingServiceEntityId;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaBookingServiceEntityRepository extends JpaRepository<BookingServiceEntity, BookingServiceEntityId> {
    Optional<BookingServiceEntity> findByBookingId(UUID bookingId);

    Optional<BookingServiceEntity> findByServiceId(UUID serviceId);
}
