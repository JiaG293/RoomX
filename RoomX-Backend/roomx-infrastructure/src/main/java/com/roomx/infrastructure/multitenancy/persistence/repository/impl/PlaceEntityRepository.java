package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.Place;
import com.roomx.domain.repository.PlaceRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.PlaceEntityJpaMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaPlaceEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Repository
public class PlaceEntityRepository implements PlaceRepository {
    private final JpaPlaceEntityRepository jpaPlaceEntityRepository;
    private final PlaceEntityJpaMapper placeEntityJpaMapper;


    @Override
    public Optional<Place> findById(String id) {
        return jpaPlaceEntityRepository
                .findById(UUID.fromString(id))
                .map(placeEntityJpaMapper::toDomain);
    }

    @Override
    public Optional<Place> findBySlug(String slug) {
        return jpaPlaceEntityRepository
                .findBySlug(slug)
                .map(placeEntityJpaMapper::toDomain);
    }

    @Override
    public Optional<Place> findByName(String name) {
        return jpaPlaceEntityRepository
                .findByName(name)
                .map(placeEntityJpaMapper::toDomain);
    }

    @Override
    public Place save(Place place) {
        var placeEntity = placeEntityJpaMapper.toEntity(place);

        var savedPlaceEntity = jpaPlaceEntityRepository.save(placeEntity);

        return placeEntityJpaMapper.toDomain(savedPlaceEntity);
    }

    @Override
    public boolean checkPlaceExistsBySlug(String slug) {
        return jpaPlaceEntityRepository.existsBySlug(slug);
    }

    @Override
    public boolean checkPlaceExistsBySlugBuildingFloorBranchId(String slug, String building, String floor, String branchId) {
        return jpaPlaceEntityRepository.existsBySlugAndBuildingAndFloorAndAndBranch_Id(slug, building, floor, UUID.fromString(branchId));
    }

    @Override
    public List<String> customFindPlaceSelectBox(String branchId, String building, String floor, String placeType) {
        return jpaPlaceEntityRepository.customFindPlaceSelectBox(UUID.fromString(branchId), building, floor, placeType);
    }
}
