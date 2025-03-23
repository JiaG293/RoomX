package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;


import com.roomx.infrastructure.multitenancy.persistence.model.entity.ServicePriceHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;


public interface JpaServicePriceHistoryEntityRepository extends JpaRepository<ServicePriceHistoryEntity, UUID> {
}
