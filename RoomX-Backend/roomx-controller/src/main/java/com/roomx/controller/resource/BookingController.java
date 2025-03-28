package com.roomx.controller.resource;

import com.roomx.application.service.booking.BookingAppService;
import com.roomx.shared.dto.TestRequest;
import com.roomx.shared.dto.booking.request.BookingRequestAdminCreateRequest;
import com.roomx.shared.dto.booking.request.BookingRequestUserCreateRequest;
import com.roomx.shared.dto.booking.response.BookingRequestResponse;
import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController {
    BookingAppService bookingAppService;

   /* @PostMapping
    public ResultResponse<?> adminCreateBookingRequest(
            @Validated @RequestBody BookingRequestUserCreateRequest request) {
        var result = bookingAppService.createBooking(request);
        return ResultResponse.<BookingRequestResponse>builder()
                .result(result)
                .build();
    }*/

    @PostMapping
    public ResultResponse<?> createBookingRequest(
            @Validated @RequestBody BookingRequestUserCreateRequest request) {
        var result = bookingAppService.createBookingRequest(request);
        return ResultResponse.<BookingRequestResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/check/{bookingRequestId}")
    public ResultResponse<?> checkRoomSuitable(
           @PathVariable String bookingRequestId) {
        var result = bookingAppService.checkRoomSuitable(bookingRequestId);
        return ResultResponse.<Object>builder()
                .result(result)
                .build();
    }

    @PostMapping("/check")
    public ResultResponse<?> checkRoomSuitable(
            @RequestBody TestRequest request) {
        var result = bookingAppService.test(request);
        return ResultResponse.<Object>builder()
                .result(result)
                .build();
    }
}
