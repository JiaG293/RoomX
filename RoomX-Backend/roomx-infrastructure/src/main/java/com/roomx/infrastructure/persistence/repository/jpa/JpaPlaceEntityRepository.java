package com.roomx.infrastructure.persistence.repository.jpa;

import com.roomx.infrastructure.persistence.model.dto.PlaceDto;
import com.roomx.infrastructure.persistence.model.entity.PlaceEntity;
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

    Optional<PlaceEntity> findByNameAndPlaceTypeAndBranchId(String name, String placeType, UUID branchId);

    Optional<PlaceEntity> findByPlaceTypeAndBranchId(String placeType, UUID branchId);

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
            p.id, p.name, p.layout, p.placeType, p.parentId, p.code, p.status, p.branch.id)
        FROM PlaceEntity p
        WHERE p.placeType = :placeType AND p.status = :status AND p.branch.id = :branchId
    """)
    Optional<PlaceDto> findByPlaceTypeAndStatusAndBranchIdCustom(String placeType, String status, UUID branchId);

    Optional<PlaceEntity> findByPlaceTypeAndStatusAndBranchId(String placeType, String status, UUID branchId);
}
