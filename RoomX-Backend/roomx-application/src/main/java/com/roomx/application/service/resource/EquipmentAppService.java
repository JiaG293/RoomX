package com.roomx.application.service.resource;


import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.model.entity.EquipmentPriceHistory;
import com.roomx.domain.repository.EquipmentPriceHistoryRepository;
import com.roomx.shared.dto.resource.request.EquipmentCreateRequest;
import com.roomx.shared.dto.resource.request.EquipmentPriceHistoryCreateRequest;
import com.roomx.shared.dto.resource.request.EquipmentQueryRequest;
import com.roomx.shared.dto.resource.request.EquipmentUpdateRequest;
import com.roomx.shared.dto.resource.response.EquipmentResponse;
import com.roomx.application.mapper.EquipmentAppMapper;
import com.roomx.domain.repository.EquipmentRepository;
import com.roomx.infrastructure.multitenancy.persistence.dto.EquipmentFilter;
import com.roomx.infrastructure.multitenancy.persistence.service.EquipmentEntityService;
import com.roomx.shared.enums.DeleteStatusType;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class EquipmentAppService {
    private final EquipmentRepository equipmentRepository;
    private final EquipmentAppMapper equipmentAppMapper;
    private final EquipmentEntityService equipmentEntityService;
    private final EquipmentPriceHistoryRepository equipmentPriceHistoryRepository;

    @Transactional
    public EquipmentResponse createEquipment(EquipmentCreateRequest request) {
        var equipmentCheckCode = equipmentRepository.findByEquipmentCode(request.getEquipmentCode());

        if (equipmentCheckCode.isPresent()) {
            if (equipmentCheckCode.get().getStatus().equals(DeleteStatusType.getDefaultString())) {
                throw new AppException(ErrorCode.EQUIPMENT_CONFLICT, equipmentCheckCode.get().getEquipmentCode());
            }
            throw new AppException(ErrorCode.EQUIPMENT_FORBIDDEN, equipmentCheckCode.get().getEquipmentCode());
        }

        var equipmentDomain = equipmentAppMapper.toDomain(request);
        equipmentDomain.setStatus(DeleteStatusType.getDefaultString());
        var savedEquipment = equipmentRepository.save(equipmentDomain);


        var equipmentPrice = EquipmentPriceHistory.builder()
                .equipment(savedEquipment)
                .validFrom(Instant.now())
                .unitPrice(request.getUnitPrice())
                .active(true)
                .build();

        var savedEquipmentPrice = equipmentPriceHistoryRepository.save(equipmentPrice);

        savedEquipment.setPrice(savedEquipmentPrice);

        return equipmentAppMapper.toResponse(savedEquipment);
    }

    @Transactional
    public EquipmentResponse updateEquipmentById(String equipmentId, EquipmentUpdateRequest request) {
        var equipmentDomain = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND, equipmentId));

        equipmentAppMapper.updateDomainFromDto(request, equipmentDomain);


        var savedEquipment = equipmentRepository.save(equipmentDomain);
        return equipmentAppMapper.toResponse(savedEquipment);
    }

    @Transactional
    public void deleteEquipmentById(String equipmentId) {
        var equipmentDomain = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND, equipmentId));
        equipmentDomain.setStatus(DeleteStatusType.INACTIVE.toString());
        equipmentRepository.save(equipmentDomain);
    }

    @Transactional
    public Map<String, List<String>> deleteListEquipmentById(List<String> equipments) {
        var listEquipmentDeleted = new ArrayList<Equipment>();
        var equipmentFailedDelete = new ArrayList<String>();

        for (String equipmentId : equipments) {
            var equipmentFind = equipmentRepository.findByIdAndStatus(equipmentId, DeleteStatusType.getDefaultString());
            if (equipmentFind.isPresent()) {
                var equipmentDomain = equipmentFind.get();
                equipmentDomain.setStatus(DeleteStatusType.INACTIVE.toString());
                listEquipmentDeleted.add(equipmentDomain);
            } else {
                equipmentFailedDelete.add(equipmentId);
            }
        }

        if (!listEquipmentDeleted.isEmpty()) {
            equipmentRepository.saveAll(listEquipmentDeleted);
        }

        return Map.of("listEquipmentDeleteFailed", equipmentFailedDelete);
    }

    public Page<EquipmentResponse> getListEquipmentPages(
            EquipmentQueryRequest filterRequest,
            int page,
            int size,
            String sortBy,
            String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        EquipmentFilter equipmentFilter = EquipmentFilter.builder()
                .equipmentCode(filterRequest.getEquipmentCode())
                .brand(filterRequest.getBrand())
                .id(filterRequest.getId())
                .name(filterRequest.getName())
                .createdAt(filterRequest.getCreatedAt())
                .updatedAt(filterRequest.getUpdatedAt())
                .unitPrice(filterRequest.getUnitPrice())
                .build();

        var equipmentDomainPage = equipmentEntityService.filterPageEquipments(equipmentFilter, pageable, filterRequest.isCompareType());

        return equipmentDomainPage.map(equipmentAppMapper::toResponse);
    }

    @Transactional
    public EquipmentResponse addPriceNew(String equipmentId, EquipmentPriceHistoryCreateRequest request) {
        var equipmentDomain = equipmentRepository.findByIdAndStatus(equipmentId, DeleteStatusType.getDefaultString())
                .orElseThrow(() -> new AppException(ErrorCode.EQUIPMENT_NOT_FOUND));

        var validEnd = Instant.now();

        var equipmentPriceOldDomain = equipmentPriceHistoryRepository.findLatestValidFrom(equipmentId);

        if (equipmentPriceOldDomain.isPresent()) {
            var oldPriceDomain = equipmentPriceOldDomain.get();
            oldPriceDomain.setActive(false);
            oldPriceDomain.setValidEnd(validEnd);
            oldPriceDomain = equipmentPriceHistoryRepository.save(oldPriceDomain);

        }

        var equipmentPriceNewDomain = EquipmentPriceHistory.builder()
                .equipment(equipmentDomain)
                .unitPrice(request.getUnitPrice())
                .validFrom(validEnd)
                .active(true)
                .build();

        // logic add new roomClassPriceHistory

        var savedEquipmentPrice = equipmentPriceHistoryRepository.save(equipmentPriceNewDomain);

        equipmentDomain.setPrice(savedEquipmentPrice);

        return equipmentAppMapper.toResponse(equipmentDomain);
    }






}
