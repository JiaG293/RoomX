package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.infrastructure.persistence.model.dto.PlaceDto;
import com.roomx.infrastructure.persistence.model.entity.PlaceEntity;
import com.roomx.infrastructure.persistence.model.projection.PlaceBranchProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface JpaPlaceEntityRepository extends JpaRepository<PlaceEntity, UUID>, JpaSpecificationExecutor<PlaceEntity> {
//    Optional<PlaceEntity> findBySlug(String slug);
//    boolean existsBySlugAndBuildingAndFloorAndAndBranch_Id(String slug, String building, String floor, UUID branchId);

//    Page<PlaceEntity> findAll(Specification<PlaceEntity> spec, Pageable pageable);

    Optional<PlaceEntity> findByName(String name);

    Optional<PlaceEntity> findByNameAndPlaceTypeAndId(String name, String placeType, UUID branchId);

    Optional<PlaceEntity> findByPlaceTypeAndCode(String placeType, String code);

    Optional<PlaceEntity> findByPlaceTypeAndParentId(String placeType, UUID uuid);

    List<PlaceEntity> findAllByStatus(String status);

    Optional<PlaceEntity> findByPlaceTypeAndParentIdAndCode(String placeType, UUID uuid, String code);

    Optional<PlaceEntity> findAllByPlaceTypeAndStatus(String placeType, String status);

    @Query("SELECT p FROM PlaceEntity p WHERE p.parentId IS NULL")
    List<PlaceEntity> findRootPlaces();

    @Query("SELECT p FROM PlaceEntity p WHERE p.placeType <> :placeType")
    List<PlaceEntity> findChildren(@Param("placeType") String placeType);

    @Query("""
        SELECT new com.roomx.infrastructure.persistence.model.dto.PlaceDto(
            p.id, p.name, p.layout, p.placeType, p.parentId, p.code, p.status, p.id)
        FROM PlaceEntity p
        WHERE p.placeType = :placeType AND p.status = :status AND p.id = :branchId
    """)
    Optional<PlaceDto> findByPlaceTypeAndStatusAndBranchIdCustom(String placeType, String status, UUID branchId);

    Optional<PlaceEntity> findByPlaceTypeAndStatusAndId(String placeType, String status, UUID branchId);

    Optional<PlaceEntity> findByIdAndStatus(UUID id, String status);

    Optional<PlaceEntity> findByPlaceTypeAndCodeAndStatus(String placeType, String code, String status);

    Optional<PlaceEntity> findByCode(String code);

    Optional<PlaceEntity> findByPlaceTypeAndCodeAndParentId(String type, String code, UUID uuid);

    Optional<PlaceEntity> findByIdAndStatusAndPlaceType(UUID placeId, String status, String placeType);

    Optional<PlaceEntity> findByIdAndPlaceTypeAndCode(UUID id, String placeType, String code);


    @Query(
            value =
                    """
                    SELECT
                    g.group_id AS groupId,
                    g.name AS name,
                    g.group_type AS groupType,
                    g.branch_id AS id,
                    g.user_id AS createBy,
                    g.group_code AS groupCode,
                    g.status AS status,
                    gm.user_id AS memberId 
                    FROM \"group\" g
                    LEFT JOIN group_member gm ON gm.group_id = g.group_id
                    WHERE gm.user_id = :userId
                    AND group_type = :groupType
                    """,
            nativeQuery = true
    )
    Optional<PlaceBranchProjection> findBranchByMemberIdAndGroupType(
            @Param("userId") UUID userId,
            @Param("groupType") String groupType);
}
