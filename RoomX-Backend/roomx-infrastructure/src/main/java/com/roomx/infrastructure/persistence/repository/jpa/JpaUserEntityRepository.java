package com.roomx.infrastructure.persistence.repository.jpa;


import com.roomx.infrastructure.persistence.model.entity.UserEntity;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface JpaUserEntityRepository extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findByUserCode(String userCode);

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.roleId = :roleId")
    List<UserEntity> findAllByRoleId(String roleId);

    @EntityGraph(attributePaths = "roles")
    Optional<UserEntity> findByIdAndEnable(UUID id, boolean enabled);

    Optional<UserEntity> findByEmailAndEnable(String email, boolean enabled);

    @Query("SELECT u.id FROM UserEntity u WHERE u.email = :email")
    Optional<UUID> findByEmailCustom(@Param("email") String email);
}
