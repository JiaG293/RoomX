package com.roomx.infrastructure.multitenancy.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.Place;
import com.roomx.shared.enums.DeleteStatusType;
import com.roomx.domain.repository.PlaceRepository;
import com.roomx.infrastructure.multitenancy.persistence.mapper.PlaceEntityMapper;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaPlaceEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class PlaceEntityRepository implements PlaceRepository {
    private final JpaPlaceEntityRepository jpaPlaceEntityRepository;
    private final PlaceEntityMapper placeEntityMapper;


    @Override
    public Optional<Place> findById(String id) {
        return jpaPlaceEntityRepository
                .findById(UUID.fromString(id))
                .map(placeEntityMapper::toDomain);
    }


    @Override
    public Optional<Place> findByName(String name) {
        return jpaPlaceEntityRepository
                .findByName(name)
                .map(placeEntityMapper::toDomain);
    }

    @Override
    public Place save(Place place) {
        var placeEntity = placeEntityMapper.toEntity(place);

        var savedPlaceEntity = jpaPlaceEntityRepository.save(placeEntity);

        return placeEntityMapper.toDomain(savedPlaceEntity);
    }

    @Override
    public Optional<Place> findByNameAndPlaceTypeAndBranchId(String name, String placeType, String branchId) {
        return jpaPlaceEntityRepository
                .findByNameAndPlaceTypeAndBranchId(name, placeType, UUID.fromString(branchId))
                .map(placeEntityMapper::toDomain);
    }

    @Override
    public Optional<Place> findByPlaceTypeAndBranchId(String placeType, String branchId) {
        return jpaPlaceEntityRepository
                .findByPlaceTypeAndBranchId(placeType, UUID.fromString(branchId))
                .map(placeEntityMapper::toDomain);
    }

    @Override
    public List<Place> saveAll(List<Place> listPlace) {
        var placeEntityList = listPlace.stream().map(placeEntityMapper::toEntity).toList();
        var savedPlaceEntityList = jpaPlaceEntityRepository.saveAll(placeEntityList);
        return savedPlaceEntityList.stream().map(placeEntityMapper::toDomain).toList();
    }

    @Override
    public Optional<Place> findByPlaceTypeAndParentId(String placeType, String parentId) {
        return jpaPlaceEntityRepository
                .findByPlaceTypeAndParentId(placeType, UUID.fromString(parentId))
                .map(placeEntityMapper::toDomain);
    }

    @Override
    public List<Place> findAll() {
        return jpaPlaceEntityRepository
                .findAllByStatus(DeleteStatusType.ACTIVE.toString())
                .stream().map(placeEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Place> findByPlaceTypeAndParentIdAndCode(String placeType, String parentId, String code) {
        return jpaPlaceEntityRepository
                .findByPlaceTypeAndParentIdAndCode(placeType, UUID.fromString(parentId), code)
                .map(placeEntityMapper::toDomain);
    }

    @Override
    public Optional<Place> findAllByPlaceTypeAndStatus(String placeType, String status) {
        return jpaPlaceEntityRepository
                .findAllByPlaceTypeAndStatus(placeType, status)
                .map(placeEntityMapper::toDomain);
    }

    @Override
    public List<Place> findRootPlace() {
        return jpaPlaceEntityRepository
                .findRootPlaces()
                .stream().map(placeEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Place> findChildrenPlace(String notPlaceType) {
        return jpaPlaceEntityRepository
                .findChildren(notPlaceType)
                .stream().map(placeEntityMapper::toDomain)
                .toList();
    }
}
