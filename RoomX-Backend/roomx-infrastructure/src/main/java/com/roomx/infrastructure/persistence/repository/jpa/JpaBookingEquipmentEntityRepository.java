package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.infrastructure.persistence.model.entity.BookingEquipmentEntity;
import com.roomx.infrastructure.persistence.model.ids.BookingEquipmentEntityId;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaBookingEquipmentEntityRepository extends JpaRepository<BookingEquipmentEntity, BookingEquipmentEntityId> {

    Optional<BookingEquipmentEntity> findByBookingId(UUID bookingId);

    Optional<BookingEquipmentEntity> findByEquipmentId(UUID equipmentId);

    List<BookingEquipmentEntity> findAllByBookingId(UUID bookingId);
}
