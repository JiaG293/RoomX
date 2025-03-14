package com.roomx.application.service.resource;

import com.roomx.application.dto.resource.request.BranchCreateRequest;

import com.roomx.application.dto.resource.request.BranchQueryFilterRequest;
import com.roomx.application.dto.resource.request.BranchUpdateRequest;
import com.roomx.application.dto.resource.response.BranchResponse;
import com.roomx.application.mapper.BranchAppMapper;
import com.roomx.domain.repository.BranchRepository;
import com.roomx.infrastructure.multitenancy.persistence.dto.BranchFilter;
import com.roomx.infrastructure.multitenancy.persistence.service.BranchEntityService;
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
    private final BranchRepository branchRepository;
    private final BranchAppMapper branchAppMapper;
    private final BranchEntityService branchEntityService;

    @Transactional
    public BranchResponse createBranch(BranchCreateRequest request) {
        var branchDomain = branchAppMapper.toDomain(request);
        var checkExistBranchCode = branchRepository.checkBranchCodeExists(request.getBranchCode());
        if (checkExistBranchCode) {
            throw new AppException(ErrorCode.BRANCH_CONFLICT, request.getBranchCode());
        }
        var saveBranch = branchRepository.save(branchDomain);

        return branchAppMapper.toResponse(saveBranch);
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

        branchRepository.delete(branchDomain);
    }


    public Page<BranchResponse> getListBranchPages(BranchQueryFilterRequest filterRequest, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
       log.info("data la: {}", filterRequest);
        BranchFilter branchFilter = BranchFilter.builder()
                .branchCode(filterRequest.getBranchCode())
                .name(filterRequest.getName())
                .email(filterRequest.getEmail())
                .address(filterRequest.getAddress())
                .phoneNumber(filterRequest.getPhoneNumber())
                .build();

        var branchDomainPage = branchEntityService.filterPageBranchs(branchFilter, pageable, filterRequest.isTypeCompare());

        return branchDomainPage.map(branchAppMapper::toResponse);
    }

    public List<BranchResponse> searchBranchByNameOrBranchCode(String name, String code) {
        var listBranchDomain = branchRepository.searchBranchByNameOrBranchCode(name, code);
        return listBranchDomain.stream().map(branchAppMapper::toResponse).toList();
    }

}
