package com.roomx.application.service.resource;

import com.roomx.application.dto.resource.request.RoomClassCreateRequest;
import com.roomx.application.dto.resource.request.RoomClassUpdateRequest;
import com.roomx.application.dto.resource.response.RoomClassDetailResponse;
import com.roomx.application.dto.resource.response.RoomClassResponse;
import com.roomx.application.mapper.EquipmentRoomClassAppMapper;
import com.roomx.application.mapper.RoomClassAppMapper;
import com.roomx.domain.model.entity.EquipmentRoomClass;
import com.roomx.domain.repository.EquipmentRoomClassRepository;
import com.roomx.domain.repository.RoomClassRepository;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomClassAppService {
    private final EquipmentRoomClassAppMapper equipmentRoomClassAppMapper;
    private final RoomClassRepository roomClassRepository;
    private final RoomClassAppMapper roomClassAppMapper;
    private final EquipmentRoomClassRepository equipmentRoomClassRepository;

    @Transactional
    public RoomClassResponse createRoomClass(RoomClassCreateRequest request) {
        if (roomClassRepository.checkExistsRoomClassCode(request.getRoomClassCode())) {
            throw new AppException(ErrorCode.ROOM_CLASS_CONFLICT, request.getRoomClassCode());
        }

        var roomClassDomain = roomClassAppMapper.toDomain(request);


        var savedRoomClass = roomClassRepository.save(roomClassDomain);

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

        var equipmentRoomClassList = new HashSet<>(equipmentRoomClassRepository.findAllByRoomClassId(roomClassId));
        roomClassDomain.setEquipments(equipmentRoomClassList);

        return RoomClassDetailResponse.builder()
                .roomClass(roomClassAppMapper.toResponse(roomClassDomain))
                .equipments(equipmentRoomClassList.stream()
                        .map(equipmentRoomClassAppMapper::toResponseDetailWithoutRoomClass)
                        .toList())
                .services(List.of())
                .totalPrice(roomClassDomain.getTotalPrice())
                .build();
    }

    public RoomClassDetailResponse getDetailRoomClassByRoomClassCode(String roomClassCode) {
        return null;
    }
}
