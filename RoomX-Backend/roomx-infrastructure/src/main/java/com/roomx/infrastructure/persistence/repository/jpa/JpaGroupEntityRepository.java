package com.roomx.infrastructure.persistence.repository.jpa;


import com.roomx.infrastructure.persistence.model.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;


public interface JpaGroupEntityRepository extends JpaRepository<GroupEntity, UUID>, JpaSpecificationExecutor<GroupEntity> {

    Optional<GroupEntity> findByGroupCodeAndStatus(String groupCode, String status);
}
