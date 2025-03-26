package com.roomx.controller.resource;

import com.roomx.shared.dto.resource.base.RoomClassPriceCalculateDto;
import com.roomx.shared.dto.resource.request.*;
import com.roomx.application.service.resource.EquipmentRoomClassAppService;
import com.roomx.application.service.resource.RoomClassAppService;
import com.roomx.application.service.resource.ServiceRoomClassAppService;
import com.roomx.shared.dto.resource.response.*;
import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/room-classes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomClassController {
    RoomClassAppService roomClassAppService;
    EquipmentRoomClassAppService equipmentRoomClassAppService;
    ServiceRoomClassAppService serviceRoomClassAppService;

    @PostMapping
    public ResultResponse<?> createRoomClass(@Validated @RequestBody RoomClassCreateRequest request) {
        var result = roomClassAppService.createRoomClass(request);
        return ResultResponse.<RoomClassResponse>builder()
                .result(result)
                .build();
    }

    @PatchMapping("/{roomClassId}")
    public ResultResponse<?> updateRoomClassById(
            @PathVariable String roomClassId,
            @Validated @RequestBody RoomClassUpdateRequest request
    ) {
        var result = roomClassAppService.updateRoomClassById(roomClassId, request);
        return ResultResponse.<RoomClassResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{roomClassId}")
    public ResultResponse<?> getDetailRoomClassById(@PathVariable String roomClassId) {
        var result = roomClassAppService.getDetailRoomClassById(roomClassId);
        return ResultResponse.<RoomClassDetailResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/room-class-code/{roomClassCode}")
    public ResultResponse<?> getDetailRoomClassByRoomClassCode(@PathVariable String roomClassCode) {
        var result = roomClassAppService.getDetailRoomClassByRoomClassCode(roomClassCode);
        return ResultResponse.<RoomClassDetailResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/{roomClassId}/equipments")
    public ResultResponse<?> addEquipmentRoomClass(
            @PathVariable String roomClassId,
            @Validated @RequestBody List<EquipmentRoomClassCreateRequest> request) {

        var result = equipmentRoomClassAppService.addEquipmentToRoomClass(roomClassId, request);
        return ResultResponse.<List<EquipmentRoomClassResponse>>builder()
                .result(result)
                .build();
    }

    @PostMapping("/{roomClassId}/services")
    public ResultResponse<?> addServiceRoomClass(
            @PathVariable String roomClassId,
            @Validated @RequestBody List<ServiceRoomClassCreateRequest> request) {
        log.info("data: {}", request);
        var result = serviceRoomClassAppService.addServiceToRoomClass(roomClassId, request);
        return ResultResponse.<List<ServiceRoomClassResponse>>builder()
                .result(result)
                .build();
    }

    @PostMapping("/{roomClassId}/prices")
    public ResultResponse<?> addNewPriceForRoomClass(
            @PathVariable String roomClassId,
            @Validated @RequestBody RoomClassPriceHistoryCreateRequest request) {
        var result = roomClassAppService.addPriceNew(roomClassId, request);
        return ResultResponse.<RoomClassResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{roomClassId}/test")
    public ResultResponse<?> calculatePriceRoomClass(
            @PathVariable String roomClassId
            ) {
        var result = roomClassAppService.calculatePriceRoomClass(roomClassId);
        return ResultResponse.<RoomClassPriceCalculateDto>builder()
                .result(result)
                .build();
    }

}
