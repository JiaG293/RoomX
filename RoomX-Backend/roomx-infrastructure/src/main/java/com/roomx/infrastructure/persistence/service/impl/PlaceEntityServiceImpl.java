package com.roomx.infrastructure.persistence.service.impl;


import com.roomx.domain.model.aggrerate.Place;
import com.roomx.infrastructure.persistence.repository.specification.PlaceSpecification;
import com.roomx.shared.enums.DeleteStatusType;
import com.roomx.shared.enums.PlaceType;
import com.roomx.domain.repository.PlaceRepository;
import com.roomx.infrastructure.persistence.dto.PlaceFilter;
import com.roomx.infrastructure.persistence.mapper.PlaceEntityMapper;
import com.roomx.infrastructure.persistence.model.base.GenericSpecification;
import com.roomx.infrastructure.persistence.model.base.SearchCriteria;
import com.roomx.infrastructure.persistence.model.entity.PlaceEntity;
import com.roomx.infrastructure.persistence.repository.jpa.JpaPlaceEntityRepository;
import com.roomx.infrastructure.persistence.service.PlaceEntityService;
import com.roomx.shared.dto.resource.request.PlaceCreateRequest;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceEntityServiceImpl implements PlaceEntityService {
    private final JpaPlaceEntityRepository jpaPlaceEntityRepository;
    private final PlaceEntityMapper placeEntityMapper;
    private final PlaceRepository placeRepository;

    @Override
    public Page<Place> filterSearchPagePlaces(PlaceFilter filter, Pageable pageable) {
        var spec = PlaceSpecification.searchFilterPlace(filter);
        var placeEntityPage = jpaPlaceEntityRepository.findAll(spec, pageable);

        return placeEntityPage.map(placeEntityMapper::toDomain);
    }

    @Transactional
    @Override
    public Place createPlaceFloor(PlaceCreateRequest request) {
        var placeBuilding = placeRepository.findById(request.getParentId())
                .orElseThrow(() -> new AppException(ErrorCode.PLACE_NOT_FOUND));

        var checkPlaceFloorExist = placeRepository
                .findByPlaceTypeAndParentIdAndCode(PlaceType.FLOOR.toString(), request.getParentId(), request.getCode());

        if (checkPlaceFloorExist.isPresent()) {
            throw new AppException(ErrorCode.PLACE_CONFLICT, request.getPlaceType(), request.getCode());
        }

        var nameFloor = request.getName() == null ?
                PlaceType.FLOOR.getDisplayName() + " " + request.getCode():
                request.getName();

        var placeFloorDomain = Place.builder()
                .placeType(PlaceType.FLOOR.toString())
                .parentId(placeBuilding.getId())
                .code(request.getCode())
                .name(nameFloor)
                .layout(request.getLayout())
                .status(DeleteStatusType.ACTIVE.toString())
                .build();


        return placeRepository.save(placeFloorDomain);
    }


    @Transactional
    @Override
    public Place createPlaceBuilding(PlaceCreateRequest request) {
        var placeBranchDomain = placeRepository.findByIdAndStatus(request.getParentId(), DeleteStatusType.ACTIVE.toString())
                .orElseThrow(() -> new AppException(ErrorCode.PLACE_NOT_FOUND, request.getPlaceType(), request.getParentId()));

        var checkPlaceBuildingExist = placeRepository.findByPlaceTypeAndParentIdAndCode(PlaceType.BUILDING.toString(), request.getParentId(), request.getCode());

        if (checkPlaceBuildingExist.isPresent()) {
            throw new AppException(ErrorCode.PLACE_CONFLICT, request.getPlaceType(), request.getCode());
        }

        var nameBuilding = request.getName() == null ?
                PlaceType.BUILDING.getDisplayName() + " " + request.getCode() :
                request.getName();

        var placeBuildingDomain = Place.builder()
                .placeType(PlaceType.BUILDING.toString())
                .parentId(placeBranchDomain.getId())
                .code(request.getCode())
                .name(nameBuilding)
                .layout(request.getLayout())
                .status(DeleteStatusType.getDefaultString())
                .build();
        return placeRepository.save(placeBuildingDomain);
    }

    @Transactional
    @Override
    public Place createPlaceBranch(PlaceCreateRequest request) {
        var placeBranchDomain = placeRepository
                .findByPlaceTypeAndCode(request.getPlaceType(), request.getCode());

        if (placeBranchDomain.isPresent()) {
            throw new AppException(ErrorCode.PLACE_CONFLICT, request.getPlaceType(), request.getCode());
        }

        var nameBranch = request.getName() == null ?
                PlaceType.BRANCH.getDisplayName() :
                request.getName();

        var placeDomain = Place.builder()
                .placeType(PlaceType.BRANCH.toString())
                .name(nameBranch)
                .code(request.getCode())
                .layout(request.getLayout())
                .status(DeleteStatusType.ACTIVE.toString())
                .parentId(null)
                .build();
        return placeRepository.save(placeDomain);
    }


}
