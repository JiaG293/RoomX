package com.roomx.controller.resource;

import com.roomx.shared.dto.resource.request.*;
import com.roomx.shared.dto.resource.response.EquipmentResponse;
import com.roomx.shared.dto.resource.response.ServiceDetailResponse;
import com.roomx.shared.dto.resource.response.ServiceResponse;
import com.roomx.application.service.resource.ServiceAppService;
import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/{serviceId}/prices")
    public ResultResponse<?> addNewPriceForService(
            @PathVariable String serviceId,
            @Validated @RequestBody ServicePriceHistoryCreateRequest request) {
        var result = serviceAppService.addPriceNew(serviceId, request);
        return ResultResponse.<ServiceResponse>builder()
                .result(result)
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
    public ResultResponse<?> searchFilterServicePage(
            @ModelAttribute ServiceFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
            ) {
        var result = serviceAppService.searchFilterService(filter, page, size, sortBy, direction);

        return ResultResponse.<Page<ServiceResponse>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{serviceId}")
    public ResultResponse<?> getDetailService(@PathVariable String serviceId) {
        var result = serviceAppService.getDetailService(serviceId);
        return ResultResponse.<ServiceDetailResponse>builder()
                .result(result)
                .build();
    }

    @DeleteMapping("/{serviceId}")
    public ResultResponse<?> deleteService(@PathVariable String serviceId) {
        serviceAppService.deleteServiceById(serviceId);
        return ResultResponse.<Void>builder().build();
    }

    @DeleteMapping
    public ResultResponse<?> deleteListService(@RequestBody List<String> listServiceId) {
        var result = serviceAppService.deleteListServiceById(listServiceId);
        return ResultResponse.<Map<String, List<String>>>builder()
                .result(result)
                .build();
    }
}
