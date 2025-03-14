package com.roomx.application.service.resource;

import com.roomx.application.dto.resource.request.PlaceCreateRequest;
import com.roomx.application.dto.resource.request.ServiceCreateRequest;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceAppService {
    private final PlaceRepository placeRepository;
    private final BranchRepository branchRepository;
    private final PlaceAppMapper placeAppMapper;

//    @Transactional
    public PlaceResponse createPlace(PlaceCreateRequest request){

        if(placeRepository.checkPlaceExistsBySlug(request.getSlug())){
            throw new AppException(ErrorCode.PLACE_SLUG_NOT_FOUND, request.getSlug());
        }

        var placeDomain = placeAppMapper.toDomain(request);

        if(request.getBranchId() != null){
            var branchDomain = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new AppException(ErrorCode.BRANCH_NOT_FOUND, request.getBranchId()));
            placeDomain.setBranch(branchDomain);
        }

        var savedPlace = placeRepository.save(placeDomain);

        return placeAppMapper.toResponse(savedPlace);
    }
}
