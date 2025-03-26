package com.roomx.application.service.resource;

import com.roomx.domain.model.aggrerate.Room;
import com.roomx.domain.model.entity.EquipmentPriceHistory;
import com.roomx.domain.model.entity.RoomClassPriceHistory;
import com.roomx.domain.repository.*;
import com.roomx.shared.dto.resource.request.EquipmentPriceHistoryCreateRequest;
import com.roomx.shared.dto.resource.request.RoomClassCreateRequest;
import com.roomx.shared.dto.resource.request.RoomClassPriceHistoryCreateRequest;
import com.roomx.shared.dto.resource.request.RoomClassUpdateRequest;
import com.roomx.shared.dto.resource.response.EquipmentResponse;
import com.roomx.shared.dto.resource.response.RoomClassDetailResponse;
import com.roomx.shared.dto.resource.response.RoomClassResponse;
import com.roomx.application.mapper.EquipmentRoomClassAppMapper;
import com.roomx.application.mapper.RoomClassAppMapper;
import com.roomx.application.mapper.ServiceRoomClassAppMapper;
import com.roomx.shared.enums.DeleteStatusType;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomClassAppService {
    private final ServiceRoomClassAppMapper serviceRoomClassAppMapper;
    private final EquipmentRoomClassAppMapper equipmentRoomClassAppMapper;
    private final RoomClassRepository roomClassRepository;
    private final RoomClassAppMapper roomClassAppMapper;
    private final EquipmentRoomClassRepository equipmentRoomClassRepository;
    private final ServiceRoomClassRepository serviceRoomClassRepository;
    private final RoomClassPriceHistoryRepository roomClassPriceHistoryRepository;
    private final EquipmentPriceHistoryRepository equipmentPriceHistoryRepository;
    private final ServicePriceHistoryRepository servicePriceHistoryRepository;

    @Transactional
    public RoomClassResponse createRoomClass(RoomClassCreateRequest request) {
        var roomClassCheckCode = roomClassRepository.findByRoomClassCode(request.getRoomClassCode());

        if (roomClassCheckCode.isPresent()) {
            if (roomClassCheckCode.get().getStatus().equals(DeleteStatusType.getDefaultString())) {
                throw new AppException(ErrorCode.ROOM_CLASS_CONFLICT, roomClassCheckCode.get().getRoomClassCode());
            }
            throw new AppException(ErrorCode.ROOM_CLASS_FORBIDDEN, roomClassCheckCode.get().getRoomClassCode());
        }

        var roomClassDomain = roomClassAppMapper.toDomain(request);
        roomClassDomain.setStatus(DeleteStatusType.getDefaultString());
        var savedRoomClass = roomClassRepository.save(roomClassDomain);

        var roomClassPriceHistory = RoomClassPriceHistory.builder()
                .roomClass(savedRoomClass)
                .validFrom(Instant.now())
                .basePrice(request.getBasePrice())
                .totalPrice(request.getBasePrice())
                .active(true)
                .build();

        var savedRoomClassPrice = roomClassPriceHistoryRepository.save(roomClassPriceHistory);
        savedRoomClass.setPrice(savedRoomClassPrice);
        return roomClassAppMapper.toResponse(savedRoomClass);
    }

    @Transactional
    public RoomClassResponse updateRoomClassById(String roomClassId, RoomClassUpdateRequest request) {
        var roomClassDomain = roomClassRepository.findById(roomClassId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_CLASS_NOT_FOUND, roomClassId));
        roomClassAppMapper.updateDomainFromDto(request, roomClassDomain);

        var savedRoomClass = roomClassRepository.save(roomClassDomain);
        return roomClassAppMapper.toResponse(savedRoomClass);
    }

    public RoomClassDetailResponse getDetailRoomClassById(String roomClassId) {
        var roomClassDomain = roomClassRepository.findById(roomClassId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_CLASS_NOT_FOUND, roomClassId));

        AtomicReference<BigDecimal> equipmentsTotalPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> servicesTotalPrice = new AtomicReference<>(BigDecimal.ZERO);


        var roomClassPriceHistoryDomain = roomClassPriceHistoryRepository.findLatestValidFrom(roomClassId)
                .orElse(null);

        if (roomClassPriceHistoryDomain != null) {
            roomClassDomain.setPrice(roomClassPriceHistoryDomain);
        }

        var equipmentRoomClassList = new HashSet<>(equipmentRoomClassRepository.findAllByRoomClassId(roomClassId));
        roomClassDomain.setEquipments(equipmentRoomClassList);

        var serviceRoomClassList = new HashSet<>(serviceRoomClassRepository.findAllByRoomClassId(roomClassId));
        roomClassDomain.setServices(serviceRoomClassList);

        var equipmentsResponse = equipmentRoomClassList.stream()
                .map(equipmentRoomClass -> {

                    var equipmentPriceHistoryDomain = equipmentPriceHistoryRepository.findLatestValidFrom(equipmentRoomClass.getEquipment().getId().toString())
                            .orElse(null);

                        equipmentRoomClass.setPrice(equipmentPriceHistoryDomain);

                    if (equipmentRoomClass.getTotalPrice() != null) {
                        equipmentsTotalPrice.updateAndGet(total -> total.add(equipmentRoomClass.getTotalPrice()));
                    }

                    return equipmentRoomClassAppMapper.toResponseDetail(equipmentRoomClass);
                })
                .toList();

        var servicesResponse = serviceRoomClassList.stream()
                .map(serviceRoomClass -> {

                    var servicePriceHistoryDomain = servicePriceHistoryRepository.findLatestValidFrom(serviceRoomClass.getService().getId().toString())
                            .orElse(null);
                    serviceRoomClass.setPrice(servicePriceHistoryDomain);

                    if (serviceRoomClass.getTotalPrice() != null) {
                        servicesTotalPrice.updateAndGet(total -> total.add(serviceRoomClass.getTotalPrice()));
                    }

                    return serviceRoomClassAppMapper.toResponseDetail(serviceRoomClass);
                })
                .toList();


        BigDecimal totalPrice = equipmentsTotalPrice.get().add(servicesTotalPrice.get()).add(roomClassDomain.getPrice().getBasePrice());

        return RoomClassDetailResponse.builder()
                .roomClass(roomClassAppMapper.toResponse(roomClassDomain))
                .equipments(equipmentsResponse)
                .services(servicesResponse)
                .equipmentsTotalPrice(equipmentsTotalPrice.get())
                .servicesTotalPrice(servicesTotalPrice.get())
                .totalPrice(totalPrice)
                .build();
    }

    public RoomClassDetailResponse getDetailRoomClassByRoomClassCode(String roomClassCode) {


        return null;
    }


    @Transactional
    public RoomClassResponse addPriceNew(String roomClassId, RoomClassPriceHistoryCreateRequest request) {
        var roomClassDomain = roomClassRepository.findByIdAndStatus(roomClassId, DeleteStatusType.getDefaultString())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_CLASS_NOT_FOUND));

        var validEnd = Instant.now();

        var roomClassPriceOldDomain = roomClassPriceHistoryRepository.findLatestValidFrom(roomClassId);
        if (roomClassPriceOldDomain.isPresent()) {
            var oldPriceDomain = roomClassPriceOldDomain.get();
            oldPriceDomain.setActive(false);
            oldPriceDomain.setValidEnd(validEnd);
            oldPriceDomain = roomClassPriceHistoryRepository.save(oldPriceDomain);

        }


        var roomClassPriceNewDomain = RoomClassPriceHistory.builder()
                .roomClass(roomClassDomain)
                .basePrice(request.getBasePrice())
                .validFrom(validEnd)
                .active(true)
                .build();


        var savedEquipmentPrice = roomClassPriceHistoryRepository.save(roomClassPriceNewDomain);

        roomClassDomain.setPrice(savedEquipmentPrice);

        return roomClassAppMapper.toResponse(roomClassDomain);
    }
}
