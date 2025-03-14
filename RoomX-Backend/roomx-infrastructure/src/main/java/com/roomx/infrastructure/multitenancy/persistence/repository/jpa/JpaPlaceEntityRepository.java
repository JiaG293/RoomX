package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaPlaceEntityRepository extends JpaRepository<PlaceEntity, UUID> {
    Optional<PlaceEntity> findBySlug(String slug);

    Optional<PlaceEntity> findByName(String name);

    boolean existsBySlug(String slug);

    boolean existsBySlugAndBuildingAndFloorAndAndBranch_Id(String slug, String building, String floor, UUID branchId);

    @Query("""
            SELECT DISTINCT p.floor FROM PlaceEntity p 
            WHERE p.building = :building AND p.placeType = :placeType
            """)
    List<String> customFindPlaceFloorByBuilding(String floor, String placeType);

    @Query("""
            SELECT DISTINCT p.floor FROM PlaceEntity p 
            WHERE p.branch.id = :branchId AND p.placeType = :placeType
            """)
    List<String> customFindPlaceBuildingByBranchId(UUID branchId, String placeType);

    @Query("""
                SELECT
                    CASE 
                        WHEN :floor IS NOT NULL THEN FUNCTION('TEXT', p.id)
                        WHEN :building IS NOT NULL THEN p.floor
                        WHEN :branchId IS NOT NULL THEN p.building
                        ELSE FUNCTION('TEXT', p.branch.id)
                    END 
                FROM PlaceEntity p 
                WHERE (:branchId IS NULL OR p.branch.id = :branchId)
                AND (:building IS NULL OR p.building = :building)
                AND (:floor IS NULL OR p.floor = :floor)
                AND (:placeType IS NULL OR p.placeType = :placeType)
                GROUP BY p.id, p.floor, p.building, p.branch.id
            """)
    List<String> customFindPlaceSelectBox(UUID branchId, String building, String floor, String placeType);

}
