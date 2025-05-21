package com.roomx.controller.resource;

import com.roomx.application.service.resource.response.RoomDetailAllResponse;
import com.roomx.shared.dto.resource.request.*;
import com.roomx.shared.dto.resource.response.RoomDetailResponse;
import com.roomx.shared.dto.resource.response.RoomFilterResponse;
import com.roomx.shared.dto.resource.response.RoomMaxMinResponse;
import com.roomx.shared.dto.resource.response.RoomResponse;
import com.roomx.application.service.resource.response.RoomAppService;
import com.roomx.shared.exception.api.ResultResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomController {
    RoomAppService roomAppService;

   /* @PostMapping
    public ResultResponse<?> createRoom() {
        var result = roomAppService.;
        return ResultResponse.<>builder()
                .result(result)
                .build();
    }*/

    @PostMapping
    public ResultResponse<?> createRoom(@Validated @RequestBody RoomCreateRequest request) {
        var result = roomAppService.createRoom(request);
        return ResultResponse.<RoomDetailResponse>builder()
                .result(result)
                .build();
    }

    @PatchMapping("/{roomId}/status")
    public ResultResponse<?> updateStatusRoom(@PathVariable String roomId, @RequestBody RoomUpdateStatusRequest request) {
        var result = roomAppService.updateStatusRoom(roomId, request);

        return ResultResponse.<RoomResponse>builder()
                .result(result)
                .build();
    }

    @PatchMapping("/{roomId}")
    public ResultResponse<?> updateRoom(@PathVariable String roomId, @RequestBody RoomUpdateRequest request) {
        var result = roomAppService.updateRoom(roomId, request);

        return ResultResponse.<RoomDetailResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{roomId}")
    public ResultResponse<?> getDetailRoom(
            @PathVariable @NotNull String roomId
    ) {
        var result = roomAppService.getDetailRoom(roomId);

        return ResultResponse.<RoomDetailAllResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/capacity-range")
    public ResultResponse<?> getCapacityRange() {
        var result = roomAppService.getMaxMinRoomCapacity();

        return ResultResponse.<RoomMaxMinResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/filters")
    public ResultResponse<?> getListPageService(
            @ModelAttribute RoomFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "capacity") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        var result = roomAppService.getListRoomPages(filter, page, size, sortBy, direction);

        return ResultResponse.<Page<RoomFilterResponse>>builder()
                .result(result)
                .build();
    }

}