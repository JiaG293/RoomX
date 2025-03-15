package com.roomx.controller.resource;

import com.roomx.application.dto.resource.request.PlaceCreateRequest;
import com.roomx.application.dto.resource.request.RoomClassCreateRequest;
import com.roomx.application.dto.resource.response.PlaceResponse;
import com.roomx.application.dto.resource.response.RoomClassResponse;
import com.roomx.application.service.resource.RoomClassAppService;
import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
