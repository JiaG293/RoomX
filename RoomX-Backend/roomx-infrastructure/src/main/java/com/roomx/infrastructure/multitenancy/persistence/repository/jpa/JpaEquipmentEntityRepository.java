package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;


import com.roomx.infrastructure.multitenancy.persistence.model.entity.EquipmentEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


public interface JpaEquipmentEntityRepository extends JpaRepository<EquipmentEntity, UUID>, JpaSpecificationExecutor<EquipmentEntity> {

    Optional<EquipmentEntity> findByEquipmentCode(String equipmentCode);

    Page<EquipmentEntity> findAll(Specification<EquipmentEntity> spec, Pageable pageable);

    Optional<EquipmentEntity> findByIdAndStatus(UUID uuid, String status);
}
