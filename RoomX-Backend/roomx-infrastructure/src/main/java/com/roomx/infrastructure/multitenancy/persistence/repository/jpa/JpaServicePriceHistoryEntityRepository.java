package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;


import com.roomx.infrastructure.multitenancy.persistence.model.entity.ServicePriceHistoryEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;
import java.util.UUID;


public interface JpaServicePriceHistoryEntityRepository extends JpaRepository<ServicePriceHistoryEntity, UUID> {
    @Query("""
                SELECT s FROM ServicePriceHistoryEntity s 
                WHERE s.service.id = :serviceId 
                ORDER BY s.validFrom DESC 
                LIMIT 1
            """)
    Optional<ServicePriceHistoryEntity> findLatestValidFrom(@Param("serviceId") UUID serviceId);
}
