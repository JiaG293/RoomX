package com.roomx.controller.resource;

import com.roomx.application.dto.resource.request.ServiceCreateRequest;
import com.roomx.application.dto.resource.request.ServiceQueryRequest;
import com.roomx.application.dto.resource.request.ServiceUpdateRequest;
import com.roomx.application.dto.resource.response.ServiceResponse;
import com.roomx.application.service.resource.ServiceAppService;
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
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceController {

    ServiceAppService serviceAppService;

    @PostMapping
    public ResultResponse<?> createService(@Validated @RequestBody ServiceCreateRequest serviceCreateRequest) {
        return ResultResponse.<ServiceResponse>builder()
                .result(serviceAppService.createService(serviceCreateRequest))
                .build();
    }

    @PatchMapping("/{serviceId}")
    public ResultResponse<?> updateService(@PathVariable String serviceId, @Validated @RequestBody ServiceUpdateRequest serviceUpdateRequest) {
        var result = serviceAppService.updateServiceById(serviceId, serviceUpdateRequest);

        return ResultResponse.<ServiceResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/filters")
    public ResultResponse<?> getListPageService(
            @ModelAttribute ServiceQueryRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
            ) {
        var result = serviceAppService.getListServicePages(filter, page, size, sortBy, direction);

        return ResultResponse.<Page<ServiceResponse>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{serviceId}")
    public ResultResponse<?> getDetailService(@PathVariable String serviceId) {

        return ResultResponse.<Void>builder().build();
    }

    @DeleteMapping("/{serviceId}")
    public ResultResponse<?> deleteService(@PathVariable String serviceId) {
        serviceAppService.deleteServiceById(serviceId);
        return ResultResponse.<Void>builder().build();
    }
}
