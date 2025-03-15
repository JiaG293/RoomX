package com.roomx.controller.resource;

import com.roomx.application.dto.resource.request.RoomClassCreateRequest;
import com.roomx.application.dto.resource.request.RoomClassUpdateRequest;
import com.roomx.application.dto.resource.response.RoomClassResponse;
import com.roomx.application.service.resource.RoomClassAppService;
import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/room-classes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomClassController {
    RoomClassAppService roomClassAppService;

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
}
