package com.roomx.controller.resource;

import com.roomx.application.dto.resource.request.PlaceCreateRequest;
import com.roomx.application.dto.resource.request.PlaceQueryRequest;
import com.roomx.application.dto.resource.request.PlaceSelectBoxRequest;
import com.roomx.application.dto.resource.request.PlaceUpdateRequest;
import com.roomx.application.dto.resource.response.PlaceResponse;
import com.roomx.application.service.resource.PlaceAppService;
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
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaceController {
    PlaceAppService placeAppService;

    @PostMapping
    public ResultResponse<?> createPlace(@Validated @RequestBody PlaceCreateRequest request) {
        var result = placeAppService.createPlace(request);
        return ResultResponse.<PlaceResponse>builder()
                .result(result)
                .build();
    }

    @PatchMapping("/{placeId}")
    public ResultResponse<?> updatePlaceById(
            @PathVariable String placeId,
            @Validated @RequestBody PlaceUpdateRequest request
            ) {
        var result = placeAppService.updatePlaceById(placeId, request);
        return ResultResponse.<PlaceResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/filters")
    public ResultResponse<?> getListPagePlace(
            @ModelAttribute PlaceQueryRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "branch.branchCode") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        var result = placeAppService.getListPlacePages(filter, page, size, sortBy, direction);

        return ResultResponse.<Page<PlaceResponse>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{placeId}")
    public ResultResponse<?> getDetailPlace(@PathVariable String placeId) {

        return ResultResponse.<Void>builder().build();
    }

    @DeleteMapping("/{placeId}")
    public ResultResponse<?> deletePlace(@PathVariable String placeId) {

        return ResultResponse.<Void>builder().build();
    }

    @GetMapping("/hierarchy")
    public ResultResponse<?> getListFloors(
            @ModelAttribute PlaceSelectBoxRequest request
            ) {
        var result = placeAppService.getListPlaceSelectBox(request);
        return ResultResponse.<List<String>>builder()
                .result(result)
                .build();
    }

}
