package com.roomx.infrastructure.persistence.repository.jpa;


import com.roomx.infrastructure.persistence.model.entity.GroupEntity;
import com.roomx.infrastructure.persistence.model.projection.GroupProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;


public interface JpaGroupEntityRepository extends JpaRepository<GroupEntity, UUID>, JpaSpecificationExecutor<GroupEntity> {

    Optional<GroupEntity> findByGroupCodeAndStatus(String groupCode, String status);


    @Query(
            value = """
                    
                            SELECT
                                           g.group_id    AS id,
                                           g.name        AS name,
                                           g.group_type  AS groupType,
                                           g.user_id     AS createdBy,
                                           g.group_code  AS groupCode,
                                           g.status      AS status,
                    
                                           uo.email      AS ownerEmail,
                                           uo.first_name AS ownerFirstName,
                                           uo.last_name  AS ownerLastName,
                                           uo.user_code  AS ownerUserCode,
                    
                                           p.name        AS branchName,
                                           p.code        AS branchCode,
                                           g.branch_id   AS branchId,
                    
                                           COUNT(um.user_id) AS quantityMember
                                       FROM "group" g
                                                LEFT JOIN group_member gm ON gm.group_id = g.group_id
                                                LEFT JOIN "user" uo ON uo.user_id = g.user_id
                                                JOIN "user" um ON um.user_id = gm.user_id
                                                LEFT JOIN place p ON g.branch_id = p.place_id
                    
                                       WHERE
                                       (
                                             (:isAdmin = true AND :viewAsUser = false AND g.group_type IN ('DEPARTMENT', 'PARTNER'))
                                             OR
                                             (
                                                 ((:isAdmin = true AND :viewAsUser = true) OR :isAdmin = false)
                                                 AND (
                                                     g.group_type IN ('DEPARTMENT', 'PARTNER')
                                                     OR (g.group_type = 'SELF' AND g.user_id = CAST(:userId AS UUID))
                                                 )
                                             )
                                         )
                                         AND (:status IS NULL OR g.status = :status)
                                         AND (:groupType IS NULL OR g.group_type = :groupType)
                                         AND (:branchId IS NULL OR g.branch_id = CAST(:branchId AS UUID))
                                         AND (
                                           :keyword IS NULL OR (
                                               (
                                                   :searchBy = 'name' AND g.name ILIKE '%' || :keyword || '%'
                                                   ) OR (
                                                   :searchBy = 'id' AND ( :keyword ~* '^[0-9a-fA-F-]{36}$'  AND g.group_id = CAST(:keyword AS UUID))
                                                   ) OR (
                                                   :searchBy = 'groupCode' AND g.group_code ILIKE '%' || :keyword || '%'
                                                   ) OR (
                                                   :searchBy = 'email' AND uo.email ILIKE '%' || :keyword || '%'
                                                   ) OR (
                                                   :searchBy IS NULL AND (
                                                       g.name ILIKE '%' || :keyword || '%' OR
                                                       g.group_code ILIKE '%' || :keyword || '%' OR
                                                       uo.email ILIKE '%' || :keyword || '%' OR
                                                       p.name ILIKE '%' || :keyword || '%' OR
                                                       p.code ILIKE '%' || :keyword || '%'
                                                       )
                                                   )
                                               )
                                           )
                                       GROUP BY g.group_id, g.name, g.group_type, g.user_id,
                                                g.group_code, g.status, uo.email, uo.first_name,
                                                uo.last_name, uo.user_code, p.name, p.code, g.branch_id
                    """,
            countQuery = """
                SELECT COUNT(DISTINCT g.group_id)
                FROM "group" g
                LEFT JOIN group_member gm ON gm.group_id = g.group_id
                LEFT JOIN "user" uo ON uo.user_id = g.user_id
                JOIN "user" um ON um.user_id = gm.user_id
                LEFT JOIN place p ON g.branch_id = p.place_id
                WHERE
                (
                    (:isAdmin = true AND :viewAsUser = false AND g.group_type IN ('DEPARTMENT', 'PARTNER'))
                    OR
                    (
                        ((:isAdmin = true AND :viewAsUser = true) OR :isAdmin = false)
                        AND (
                            g.group_type IN ('DEPARTMENT', 'PARTNER')
                            OR (g.group_type = 'SELF' AND g.user_id = CAST(:userId AS UUID))
                        )
                    )
                )
                AND (:status IS NULL OR g.status = :status)
                AND (:groupType IS NULL OR g.group_type = :groupType)
                AND (:branchId IS NULL OR g.branch_id = CAST(:branchId AS UUID))
                AND (
                    :keyword IS NULL OR (
                        (:searchBy = 'name' AND g.name ILIKE '%' || :keyword || '%')
                        OR (:searchBy = 'email' AND uo.email ILIKE '%' || :keyword || '%')
                        OR (:searchBy = 'id' AND ( :keyword ~* '^[0-9a-fA-F-]{36}$'  AND g.group_id = CAST(:keyword AS UUID)))
                        OR (:searchBy = 'groupCode' AND g.group_code ILIKE '%' || :keyword || '%')
                        OR (:searchBy IS NULL AND (
                            g.name ILIKE '%' || :keyword || '%'
                            OR g.group_code ILIKE '%' || :keyword || '%'
                            OR um.email ILIKE '%' || :keyword || '%'
                            OR p.name ILIKE '%' || :keyword || '%'
                            OR p.code ILIKE '%' || :keyword || '%'
                        ))
                    )
                )
    """, nativeQuery = true
    )
    Page<GroupProjection> findGroupWithFilters(
            @Param("keyword") String keyword,
            @Param("searchBy") String searchBy,
            @Param("status") String status,
            @Param("groupType") String groupType,
            @Param("branchId") String branchId,
            @Param("userId") String userId,
            @Param("isAdmin") boolean isAdmin,
            @Param("viewAsUser") boolean viewAsUser,
            Pageable pageable
    );


}