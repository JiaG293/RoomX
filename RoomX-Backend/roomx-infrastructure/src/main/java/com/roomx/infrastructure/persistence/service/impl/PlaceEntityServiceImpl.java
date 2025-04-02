package com.roomx.infrastructure.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Branch;
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
    public Place createPlaceFloor(Branch branchDomain, PlaceCreateRequest request){
        var placeBuilding = placeRepository.findById(request.getParentId())
                .orElseThrow(() -> new AppException(ErrorCode.PLACE_NOT_FOUND));

        var checkPlaceFloorExist = placeRepository.findByPlaceTypeAndParentIdAndCode(PlaceType.FLOOR.toString(), request.getParentId(), request.getCode());

        if(checkPlaceFloorExist.isPresent()){
            throw new AppException(ErrorCode.PLACE_CONFLICT, checkPlaceFloorExist.get().getId());
        }

        var placeFloorDomain = Place.builder()
                .placeType(PlaceType.FLOOR.toString())
                .parentId(placeBuilding.getId())
                .branch(branchDomain)
                .code(request.getCode())
                .name(PlaceType.FLOOR.getDisplayName() + " " + request.getCode())
                .layout(request.getLayout())
                .status(DeleteStatusType.getDefaultString())
                .build();


        return placeRepository.save(placeFloorDomain);
    }


    @Transactional
    @Override
    public Place createPlaceBuilding(Branch branchDomain, PlaceCreateRequest request){
        var placeBranchDomain = placeRepository.findById(request.getParentId())
                .orElseThrow(() -> new AppException(ErrorCode.PLACE_NOT_FOUND));

        var checkPlaceBuildingExist = placeRepository.findByPlaceTypeAndParentIdAndCode(PlaceType.BUILDING.toString(), request.getParentId(), request.getCode());

        if(checkPlaceBuildingExist.isPresent()){
            throw new AppException(ErrorCode.PLACE_CONFLICT, checkPlaceBuildingExist.get().getId());
        }

        var placeBuildingDomain = Place.builder()
                .placeType(PlaceType.BUILDING.toString())
                .parentId(placeBranchDomain.getId())
                .branch(branchDomain)
                .code(request.getCode())
                .name(PlaceType.BUILDING.getDisplayName() + " " + request.getCode())
                .layout(request.getLayout())
                .status(DeleteStatusType.getDefaultString())
                .build();
        return placeRepository.save(placeBuildingDomain);
    }

    @Override
    public Place createPlaceBranch(Branch branchDomain, PlaceCreateRequest request) {
        var placeBranchDomain = placeRepository
                .findByPlaceTypeAndBranchId(request.getPlaceType(), request.getBranchId());

        if(placeBranchDomain.isPresent()){
            throw new AppException(ErrorCode.PLACE_CONFLICT, request.getBranchId());
        }

        var placeDomain = Place.builder()
                .placeType(PlaceType.BRANCH.toString())
                .branch(branchDomain)
                .name(branchDomain.getName())
                .code(branchDomain.getBranchCode())
                .layout(request.getLayout())
                .status(DeleteStatusType.getDefaultString())
                .build();
        return placeRepository.save(placeDomain);
    }
}
