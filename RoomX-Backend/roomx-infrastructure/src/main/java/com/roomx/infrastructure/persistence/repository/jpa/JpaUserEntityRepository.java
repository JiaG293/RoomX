package com.roomx.infrastructure.persistence.repository.jpa;


import aj.org.objectweb.asm.commons.Remapper;
import com.roomx.infrastructure.persistence.model.entity.UserEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


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

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_role WHERE user_id = :userId", nativeQuery = true)
    void deleleUserRole(@Param("userId") UUID userId);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByIdAndStatus(UUID id, String status);
}
