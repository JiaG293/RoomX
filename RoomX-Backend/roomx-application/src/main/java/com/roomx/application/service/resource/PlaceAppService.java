package com.roomx.application.service.resource;

import com.roomx.shared.dto.resource.request.*;
import com.roomx.shared.dto.resource.response.PlaceHierarchyResponse;
import com.roomx.shared.dto.resource.response.PlaceResponse;
import com.roomx.application.mapper.PlaceAppMapper;
import com.roomx.domain.model.aggrerate.Place;
import com.roomx.shared.enums.DeleteStatusType;
import com.roomx.shared.enums.PlaceType;
import com.roomx.domain.repository.PlaceRepository;
import com.roomx.infrastructure.persistence.dto.PlaceFilter;
import com.roomx.infrastructure.persistence.service.PlaceEntityService;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceAppService {
    private final PlaceRepository placeRepository;
    private final PlaceAppMapper placeAppMapper;
    private final PlaceEntityService placeEntityService;

    @Transactional
    public PlaceResponse createPlace(PlaceCreateRequest request) {
        Place savedPlace = null;

        switch (request.getPlaceType()) {
            case "BRANCH": {
                savedPlace = placeEntityService.createPlaceBranch(request);
                break;
            }
            case "BUILDING": {
                savedPlace = placeEntityService.createPlaceBuilding(request);
                break;
            }
            case "FLOOR": {
                savedPlace = placeEntityService.createPlaceFloor(request);
                break;
            }
            default:
                throw new AppException(ErrorCode.PLACE_INVALID);
        }

        return placeAppMapper.toResponse(savedPlace);

    }


    @Transactional
    public PlaceHierarchyResponse createPlaceBuildingWithFloors(String placeBranchIdOrCode, PlaceCreateBuildingWithFloorRequest request) {
        List<Place> placeAllDomain = new ArrayList<>();

        var placeBranchDomain = placeBranchIdOrCode.length() <= 32 ?
                placeRepository.findByCode(placeBranchIdOrCode)
                        .orElseThrow(() -> new AppException(ErrorCode.PLACE_NOT_FOUND, "code", placeBranchIdOrCode)) :
                placeRepository.findById(placeBranchIdOrCode)
                        .orElseThrow(() -> new AppException(ErrorCode.PLACE_NOT_FOUND, "id", placeBranchIdOrCode));

        var nameBuilding = PlaceType.BUILDING.getDisplayName() + " " + request.getCode();

        var placeBuildingDomain = Place.builder()
                .placeType(PlaceType.BUILDING.toString())
                .code(request.getCode())
                .name(nameBuilding)
                .layout(request.getLayout())
                .status(DeleteStatusType.ACTIVE.toString())
                .parentId(placeBranchDomain.getId())
                .build();

        var checkBuilding = placeRepository.findByPlaceTypeAndCode(placeBuildingDomain.getPlaceType(), placeBuildingDomain.getCode());
        if (checkBuilding.isPresent()) {
            placeBuildingDomain = checkBuilding.get();
        } else {
            placeBuildingDomain = placeRepository.save(placeBuildingDomain);
        }




        placeAllDomain.add(placeBuildingDomain);


        for (int i = 1; i <= request.getNumberFloor(); i++) {
            if (request.getExceptions() != null && request.getExceptions().contains(i)) continue;

            var layout = i <= request.getLayouts().size() ? request.getLayouts().get(i - 1) : null;
            var code = String.valueOf(i);

            var nameFloor = PlaceType.FLOOR.getDisplayName() + " " + i;
            PlaceCreateRequest floorRequest = PlaceCreateRequest.builder()
                    .placeType(PlaceType.FLOOR.toString())
                    .parentId(placeBuildingDomain.getId().toString())
                    .layout(layout)
                    .name(nameFloor)
                    .code(code)
                    .build();

            var savedPlaceFloorDomain = placeEntityService.createPlaceFloor(floorRequest);

            placeAllDomain.add(savedPlaceFloorDomain);
        }


        return placeAppMapper.toResponseHierarchy(placeBranchDomain, placeAllDomain);

    }


    @Transactional
    public PlaceResponse updatePlaceById(String placeId, PlaceUpdateRequest request) {
        var placeDomain = placeRepository.findById(placeId)
                .orElseThrow(() -> new AppException(ErrorCode.PLACE_NOT_FOUND, placeId));

        /*if (request.getBranchId() != null) {
            var branchDomain = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new AppException(ErrorCode.BRANCH_NOT_FOUND, request.getBranchId()));
            placeDomain.setBranch(branchDomain);
        }*/

        placeAppMapper.updateDomainFromDto(request, placeDomain);

        var savedPlace = placeRepository.save(placeDomain);
        return placeAppMapper.toResponse(savedPlace);
    }

    public List<String> getListPlaceSelectBox(PlaceSelectBoxRequest request) {
       /* log.info("place type : {}", request.getPlaceType());
        return placeRepository.customFindPlaceSelectBox(
                request.getBranchId(),
                request.getBuilding(),
                request.getFloor(),
                request.getPlaceType()
        );*/
        return null;
    }


    public Page<PlaceResponse> getListPlacePages(PlaceFilterRequest filter,
                                                 int page,
                                                 int size,
                                                 String sortBy,
                                                 String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        var placeFilter = PlaceFilter.builder()
                .keyword(filter.keyword())
                .searchBy(filter.searchBy())
                .placeType(filter.placeType())
                .status(filter.status())
                .build();

        var placeDomainPage = placeEntityService.filterSearchPagePlaces(placeFilter, pageable);

        return placeDomainPage.map(placeAppMapper::toResponse);
    }

    @Transactional
    public List<PlaceHierarchyResponse> getAllPlacesHierarchy() {
        var placeParentListDomain = placeRepository.findRootPlace();
        var placeChildrenListDomain = placeRepository.findChildrenPlace(PlaceType.BRANCH.toString());


        var listResponse = new ArrayList<PlaceHierarchyResponse>();


        List<Place> places = new ArrayList<>();
        places.addAll(placeParentListDomain);
        places.addAll(placeChildrenListDomain);


        for (Place place : places) {
            if (place.getParentId() == null) {

                listResponse.add(placeAppMapper.toResponseHierarchy(place, places));
            }
        }

        return listResponse;

    }


}
