package com.roomx.infrastructure.multitenancy.persistence.repository.jpa;

import com.roomx.infrastructure.multitenancy.persistence.model.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaPlaceEntityRepository extends JpaRepository<PlaceEntity, UUID> {
    Optional<PlaceEntity> findBySlug(String slug);

    Optional<PlaceEntity> findByName(String name);

    boolean existsBySlug(String slug);

}
