package com.roomx.application.service.resource.response;

import com.roomx.domain.model.entity.EquipmentPriceHistory;
import com.roomx.domain.repository.EquipmentPriceHistoryRepository;
import com.roomx.shared.dto.resource.request.EquipmentRoomClassCreateRequest;
import com.roomx.shared.dto.resource.response.EquipmentRoomClassDetailResponse;
import com.roomx.application.mapper.EquipmentRoomClassAppMapper;
import com.roomx.domain.model.entity.EquipmentRoomClass;
import com.roomx.domain.model.vo.EquipmentRoomClassId;
import com.roomx.domain.repository.EquipmentRepository;
import com.roomx.domain.repository.EquipmentRoomClassRepository;
import com.roomx.domain.repository.RoomClassRepository;
import com.roomx.shared.dto.resource.response.EquipmentRoomClassResponse;
import com.roomx.shared.enums.DeleteStatusType;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipmentRoomClassAppService {

    private final EquipmentRepository equipmentRepository;
    private final RoomClassRepository roomClassRepository;
    private final EquipmentRoomClassRepository equipmentRoomClassRepository;
    private final EquipmentRoomClassAppMapper equipmentRoomClassAppMapper;
    private final EquipmentPriceHistoryRepository equipmentPriceHistoryRepository;

    @Transactional
    public List<EquipmentRoomClassResponse> addEquipmentToRoomClass(String roomClassId, List<EquipmentRoomClassCreateRequest> request) {

        var roomClassDomain = roomClassRepository.findByIdAndStatus(roomClassId, DeleteStatusType.getDefaultString())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_CLASS_NOT_FOUND, roomClassId));

        var equipmentCanAdd = request.stream()
                .map(req -> new AbstractMap.SimpleEntry<>(UUID.fromString(req.getEquipmentId()), req.getQuantity()))
                .filter(entry -> {
                    boolean exists = equipmentRoomClassRepository.checkExistsByEquipmentRoomClassId(
                            new EquipmentRoomClassId(UUID.fromString(roomClassId), entry.getKey()));
                    if (exists) {
                        throw new AppException(ErrorCode.EQUIPMENT_ROOM_CLASS_CONFLICT, entry.getKey());
                    }
                    return !exists;
                })
                .map(entry -> equipmentRepository.findById(entry.getKey().toString())
                        .map(equipment -> EquipmentRoomClass.builder()
                                .id(new EquipmentRoomClassId(UUID.fromString(roomClassId), equipment.getId()))
                                .roomClass(roomClassDomain)
                                .equipment(equipment)
                                .quantity(entry.getValue().shortValue())
                                .build()))
                .flatMap(Optional::stream)
                .toList();

        var savedEntity = equipmentRoomClassRepository.saveAll(equipmentCanAdd);

        var equipmentRoomClassWithPrice = savedEntity.stream()
                .map(equipmentRoomClass -> {
                    var equipmentPrice = equipmentPriceHistoryRepository
                            .findLatestValidFrom(equipmentRoomClass.getEquipment().getId().toString())
                            .orElse(null);
                    equipmentRoomClass.setPrice(equipmentPrice);
                    return equipmentRoomClass;
                }).toList();

        return savedEntity.stream()
                .map(equipmentRoomClassAppMapper::toResponse)
                .toList();


    }


}
