package com.roomx.infrastructure.persistence.repository.jpa;


import com.github.loki4j.client.batch.LogRecord;
import com.roomx.infrastructure.persistence.model.entity.DateRequestExceptionEntity;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaDateRequestExceptionEntityRepository extends JpaRepository<DateRequestExceptionEntity, UUID>, JpaSpecificationExecutor<DateRequestExceptionEntity> {
    Optional<DateRequestExceptionEntity> findByBookingRequestId(UUID bookingRequestId);

    List<DateRequestExceptionEntity> findAllByBookingRequestId(UUID bookingRequestId);
}
