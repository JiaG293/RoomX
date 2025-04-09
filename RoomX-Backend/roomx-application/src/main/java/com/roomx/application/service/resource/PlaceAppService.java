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
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

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

        var checkBuilding = placeRepository
                .findByPlaceTypeAndCodeAndParentId(placeBuildingDomain.getPlaceType(), placeBuildingDomain.getCode(), placeBranchDomain.getId().toString());
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
    public PlaceResponse updatePlaceById(String placeId, String type, PlaceUpdateRequest request) {
        String placeType = null;
        if(type == null || type.isEmpty()){
            throw  new AppException(ErrorCode.PLACE_INVALID, type);
        } else {
            placeType = type.toUpperCase();
        }

        return switch (PlaceType.valueOf(placeType)) {
            case FLOOR -> updateFloorById(placeId, request);
            case BUILDING -> updateBuildingById(placeId, request);
            case BRANCH -> updateBranchById(placeId, request);
        };
    }

    @Transactional
    public PlaceResponse updateFloorById(String placeId, PlaceUpdateRequest request) {
        var placeDomain = findActivePlace(placeId, PlaceType.FLOOR.toString());

        placeAppMapper.updateDomainFromDto(request, placeDomain);

        validateCodeConflict(placeDomain, request.getCode(), placeId);

        if (StringUtils.hasText(request.getCode())) {
            placeDomain.setCode(request.getCode());
            placeDomain.setName(PlaceType.FLOOR.getDisplayName() + " " + request.getCode());
        }

        var saved = placeRepository.save(placeDomain);
        return placeAppMapper.toResponse(saved);
    }

    @Transactional
    public PlaceResponse updateBuildingById(String placeId, PlaceUpdateRequest request) {
        var placeDomain = findActivePlace(placeId, PlaceType.BUILDING.toString());

        placeAppMapper.updateDomainFromDto(request, placeDomain);

        validateCodeConflict(placeDomain, request.getCode(), placeId);

        if (StringUtils.hasText(request.getCode())) {
            placeDomain.setCode(request.getCode());
            placeDomain.setName(PlaceType.BUILDING.getDisplayName() + " " + request.getCode());
        }

        var saved = placeRepository.save(placeDomain);
        return placeAppMapper.toResponse(saved);
    }

    @Transactional
    public PlaceResponse updateBranchById(String placeId, PlaceUpdateRequest request) {
        var placeDomain = findActivePlace(placeId, PlaceType.BRANCH.toString());

        placeAppMapper.updateDomainFromDto(request, placeDomain);

        validateCodeConflict(placeDomain, request.getCode(), placeId);

        if (StringUtils.hasText(request.getCode())) {
            placeDomain.setCode(request.getCode());
            placeDomain.setName(PlaceType.BRANCH.getDisplayName() + " " + request.getCode());
        }

        var saved = placeRepository.save(placeDomain);
        return placeAppMapper.toResponse(saved);
    }

    private Place findActivePlace(String id, String placeType) {
        return placeRepository
                .findByIdAndStatusAndPlaceType(id, DeleteStatusType.ACTIVE.toString(), placeType)
                .orElseThrow(() -> new AppException(ErrorCode.PLACE_NOT_FOUND, placeType, id));
    }

    private void validateCodeConflict(Place placeDomain, String code, String placeId) {
        if (StringUtils.hasText(code)) {
            Optional<Place> checkPlace;

            if (placeDomain.getParentId() != null) {
                checkPlace = placeRepository.findByPlaceTypeAndCodeAndParentId(
                        placeDomain.getPlaceType(),
                        code,
                        placeDomain.getParentId().toString()
                );
            } else {
                checkPlace = placeRepository.findByPlaceTypeAndCode(
                        placeDomain.getPlaceType(),
                        code
                );
            }

            if (checkPlace.isPresent() && !checkPlace.get().getId().toString().equals(placeId)) {
                throw new AppException(ErrorCode.PLACE_CONFLICT, placeDomain.getPlaceType(), code);
            }
        }
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
        var places = new ArrayList<Place>();
        places.addAll(placeRepository.findRootPlace());
        places.addAll(placeRepository.findChildrenPlace(PlaceType.BRANCH.toString()));

        return placeAppMapper.buildHierarchy(places);
    }



    @Transactional
    public void deletePlaceById(String placeId) {
        var placeDomain = placeRepository.findById(placeId).orElse(null);
        if(placeDomain != null){
            placeDomain.setStatus(DeleteStatusType.INACTIVE.toString());
            placeRepository.save(placeDomain);
        }
    }


    public Map<String, PlaceResponse> getDetailPlaceById(String placeId) {
        Map<String, PlaceResponse> result = new LinkedHashMap<>();
        List<String> order = List.of("branch", "building", "floor");

        order.forEach(type -> result.put(type, null));

        Map<String, Place> cache = new HashMap<>();

        String currentId = placeId;
        while (currentId != null) {
            Place place = cache.computeIfAbsent(currentId, id ->
                    placeRepository.findById(id)
                            .orElseThrow(() -> new AppException(ErrorCode.PLACE_NOT_FOUND, id))
            );

            String type = place.getPlaceType().toLowerCase();
            if (result.containsKey(type)) {
                result.put(type, placeAppMapper.toResponse(place));
            }

            currentId = place.getParentId() != null ? place.getParentId().toString() : null;
        }

        return result;
    }
}
