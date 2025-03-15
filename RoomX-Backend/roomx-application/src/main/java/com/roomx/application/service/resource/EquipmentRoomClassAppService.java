package com.roomx.application.service.resource;

import com.roomx.application.dto.resource.request.EquipmentRoomClassCreateRequest;
import com.roomx.application.dto.resource.response.EquipmentRoomClassDetailResponse;
import com.roomx.application.dto.resource.response.EquipmentRoomClassResponse;
import com.roomx.application.mapper.EquipmentRoomClassAppMapper;
import com.roomx.domain.model.entity.EquipmentRoomClass;
import com.roomx.domain.model.vo.EquipmentRoomClassId;
import com.roomx.domain.repository.EquipmentRepository;
import com.roomx.domain.repository.EquipmentRoomClassRepository;
import com.roomx.domain.repository.RoomClassRepository;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Transactional
    public List<EquipmentRoomClassDetailResponse> addEquipmentToRoomClass(String roomClassId, List<EquipmentRoomClassCreateRequest> request) {

        var roomClassDomain = roomClassRepository.findById(roomClassId)
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
                                .unitPrice(equipment.getUnitPrice())
                                .build()))
                .flatMap(Optional::stream)
                .toList();

        var savedEntity = equipmentRoomClassRepository.saveAll(equipmentCanAdd);

        return savedEntity.stream()
                .map(equipmentRoomClassAppMapper::toResponseDetail)
                .toList();


    }


}
