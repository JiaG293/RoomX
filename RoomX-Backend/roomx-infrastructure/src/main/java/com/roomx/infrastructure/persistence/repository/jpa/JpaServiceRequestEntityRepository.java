package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.infrastructure.persistence.model.entity.ServiceRequestEntity;
import com.roomx.infrastructure.persistence.model.ids.ServiceRequestEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface JpaServiceRequestEntityRepository extends JpaRepository<ServiceRequestEntity, ServiceRequestEntityId> {
    Optional<ServiceRequestEntity> findByServiceId(UUID serviceId);

    Optional<ServiceRequestEntity> findByBookingRequestId(UUID bookingRequestId);
}
