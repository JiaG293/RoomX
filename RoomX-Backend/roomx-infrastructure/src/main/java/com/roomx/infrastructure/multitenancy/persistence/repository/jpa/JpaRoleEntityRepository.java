package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


public interface JpaRoleEntityRepository extends JpaRepository<RoleEntity, String> {

    @Query("""
                SELECT r FROM RoleEntity r
                WHERE NOT EXISTS (
                SELECT 1 FROM UserEntity u JOIN u.roles ur WHERE u.id = :userId AND ur = r
                )
            """)
    List<RoleEntity> findRolesNotAssignedUser(@Param("userId") UUID userId);
}
