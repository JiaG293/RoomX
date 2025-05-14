package com.roomx.application.service.resource;


import com.roomx.application.mapper.ServicePriceHistoryAppMapper;
import com.roomx.domain.model.aggrerate.Service;
import com.roomx.domain.model.entity.ServicePriceHistory;
import com.roomx.domain.repository.ServicePriceHistoryRepository;
import com.roomx.shared.dto.resource.request.*;
import com.roomx.shared.dto.resource.response.ServiceDetailResponse;
import com.roomx.shared.dto.resource.response.ServiceResponse;
import com.roomx.application.mapper.ServiceAppMapper;
import com.roomx.domain.repository.ServiceRepository;
import com.roomx.infrastructure.persistence.dto.ServiceFilter;
import com.roomx.infrastructure.persistence.service.ServiceEntityService;
import com.roomx.shared.enums.DeleteStatusType;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceAppService {
    private final ServiceRepository serviceRepository;
    private final ServiceAppMapper serviceAppMapper;
    private final ServiceEntityService serviceEntityService;
    private final ServicePriceHistoryRepository servicePriceHistoryRepository;
    private final ServicePriceHistoryAppMapper servicePriceHistoryAppMapper;


    @Transactional
    public ServiceResponse createService(ServiceCreateRequest request) {
        var serviceDomainCheckCode = serviceRepository.findByServiceCode(request.getServiceCode());

        if (serviceDomainCheckCode.isPresent()) {
            if (serviceDomainCheckCode.get().getStatus().equals(DeleteStatusType.getDefaultString())) {
                throw new AppException(ErrorCode.SERVICE_CONFLICT, serviceDomainCheckCode.get().getServiceCode());
            }
            throw new AppException(ErrorCode.SERVICE_FORBIDDEN, serviceDomainCheckCode.get().getServiceCode());
        }

        var serviceDomain = serviceAppMapper.toDomain(request);
        serviceDomain.setStatus(DeleteStatusType.getDefaultString());
        var savedService = serviceRepository.save(serviceDomain);

        var servicePrice = ServicePriceHistory.builder()
                .service(savedService)
                .validFrom(Instant.now())
                .unitPrice(request.getUnitPrice())
                .build();

        var savedServicePrice = servicePriceHistoryRepository.save(servicePrice);

        savedService.setPrice(savedServicePrice);
        return serviceAppMapper.toResponse(savedService);
    }

    @Transactional
    public ServiceResponse updateServiceById(String serviceId, ServiceUpdateRequest request) {
        var serviceDomain = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND, null, serviceId));
        var servicePriceDomain = servicePriceHistoryRepository
                .findLatestValidFrom(serviceDomain.getId().toString())
                .orElse(null);

        log.info("services: {}", serviceDomain);
        serviceAppMapper.updateDomainFromDto(request, serviceDomain);
        log.info("service updated: {}", serviceDomain);
        var savedService = serviceRepository.save(serviceDomain);

        var responseService = serviceAppMapper.toResponse(savedService);
        responseService.setPrice(servicePriceHistoryAppMapper.toResponse(servicePriceDomain));
        return responseService;
    }

    @Transactional
    public void deleteServiceById(String serviceId) {
        var serviceDomain = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND, serviceId));
        serviceDomain.setStatus(DeleteStatusType.INACTIVE.toString());
        serviceRepository.save(serviceDomain);
    }

    @Transactional
    public Map<String, List<String>> deleteListServiceById(List<String> services) {
        var listServiceDeleted = new ArrayList<Service>();
        var serviceFailedDelete = new ArrayList<String>();

        for (String serviceId : services) {
            var serviceFind = serviceRepository.findByIdAndStatus(serviceId, DeleteStatusType.getDefaultString());
            if (serviceFind.isPresent()) {
                var serviceDomain = serviceFind.get();
                serviceDomain.setStatus(DeleteStatusType.INACTIVE.toString());
                listServiceDeleted.add(serviceDomain);
            } else {
                serviceFailedDelete.add(serviceId);
            }
        }

        if (!listServiceDeleted.isEmpty()) {
            serviceRepository.saveAll(listServiceDeleted);
        }

        return Map.of("listServiceDeleteFailed", serviceFailedDelete);
    }


   /* public Page<ServiceResponse> getListServicePages(
            ServiceQueryRequest filterRequest,
            int page,
            int size,
            String sortBy,
            String direction){
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        ServiceFilter serviceFilter = ServiceFilter.builder()
//                .name(filterRequest.getName())
//                .description(filterRequest.getDescription())
//                .note(filterRequest.getNote())
                .createdAt(filterRequest.getCreatedAt())
                .updatedAt(filterRequest.getUpdatedAt())
                //.unitPrice(filterRequest.getUnitPrice())
                .build();

        var serviceDomainPage = serviceEntityService.filterPageServices(serviceFilter, pageable, filterRequest.isCompareType());

        return serviceDomainPage.map(serviceAppMapper::toResponse);
    }*/


    public Page<ServiceResponse> searchFilterService(
            ServiceFilterRequest filter,
            int page,
            int size,
            String sortBy,
            String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        ServiceFilter serviceFilter = ServiceFilter.builder()
                .keyword(filter.keyword())
                .searchBy(filter.searchBy())
                .fromPrice(filter.fromPrice())
                .toPrice(filter.toPrice())
                .validPriceFrom(filter.validPriceFrom())
                .validPriceEnd(filter.validPriceEnd())
                .status(filter.status())
                .build();

        var serviceDomainPage = serviceEntityService.searchFilterService(serviceFilter, pageable);

        return serviceDomainPage.map(serviceAppMapper::toResponse);
    }


    @Transactional
    public ServiceResponse addPriceNew(String serviceId, ServicePriceHistoryCreateRequest request) {
        var serviceDomain = serviceRepository.findByIdAndStatus(serviceId, DeleteStatusType.getDefaultString())
                .orElseThrow(() -> new AppException(ErrorCode.EQUIPMENT_NOT_FOUND));

        var validEnd = Instant.now();

        var servicePriceOldDomain = servicePriceHistoryRepository.findLatestValidFrom(serviceId);

        if (servicePriceOldDomain.isPresent()) {
            var oldPriceDomain = servicePriceOldDomain.get();
            oldPriceDomain.setValidEnd(validEnd);
            servicePriceHistoryRepository.save(oldPriceDomain);

        }

        var servicePriceNewDomain = ServicePriceHistory.builder()
                .service(serviceDomain)
                .unitPrice(request.getUnitPrice())
                .validFrom(validEnd)
                .build();

        // logic add new roomClassPriceHistory

        var savedServicePrice = servicePriceHistoryRepository.save(servicePriceNewDomain);

        serviceDomain.setPrice(savedServicePrice);

        return serviceAppMapper.toResponse(serviceDomain);
    }



    public ServiceDetailResponse getDetailService(String serviceId) {
        var serviceDomain = serviceRepository.findByIdAndStatus(serviceId, DeleteStatusType.ACTIVE.toString())
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));
        var servicePriceDomain = servicePriceHistoryRepository.findLatestValidFrom(serviceId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));

        serviceDomain.setPrice(servicePriceDomain);
        return serviceAppMapper.toResponseDetail(serviceDomain);
    }
}
