package com.roomx.application.service.resource;


import com.roomx.application.mapper.EquipmentPriceHistoryAppMapper;
import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.model.entity.EquipmentPriceHistory;
import com.roomx.domain.repository.EquipmentPriceHistoryRepository;
import com.roomx.shared.dto.resource.request.*;
import com.roomx.shared.dto.resource.response.EquipmentDetailResponse;
import com.roomx.shared.dto.resource.response.EquipmentResponse;
import com.roomx.application.mapper.EquipmentAppMapper;
import com.roomx.domain.repository.EquipmentRepository;
import com.roomx.infrastructure.persistence.dto.EquipmentFilter;
import com.roomx.infrastructure.persistence.service.EquipmentEntityService;
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


@Slf4j
@Service
@RequiredArgsConstructor
public class EquipmentAppService {
    private final EquipmentRepository equipmentRepository;
    private final EquipmentAppMapper equipmentAppMapper;
    private final EquipmentEntityService equipmentEntityService;
    private final EquipmentPriceHistoryRepository equipmentPriceHistoryRepository;
    private final EquipmentPriceHistoryAppMapper equipmentPriceHistoryAppMapper;

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
                .build();

        var savedEquipmentPrice = equipmentPriceHistoryRepository.save(equipmentPrice);

        savedEquipment.setPrice(savedEquipmentPrice);

        return equipmentAppMapper.toResponse(savedEquipment);
    }

    @Transactional
    public EquipmentResponse updateEquipmentById(String equipmentId, EquipmentUpdateRequest request) {
        var equipmentDomain = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new AppException(ErrorCode.EQUIPMENT_NOT_FOUND, null, equipmentId));
        var equipmentPriceDomain = equipmentPriceHistoryRepository
                .findLatestValidFrom(equipmentDomain.getId().toString())
                .orElse(null);

        log.info("services: {}", equipmentDomain);
        equipmentAppMapper.updateDomainFromDto(request, equipmentDomain);
        log.info("service updated: {}", equipmentDomain);

        var savedEquipment = equipmentRepository.save(equipmentDomain);

        var responseEquipment = equipmentAppMapper.toResponse(savedEquipment);
        responseEquipment.setPrice(equipmentPriceHistoryAppMapper.toResponse(equipmentPriceDomain));
        return responseEquipment;
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

    public Page<EquipmentResponse> searchFilterEquipment(
            EquipmentFilterRequest filter,
            int page,
            int size,
            String sortBy,
            String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        EquipmentFilter equipmentFilter = EquipmentFilter.builder()
                .keyword(filter.keyword())
                .searchBy(filter.searchBy())
                .status(filter.status())
                .brand(filter.brand())
                .fromPrice(filter.fromPrice())
                .toPrice(filter.toPrice())
                .validPriceFrom(filter.validPriceFrom())
                .validPriceEnd(filter.validPriceEnd())
                .build();

        return equipmentEntityService
                .filterPageEquipments(equipmentFilter, pageable)
                .map(equipmentAppMapper::toResponse);
    }

    @Transactional
    public EquipmentResponse addPriceNew(String equipmentId, EquipmentPriceHistoryCreateRequest request) {
        var equipmentDomain = equipmentRepository.findByIdAndStatus(equipmentId, DeleteStatusType.getDefaultString())
                .orElseThrow(() -> new AppException(ErrorCode.EQUIPMENT_NOT_FOUND));

        var validEnd = Instant.now();

        var equipmentPriceOldDomain = equipmentPriceHistoryRepository.findLatestValidFrom(equipmentId);

        if (equipmentPriceOldDomain.isPresent()) {
            var oldPriceDomain = equipmentPriceOldDomain.get();
            oldPriceDomain.setValidEnd(validEnd);
            oldPriceDomain = equipmentPriceHistoryRepository.save(oldPriceDomain);

        }

        var equipmentPriceNewDomain = EquipmentPriceHistory.builder()
                .equipment(equipmentDomain)
                .unitPrice(request.getUnitPrice())
                .validFrom(validEnd)
                .build();

        // logic add new roomClassPriceHistory

        var savedEquipmentPrice = equipmentPriceHistoryRepository.save(equipmentPriceNewDomain);

        equipmentDomain.setPrice(savedEquipmentPrice);

        return equipmentAppMapper.toResponse(equipmentDomain);
    }


    public EquipmentDetailResponse getDetailEquipment(String equipmentId) {
        var equipmentDomain = equipmentRepository.findByIdAndStatus(equipmentId, DeleteStatusType.ACTIVE.toString())
                .orElseThrow(() -> new AppException(ErrorCode.EQUIPMENT_NOT_FOUND));

        var equipmentPriceDomain = equipmentPriceHistoryRepository.findLatestValidFrom(equipmentId)
                .orElseThrow(() -> new AppException(ErrorCode.EQUIPMENT_NOT_FOUND));

        equipmentDomain.setPrice(equipmentPriceDomain);

        return equipmentAppMapper.toResponseDetail(equipmentDomain);
    }
}
