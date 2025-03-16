package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.domain.model.entity.EquipmentRequest;
import com.roomx.domain.model.vo.EquipmentRequestId;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.EquipmentRequestEntity;
import com.roomx.infrastructure.multitenancy.persistence.model.ids.EquipmentRequestEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaEquipmentRequestEntityRepository extends JpaRepository<EquipmentRequestEntity, EquipmentRequestEntityId> {

    Optional<EquipmentRequestEntity> findByEquipmentId(UUID equipmentId);

    Optional<EquipmentRequestEntity> findByBookingRequestId(UUID bookingRequestId);
}
