package com.roomx.controller.resource;

import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaceController {
    @PostMapping
    public ResultResponse<?> createPlace() {

        return ResultResponse.<Void>builder().build();
    }

    @GetMapping("/filters")
    public ResultResponse<?> getListPagePlace() {

        return ResultResponse.<Void>builder().build();
    }

    @GetMapping("/{placeId}")
    public ResultResponse<?> getDetailPlace(@PathVariable String placeId) {

        return ResultResponse.<Void>builder().build();
    }

    @DeleteMapping("/{placeId}")
    public ResultResponse<?> deletePlace(@PathVariable String placeId) {

        return ResultResponse.<Void>builder().build();
    }
}
