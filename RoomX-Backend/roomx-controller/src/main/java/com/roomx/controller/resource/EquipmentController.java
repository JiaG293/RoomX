package com.roomx.controller.resource;


import com.roomx.application.dto.resource.request.EquipmentCreateRequest;
import com.roomx.application.dto.resource.request.EquipmentQueryRequest;
import com.roomx.application.dto.resource.request.EquipmentUpdateRequest;
import com.roomx.application.dto.resource.response.EquipmentResponse;
import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/equipments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EquipmentController {
    com.roomx.application.equipment.resource.EquipmentAppService equipmentAppService;

    @PostMapping
    public ResultResponse<?> createEquipment(@Validated @RequestBody EquipmentCreateRequest request) {
        var result = equipmentAppService.createEquipment(request);
        return ResultResponse.<EquipmentResponse>builder()
                .result(result)
                .build();
    }

    @PatchMapping("/{equipmentId}")
    public ResultResponse<?> createEquipment(@PathVariable String equipmentId, @Validated @RequestBody EquipmentUpdateRequest request) {
        var result = equipmentAppService.updateEquipmentById(equipmentId, request);
        return ResultResponse.<EquipmentResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/filters")
    public ResultResponse<?> getListPageEquipment(
            @ModelAttribute EquipmentQueryRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "branchCode") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        var result = equipmentAppService.getListEquipmentPages(filter, page, size, sortBy, direction);
        return ResultResponse.<Page<EquipmentResponse>>builder()
                .result(result)
                .build();
    }

    /*@GetMapping("/{equipmentId}")
    public ResultResponse<?> getDetailEquipment(@PathVariable String equipmentId) {

        return ResultResponse.<Void>builder().build();
    }*/

    @DeleteMapping("/{equipmentId}")
    public ResultResponse<?> deleteEquipment(@PathVariable String equipmentId) {
        equipmentAppService.deleteEquipmentById(equipmentId);
        return ResultResponse.<Void>builder().build();
    }
}
