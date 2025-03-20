package com.roomx.application.service.resource;

import com.roomx.shared.dto.resource.request.ServiceRoomClassCreateRequest;
import com.roomx.shared.dto.resource.response.ServiceRoomClassDetailResponse;
import com.roomx.application.mapper.ServiceRoomClassAppMapper;
import com.roomx.domain.model.entity.ServiceRoomClass;
import com.roomx.domain.model.vo.ServiceRoomClassId;
import com.roomx.domain.repository.RoomClassRepository;
import com.roomx.domain.repository.ServiceRepository;
import com.roomx.domain.repository.ServiceRoomClassRepository;
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
public class ServiceRoomClassAppService {
    private final ServiceRoomClassAppMapper serviceRoomClassAppMapper;
    private final ServiceRoomClassRepository serviceRoomClassRepository;
    private final RoomClassRepository roomClassRepository;
    private final ServiceRepository serviceRepository;

    @Transactional
    public List<ServiceRoomClassDetailResponse> addServiceToRoomClass(String roomClassId, List<ServiceRoomClassCreateRequest> request) {

        var roomClassDomain = roomClassRepository.findById(roomClassId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_CLASS_NOT_FOUND, roomClassId));

        var serviceCanAdd = request.stream()
                .map(req -> new AbstractMap.SimpleEntry<>(UUID.fromString(req.getServiceId()), req.getQuantity()))
                .filter(entry -> {
                    boolean exists = serviceRoomClassRepository.checkExistsByServiceRoomClassId(
                            new ServiceRoomClassId(UUID.fromString(roomClassId), entry.getKey()));
                    if (exists) {
                        throw new AppException(ErrorCode.SERVICE_ROOM_CLASS_CONFLICT, entry.getKey());
                    }
                    return !exists;
                })
                .map(entry -> serviceRepository.findById(entry.getKey().toString())
                        .map(service -> ServiceRoomClass.builder()
                                .id(new ServiceRoomClassId(UUID.fromString(roomClassId), service.getId()))
                                .roomClass(roomClassDomain)
                                .service(service)
                                .quantity(entry.getValue().shortValue())
                                .build()))
                .flatMap(Optional::stream)
                .toList();

        var savedEntity = serviceRoomClassRepository.saveAll(serviceCanAdd);

        return savedEntity.stream()
                .map(serviceRoomClassAppMapper::toResponseDetail)
                .toList();


    }
}
