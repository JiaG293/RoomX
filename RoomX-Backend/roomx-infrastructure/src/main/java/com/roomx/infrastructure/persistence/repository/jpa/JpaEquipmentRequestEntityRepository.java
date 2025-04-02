package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.infrastructure.persistence.model.entity.EquipmentRequestEntity;
import com.roomx.infrastructure.persistence.model.ids.EquipmentRequestEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface JpaEquipmentRequestEntityRepository extends JpaRepository<EquipmentRequestEntity, EquipmentRequestEntityId> {

    Optional<EquipmentRequestEntity> findByEquipmentId(UUID equipmentId);

    Optional<EquipmentRequestEntity> findByBookingRequestId(UUID bookingRequestId);
}
