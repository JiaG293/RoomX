package com.roomx.controller.resource;

import com.roomx.shared.dto.resource.request.RoomCreateRequest;
import com.roomx.shared.dto.resource.request.RoomQueryRequest;
import com.roomx.shared.dto.resource.request.RoomUpdateStatusRequest;
import com.roomx.shared.dto.resource.response.RoomDetailResponse;
import com.roomx.shared.dto.resource.response.RoomResponse;
import com.roomx.application.service.resource.RoomAppService;
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

    @GetMapping("/filters")
    public ResultResponse<?> getListPageService(
            @ModelAttribute RoomQueryRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "roomCode") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        var result = roomAppService.getListRoomPages(filter, page, size, sortBy, direction);

        return ResultResponse.<Page<RoomResponse>>builder()
                .result(result)
                .build();
    }

}