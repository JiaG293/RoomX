package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.EquipmentEntity;
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
public interface JpaEquipmentEntityRepository extends JpaRepository<EquipmentEntity, UUID> {

    Optional<EquipmentEntity> findByEquipmentCode(String equipmentCode);

    Page<EquipmentEntity> findAll(Specification<ServiceEntity> spec, Pageable pageable);
}
