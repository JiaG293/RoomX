package com.roomx.application.service.resource;

import com.roomx.application.dto.resource.request.ServiceCreateRequest;
import com.roomx.application.dto.resource.request.ServiceQueryRequest;
import com.roomx.application.dto.resource.request.ServiceUpdateRequest;
import com.roomx.application.dto.resource.response.ServiceResponse;
import com.roomx.application.mapper.ServiceAppMapper;
import com.roomx.domain.repository.ServiceRepository;
import com.roomx.infrastructure.multitenancy.persistence.dto.ServiceFilter;
import com.roomx.infrastructure.multitenancy.persistence.service.ServiceEntityService;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceAppService {
    private final ServiceRepository serviceRepository;
    private final ServiceAppMapper serviceAppMapper;
    private final ServiceEntityService serviceEntityService;


    @Transactional
    public ServiceResponse createService(ServiceCreateRequest serviceCreateRequest){
        var serviceDomain = serviceAppMapper.toDomain(serviceCreateRequest);

        var savedService = serviceRepository.save(serviceDomain);

        return serviceAppMapper.toResponse(savedService);
    }

    @Transactional
    public ServiceResponse updateServiceById(String serviceId, ServiceUpdateRequest request){
        var serviceDomain = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND, serviceId));

        log.info("domain: {}", serviceDomain.getName());
        serviceAppMapper.updateDomainFromDto(request, serviceDomain);
        log.info("domainUpdate: {}", serviceDomain.getName());

        var savedService = serviceRepository.save(serviceDomain);
        return serviceAppMapper.toResponse(savedService);
    }

    @Transactional
    public void deleteServiceById(String serviceId){
        var serviceDomain = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND, serviceId));

        serviceRepository.delete(serviceDomain);
    }

    public Page<ServiceResponse> getListServicePages(
            ServiceQueryRequest filterRequest,
            int page,
            int size,
            String sortBy,
            String direction){
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        ServiceFilter serviceFilter = ServiceFilter.builder()
                .name(filterRequest.getName())
                .description(filterRequest.getDescription())
                .note(filterRequest.getNote())
                .createdAt(filterRequest.getCreatedAt())
                .updatedAt(filterRequest.getUpdatedAt())
                .unitPrice(filterRequest.getUnitPrice())
                .build();

        var serviceDomainPage = serviceEntityService.filterPageServices(serviceFilter, pageable, filterRequest.isCompareType());

        return serviceDomainPage.map(serviceAppMapper::toResponse);
    }



}
