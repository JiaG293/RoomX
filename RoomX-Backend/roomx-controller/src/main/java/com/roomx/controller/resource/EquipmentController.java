package com.roomx.controller.resource;


import com.roomx.shared.dto.resource.request.*;
import com.roomx.shared.dto.resource.response.EquipmentDetailResponse;
import com.roomx.shared.dto.resource.response.EquipmentResponse;
import com.roomx.application.service.resource.response.EquipmentAppService;
import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/equipments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EquipmentController {
    EquipmentAppService equipmentAppService;

    @PostMapping
    public ResultResponse<?> createEquipment(@Validated @RequestBody EquipmentCreateRequest request) {
        var result = equipmentAppService.createEquipment(request);
        return ResultResponse.<EquipmentResponse>builder()
                .result(result)
                .build();
    }

    @PatchMapping("/{equipmentId}")
    public ResultResponse<?> updateEquipment(@PathVariable String equipmentId, @Validated @RequestBody EquipmentUpdateRequest request) {
        var result = equipmentAppService.updateEquipmentById(equipmentId, request);
        return ResultResponse.<EquipmentResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/{equipmentId}/prices")
    public ResultResponse<?> addNewPriceForEquipment(
            @PathVariable String equipmentId,
            @Validated @RequestBody EquipmentPriceHistoryCreateRequest request) {
        var result = equipmentAppService.addPriceNew(equipmentId, request);
        return ResultResponse.<EquipmentResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/filters")
    public ResultResponse<?> searchFilterEquipmentPage(
            @ModelAttribute EquipmentFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {

        var result = equipmentAppService.searchFilterEquipment(filter, page, size, sortBy, direction);
        return ResultResponse.<Page<EquipmentResponse>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{equipmentId}")
    public ResultResponse<?> getDetailEquipment(@PathVariable String equipmentId) {
        var result = equipmentAppService.getDetailEquipment(equipmentId);
        return ResultResponse.<EquipmentDetailResponse>builder()
                .result(result)
                .build();
    }

    @DeleteMapping("/{equipmentId}")
    public ResultResponse<?> deleteEquipment(@PathVariable String equipmentId) {
        equipmentAppService.deleteEquipmentById(equipmentId);
        return ResultResponse.<Void>builder().build();
    }

    @DeleteMapping
    public ResultResponse<?> deleteListEquipment(@RequestBody List<String> listEquipmentId) {
        var result = equipmentAppService.deleteListEquipmentById(listEquipmentId);
        return ResultResponse.<Map<String, List<String>>>builder()
                .result(result)
                .build();
    }
}
