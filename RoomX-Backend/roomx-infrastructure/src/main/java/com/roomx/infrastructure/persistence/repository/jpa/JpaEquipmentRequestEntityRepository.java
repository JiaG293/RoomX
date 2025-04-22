package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.infrastructure.persistence.model.entity.EquipmentRequestEntity;
import com.roomx.infrastructure.persistence.model.ids.EquipmentRequestEntityId;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface JpaEquipmentRequestEntityRepository extends JpaRepository<EquipmentRequestEntity, EquipmentRequestEntityId> {

    Optional<EquipmentRequestEntity> findByEquipmentId(UUID equipmentId);

    Optional<EquipmentRequestEntity> findByBookingRequestId(UUID bookingRequestId);

    List<EquipmentRequestEntity> findAllByBookingRequestId(UUID bookingRequestId);
}
