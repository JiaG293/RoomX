package com.roomx.infrastructure.multitenancy.persistence.repository;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoleEntityRepository extends JpaRepository<RoleEntity, String> {

}
