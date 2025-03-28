package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.domain.model.vo.ServiceRequestId;
import com.roomx.infrastructure.multitenancy.persistence.model.entity.ServiceRequestEntity;
import com.roomx.infrastructure.multitenancy.persistence.model.ids.ServiceRequestEntityId;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


public interface JpaServiceRequestEntityRepository extends JpaRepository<ServiceRequestEntity, ServiceRequestEntityId> {
    Optional<ServiceRequestEntity> findByServiceId(UUID serviceId);

    Optional<ServiceRequestEntity> findByBookingRequestId(UUID bookingRequestId);
}
