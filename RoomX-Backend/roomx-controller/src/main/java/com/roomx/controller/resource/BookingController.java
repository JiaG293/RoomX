package com.roomx.controller.resource;

import com.roomx.application.service.booking.BookingAppService;
import com.roomx.shared.dto.TestRequest;
import com.roomx.shared.dto.booking.request.*;
import com.roomx.shared.dto.booking.response.BookingRequestResponse;
import com.roomx.shared.dto.booking.response.BookingResponse;
import com.roomx.shared.dto.resource.request.BranchQueryRequest;
import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/{bookingRequestId}/check")
    public ResultResponse<?> checkRoomSuitable(
            @PathVariable String bookingRequestId) {
        var result = bookingAppService.checkRoomSuitable(bookingRequestId);
        return ResultResponse.<Object>builder()
                .result(result)
                .build();
    }

    @PostMapping("/{bookingRequestId}")
    public ResultResponse<?> approveBooking(
            @PathVariable String bookingRequestId) {
        var result = bookingAppService.approveBooking(bookingRequestId);
        return ResultResponse.<Object>builder()
                .result(result)
                .build();
    }

    @GetMapping("/filters")
    public ResultResponse<?> filterPageWithAdmin(
            @ModelAttribute BookingFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        var result = bookingAppService.filterSearchPageBookingAdmin(filter, page, size, sortBy, direction);
        return ResultResponse.<Page<BookingResponse>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/approvals")
    public ResultResponse<?> getPageBookingRequestAdminApproval(
            @ModelAttribute BookingRequestApprovalRequest request,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        var result = bookingAppService.getListPageBookingRequestAdminApproval(request, page, size, sortBy, direction);
        return ResultResponse.<Page<BookingRequestResponse>>builder()
                .result(result)
                .build();
    }

    @PostMapping("/3")
    public ResultResponse<?> test3() {
        var result = "";
        return ResultResponse.<Object>builder()
                .result(result)
                .build();
    }

    @PostMapping("/test")
    public ResultResponse<?> test() {
        var result = bookingAppService.test();
        return ResultResponse.<Object>builder()
                .result(result)
                .build();
    }
}
