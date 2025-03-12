package com.roomx.controller.resource;

import com.roomx.application.dto.resource.request.BranchCreateRequest;
import com.roomx.application.dto.resource.request.BranchQueryFilterRequest;
import com.roomx.application.dto.resource.request.BranchUpdateRequest;
import com.roomx.application.dto.resource.response.BranchResponse;
import com.roomx.application.dto.user.request.UserQueryFilterRequest;
import com.roomx.application.dto.user.response.UserResponse;
import com.roomx.application.service.resource.BranchAppService;
import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/branchs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BranchController {
    BranchAppService branchAppService;

    @PostMapping
    public ResultResponse<?> createBranch(@Validated @RequestBody BranchCreateRequest branchCreateRequest) {
        return ResultResponse.<BranchResponse>builder()
                .result(branchAppService.createBranch(branchCreateRequest))
                .build();
    }

    @GetMapping("/search")
    public ResultResponse<?> searchBranchByName(
            @RequestParam String name,
            @RequestParam String code
    ) {
        return ResultResponse.<List<BranchResponse>>builder()
                .result(branchAppService.searchBranchByNameOrBranchCode(name, code))
                .build();
    }

    @PatchMapping("/{branchId}")
    public ResultResponse<?> searchBranchByName(
            @PathVariable String branchId,
            @Validated @RequestBody BranchUpdateRequest branchUpdateRequest
    ) {
        return ResultResponse.<BranchResponse>builder()
                .result(branchAppService.updateBranchById(branchId, branchUpdateRequest))
                .build();
    }

    @GetMapping("/filter")
    public ResultResponse<?> getListPageBranch(
            @ModelAttribute BranchQueryFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "branchCode") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        var result = branchAppService.getListBranchPages(filter, page, size, sortBy, direction);

        return ResultResponse.<Page<BranchResponse>>builder()
                .result(result)
                .build();
    }


}
