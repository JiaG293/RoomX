package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.ServiceEntity;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaServiceEntityRepository extends JpaRepository<ServiceEntity, UUID> {

    boolean existsByName(String serviceName);

    Optional<ServiceEntity> findByName(String serviceName);

    Page<ServiceEntity> findAll(Specification<ServiceEntity> spec, Pageable pageable);

    boolean existsByServiceCode(String serviceCode);

    Optional<ServiceEntity> findByServiceCode(String serviceCode);
}
