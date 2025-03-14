package com.roomx.application.service.resource;

import com.roomx.application.dto.resource.request.*;
import com.roomx.application.dto.resource.response.EquipmentResponse;
import com.roomx.application.dto.resource.response.PlaceResponse;
import com.roomx.application.dto.resource.response.ServiceResponse;
import com.roomx.application.mapper.PlaceAppMapper;
import com.roomx.domain.repository.BranchRepository;
import com.roomx.domain.repository.PlaceRepository;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceAppService {
    private final PlaceRepository placeRepository;
    private final BranchRepository branchRepository;
    private final PlaceAppMapper placeAppMapper;

    @Transactional
    public PlaceResponse createPlace(PlaceCreateRequest request) {

        if (placeRepository.checkPlaceExistsBySlugBuildingFloorBranchId(
                request.getSlug(),
                request.getBuilding(),
                request.getFloor(),
                request.getBranchId())) {
            throw new AppException(ErrorCode.PLACE_CONFLICT,
                    request.getSlug(),
                    request.getBuilding(),
                    request.getFloor(),
                    request.getBranchId());
        }

        if (placeRepository.checkPlaceExistsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.PLACE_SLUG_CONFLICT, request.getSlug());
        }

        var placeDomain = placeAppMapper.toDomain(request);

        if (request.getBranchId() != null) {
            var branchDomain = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new AppException(ErrorCode.BRANCH_NOT_FOUND, request.getBranchId()));
            placeDomain.setBranch(branchDomain);
        }

        var savedPlace = placeRepository.save(placeDomain);

        return placeAppMapper.toResponse(savedPlace);
    }

    @Transactional
    public PlaceResponse updatePlaceById(String placeId, PlaceUpdateRequest request) {
        var placeDomain = placeRepository.findById(placeId)
                .orElseThrow(() -> new AppException(ErrorCode.PLACE_NOT_FOUND, placeId));

        if (request.getBranchId() != null) {
            var branchDomain = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new AppException(ErrorCode.BRANCH_NOT_FOUND, request.getBranchId()));
            placeDomain.setBranch(branchDomain);
        }

        placeAppMapper.updateDomainFromDto(request, placeDomain);

        var savedPlace = placeRepository.save(placeDomain);
        return placeAppMapper.toResponse(savedPlace);
    }

    public List<String> getListPlaceSelectBox(PlaceSelectBoxRequest request){
        log.info("place type : {}",request.getPlaceType());
        return placeRepository.customFindPlaceSelectBox(
                request.getBranchId(),
                request.getBuilding(),
                request.getFloor(),
                request.getPlaceType()
        );
    }


}
