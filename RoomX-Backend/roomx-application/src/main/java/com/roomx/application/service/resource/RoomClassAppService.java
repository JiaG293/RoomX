package com.roomx.application.service.resource;

import com.roomx.shared.dto.resource.request.RoomClassCreateRequest;
import com.roomx.shared.dto.resource.request.RoomClassUpdateRequest;
import com.roomx.shared.dto.resource.response.RoomClassDetailResponse;
import com.roomx.shared.dto.resource.response.RoomClassResponse;
import com.roomx.application.mapper.EquipmentRoomClassAppMapper;
import com.roomx.application.mapper.RoomClassAppMapper;
import com.roomx.application.mapper.ServiceRoomClassAppMapper;
import com.roomx.domain.repository.EquipmentRoomClassRepository;
import com.roomx.domain.repository.RoomClassRepository;
import com.roomx.domain.repository.ServiceRoomClassRepository;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

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

        var serviceRoomClassList = new HashSet<>(serviceRoomClassRepository.findAllByRoomClassId(roomClassId));
        roomClassDomain.setServices(serviceRoomClassList);

        return RoomClassDetailResponse.builder()
                .roomClass(roomClassAppMapper.toResponse(roomClassDomain))
                .equipments(equipmentRoomClassList.stream()
                        .map(equipmentRoomClassAppMapper::toResponseDetailWithoutRoomClass)
                        .toList())
                .services(serviceRoomClassList.stream()
                        .map(serviceRoomClassAppMapper::toResponseDetailWithoutRoomClass)
                        .toList())
                .build();
    }

    public RoomClassDetailResponse getDetailRoomClassByRoomClassCode(String roomClassCode) {
        return null;
    }
}
