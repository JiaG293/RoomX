package com.roomx.application.equipment.resource;

import com.roomx.application.dto.resource.request.EquipmentCreateRequest;
import com.roomx.application.dto.resource.request.EquipmentQueryRequest;
import com.roomx.application.dto.resource.request.EquipmentUpdateRequest;
import com.roomx.application.dto.resource.response.EquipmentResponse;
import com.roomx.application.mapper.EquipmentAppMapper;
import com.roomx.domain.repository.EquipmentRepository;

import com.roomx.infrastructure.multitenancy.persistence.dto.EquipmentFilter;
import com.roomx.infrastructure.multitenancy.persistence.service.EquipmentEntityService;
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
public class EquipmentAppService {
    private final EquipmentRepository equipmentRepository;
    private final EquipmentAppMapper equipmentAppMapper;
    private final EquipmentEntityService equipmentEntityService;

    @Transactional
    public EquipmentResponse createEquipment(EquipmentCreateRequest equipmentCreateRequest){
        var equipmentDomain = equipmentAppMapper.toDomain(equipmentCreateRequest);

        var savedEquipment = equipmentRepository.save(equipmentDomain);

        return equipmentAppMapper.toResponse(savedEquipment);
    }

    @Transactional
    public EquipmentResponse updateEquipmentById(String equipmentId, EquipmentUpdateRequest request){
        var equipmentDomain = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND, equipmentId));

        log.info("domain: {}", equipmentDomain.getName());
        equipmentAppMapper.updateDomainFromDto(request, equipmentDomain);
        log.info("domainUpdate: {}", equipmentDomain.getName());

        var savedEquipment = equipmentRepository.save(equipmentDomain);
        return equipmentAppMapper.toResponse(savedEquipment);
    }

    @Transactional
    public void deleteEquipmentById(String equipmentId){
        var equipmentDomain = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND, equipmentId));

        equipmentRepository.delete(equipmentDomain);
    }

    public Page<EquipmentResponse> getListEquipmentPages(
            EquipmentQueryRequest filterRequest,
            int page,
            int size,
            String sortBy,
            String direction){
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        EquipmentFilter equipmentFilter = EquipmentFilter.builder()

                .build();

        var equipmentDomainPage = equipmentEntityService.filterPageEquipments(equipmentFilter, pageable, filterRequest.isCompareType());

        return equipmentDomainPage.map(equipmentAppMapper::toResponse);
    }


}
