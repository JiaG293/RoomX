package com.roomx.infrastructure.multitenancy.persistence.repository;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.PermissonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPermissionEntityRepository extends JpaRepository<PermissonEntity, String> {

}
