package com.roomx.infrastructure.persistence.repository.jpa;


import aj.org.objectweb.asm.commons.Remapper;
import com.roomx.infrastructure.persistence.model.entity.UserEntity;

import com.roomx.infrastructure.persistence.model.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(
            value = """
                    WITH user_group AS (
                        SELECT DISTINCT ON (gm.user_id)
                            gm.user_id,
                            g.group_id,
                            g.name AS group_name,
                            g.group_type,
                            g.branch_id
                        FROM group_member gm
                                 JOIN "group" g ON gm.group_id = g.group_id
                        WHERE g.group_type = 'DEPARTMENT'
                        ORDER BY gm.user_id, g.group_type
                    )
                    SELECT
                        u.user_id AS id,
                        u.last_name AS lastName,
                        u.first_name AS firstName,
                        u.enable AS enable,
                        u.email AS email,
                        u.phone_number AS phoneNumber,
                        u.gender AS gender,
                        u.avatar_image AS avatarImage,
                        u.user_type AS userType,
                        u.user_code AS userCode,
                        u.status AS status,
                        ug.group_id AS groupId,
                        ug.group_name AS groupName,
                        ug.group_type AS groupType,
                        ug.branch_id AS branchId,
                        p.name AS branchName,
                        
                        ARRAY_AGG(DISTINCT ur.role_id) AS arrayRoles
                    FROM "user" u
                             LEFT JOIN user_group ug ON u.user_id = ug.user_id
                             LEFT JOIN user_role ur ON u.user_id = ur.user_id
                             LEFT JOIN place p ON ug.branch_id = p.place_id
                    WHERE (
                        :keyword IS NULL OR
                        (
                            (
                                (:searchBy = 'id') AND u.user_id::text ILIKE '%' || :keyword || '%'
                                ) OR (
                                (:searchBy = 'email') AND u.email ILIKE '%' || :keyword || '%'
                                ) OR (
                                (:searchBy = 'userCode') AND u.user_code ILIKE '%' || :keyword || '%'
                                ) OR (
                                (:searchBy = 'fullName') AND (
                                    CONCAT(u.last_name, ' ', u.first_name) ILIKE '%' || :keyword || '%' OR
                                    CONCAT(u.first_name, ' ', u.last_name) ILIKE '%' || :keyword || '%'
                                    )
                                ) OR (
                                (:searchBy IS NULL OR :searchBy = '') AND (
                                    u.user_id::text ILIKE '%' || :keyword || '%' OR
                                    u.email ILIKE '%' || :keyword || '%' OR
                                    u.user_code ILIKE '%' || :keyword || '%' OR
                                    CONCAT(u.last_name, ' ', u.first_name) ILIKE '%' || :keyword || '%' OR
                                    CONCAT(u.first_name, ' ', u.last_name) ILIKE '%' || :keyword || '%'
                                    )
                                )
                            )
                        )
                      AND (:userType IS NULL OR u.user_type = :userType)
                      AND (:branchId IS NULL OR ug.branch_id::text = :branchId)
                      AND (:groupId IS NULL OR ug.group_id::text = :groupId)
                      AND (:enabled IS NULL OR u.enable = :enabled)
                    GROUP BY
                        u.user_id,
                        ug.group_id, ug.group_name, ug.group_type, ug.branch_id, p.name
                    """,
            countQuery = """
                    WITH user_group AS (
                        SELECT DISTINCT ON (gm.user_id)
                            gm.user_id,
                            g.group_id,
                            g.name AS group_name,
                            g.group_type,
                            g.branch_id
                        FROM group_member gm
                                 JOIN "group" g ON gm.group_id = g.group_id
                        WHERE g.group_type = 'DEPARTMENT'
                        ORDER BY gm.user_id, g.group_type
                    )
                    SELECT COUNT(DISTINCT u.user_id)
                    FROM "user" u
                             LEFT JOIN user_group ug ON u.user_id = ug.user_id
                             LEFT JOIN user_role ur ON u.user_id = ur.user_id
                             LEFT JOIN place p ON ug.branch_id = p.place_id
                    WHERE (
                        :keyword IS NULL OR
                        (
                            (
                                (:searchBy = 'id') AND u.user_id::text ILIKE '%' || :keyword || '%'
                            ) OR (
                                (:searchBy = 'email') AND u.email ILIKE '%' || :keyword || '%'
                            ) OR (
                                (:searchBy = 'userCode') AND u.user_code ILIKE '%' || :keyword || '%'
                            ) OR (
                                (:searchBy = 'fullName') AND (
                                    CONCAT(u.last_name, ' ', u.first_name) ILIKE '%' || :keyword || '%' OR
                                    CONCAT(u.first_name, ' ', u.last_name) ILIKE '%' || :keyword || '%'
                                )
                            ) OR (
                                (:searchBy IS NULL OR :searchBy = '') AND (
                                    u.user_id::text ILIKE '%' || :keyword || '%' OR
                                    u.email ILIKE '%' || :keyword || '%' OR
                                    u.user_code ILIKE '%' || :keyword || '%' OR
                                    CONCAT(u.last_name, ' ', u.first_name) ILIKE '%' || :keyword || '%' OR
                                    CONCAT(u.first_name, ' ', u.last_name) ILIKE '%' || :keyword || '%'
                                )
                            )
                        )
                    )
                      AND (:userType IS NULL OR u.user_type = :userType)
                      AND (:branchId IS NULL OR ug.branch_id::text = :branchId)
                      AND (:groupId IS NULL OR ug.group_id::text = :groupId)
                      AND (:enabled IS NULL OR u.enable = :enabled)
                    """,
            nativeQuery = true
    )
    Page<UserProjection> findUserWithFilters(
            @Param("keyword") String keyword,
            @Param("searchBy") String searchBy,
            @Param("branchId") String branchId,
            @Param("groupId") String groupId,
            @Param("userType") String userType,
            @Param("enabled") Boolean enabled,
            @Param("roles") List<String> roles,
            @Param("pageable") Pageable pageable
    );
}
