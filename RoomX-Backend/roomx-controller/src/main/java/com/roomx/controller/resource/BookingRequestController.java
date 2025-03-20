package com.roomx.controller.resource;

import com.roomx.shared.dto.booking.request.BookingRequestAdminCreateRequest;

import com.roomx.shared.dto.booking.response.BookingRequestResponse;
import com.roomx.application.service.booking.BookingAppService;
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
@RequestMapping("/api/v1/booking-requests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingRequestController {
    BookingAppService bookingAppService;

    @PostMapping
    public ResultResponse<?> adminCreateBookingRequest(
            @Validated @RequestBody BookingRequestAdminCreateRequest request) {
        var result = bookingAppService.adminCreateBooking(request);
        return ResultResponse.<BookingRequestResponse>builder()
                .result(result)
                .build();
    }

}
