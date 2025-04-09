/*
package com.roomx.application.service.resource;

import com.roomx.application.mapper.PlaceAppMapper;
import com.roomx.domain.model.aggrerate.Place;
import com.roomx.domain.repository.PlaceRepository;
import com.roomx.infrastructure.persistence.service.PlaceEntityService;
import com.roomx.shared.base.filter.BranchFilter;
import com.roomx.shared.dto.resource.request.*;
import com.roomx.shared.dto.resource.response.BranchDetailResponse;
import com.roomx.shared.dto.resource.response.BranchResponse;
import com.roomx.shared.enums.DeleteStatusType;
import com.roomx.shared.enums.PlaceType;
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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchAppService {
    private final PlaceRepository placeRepository;
    private final PlaceAppMapper placeAppMapper;
    private final PlaceEntityService placeEntityService;

    @Transactional
    public BranchResponse createBranch(BranchCreateRequest request) {
        var branchDomain = branchAppMapper.toDomain(request);

        branchDomain.setStatus(DeleteStatusType.getDefaultString());

        var checkExistBranchCode = branchRepository.checkBranchCodeExists(request.getBranchCode());

        if (checkExistBranchCode) {
            throw new AppException(ErrorCode.BRANCH_CONFLICT, request.getBranchCode());
        }
        var savedBranch = branchRepository.save(branchDomain);

        var savedPlaceBranch = placeRepository.save(Place.builder()
                .placeType(PlaceType.BRANCH.toString())
                .branch(savedBranch)
                .name(savedBranch.getName())
                .code(savedBranch.getBranchCode())
                .build());
        var response = branchAppMapper.toResponse(savedBranch);
        response.setPlaceId(savedPlaceBranch.getId().toString());


        return response;
    }

    @Transactional
    public BranchResponse updateBranchById(String branchId, BranchUpdateRequest request) {
        var branchDomain = branchRepository.findById(branchId)
                .orElseThrow(() -> new AppException(ErrorCode.BRANCH_NOT_FOUND, branchId));

        branchAppMapper.updateDomainFromDto(request, branchDomain);

        var savedBranchDomain = branchRepository.save(branchDomain);

        return branchAppMapper.toResponse(savedBranchDomain);

    }

    @Transactional
    public void deleteBranchById(String branchId) {
        var branchDomain = branchRepository.findById(branchId)
                .orElseThrow(() -> new AppException(ErrorCode.BRANCH_NOT_FOUND, branchId));

        branchDomain.setStatus(DeleteStatusType.INACTIVE.toString());
        branchRepository.save(branchDomain);
    }


    public Page<BranchResponse> getListBranchPages(
            BranchQueryRequest filterRequest,
            int page,
            int size,
            String sortBy,
            String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        log.info("data la: {}", filterRequest);
        BranchFilter branchFilter = BranchFilter.builder()
//                .branchCode(filterRequest.getBranchCode())
//                .name(filterRequest.getName())
//                .email(filterRequest.getEmail())
//                .address(filterRequest.getAddress())
//                .phoneNumber(filterRequest.getPhoneNumber())
                .build();

        var branchDomainPage = branchEntityService.filterPageBranchs(branchFilter, pageable, filterRequest.isTypeCompare());

        return branchDomainPage.map(branchAppMapper::toResponse);
    }

    public Page<BranchResponse> searchFilterBranch(
            BranchFilterRequest filter,
            int page,
            int size,
            String sortBy,
            String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        var branchFilter = BranchFilter.builder()
                .status(filter.status())
                .searchBy(filter.searchBy())
                .keyword(filter.keyword())
                .build();
        var branchDomainPage = branchEntityService
                .searchFilterBranch(branchFilter, pageable);

        return branchDomainPage.map(branchAppMapper::toResponse);
    }

    public Page<BranchResponse> searchBranchByKeyword(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        var branchDomainPage = branchEntityService
                .searchPageBranchByKeyword(keyword, pageable);

        return branchDomainPage.map(branchAppMapper::toResponse);
    }


    public List<BranchResponse> searchBranchByNameOrBranchCode(String name, String code) {
        var listBranchDomain = branchRepository.searchBranchByNameOrBranchCode(name, code);
        return listBranchDomain.stream().map(branchAppMapper::toResponse).toList();
    }

    public List<BranchResponse> getAllBranch() {
        var listBranchDomain = branchRepository.findAll();
        return listBranchDomain.stream().map(branchAppMapper::toResponse).toList();
    }


    public BranchDetailResponse getBranchDetail(String branchId) {
        var branchDomain = branchRepository.findById(branchId)
                .orElseThrow(() -> new AppException(ErrorCode.BRANCH_NOT_FOUND, branchId));

        var placeDomain = placeRepository
                .findByBranchIdAndPlaceTypeAndStatus(
                        branchId,
                        PlaceType.BRANCH.toString(),
                        DeleteStatusType.getDefaultString()
                );
        var response = branchAppMapper.toDetailResponse(branchDomain);
        if (placeDomain.isPresent()) {
            response.setPlaces(placeAppMapper.toResponseBranch(placeDomain.get()));
            response.getPlaces().setBranchId(branchDomain.getId().toString());
        }

        return response;
    }
}
*/
