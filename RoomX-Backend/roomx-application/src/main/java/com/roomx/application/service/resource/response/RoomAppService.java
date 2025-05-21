package com.roomx.application.service.resource.response;

import com.roomx.application.mapper.*;
import com.roomx.domain.repository.RoomClassPriceHistoryRepository;
import com.roomx.infrastructure.persistence.repository.impl.EquipmentPriceHistoryEntityRepository;
import com.roomx.infrastructure.persistence.repository.impl.EquipmentRoomClassEntityRepository;
import com.roomx.infrastructure.persistence.repository.impl.ServicePriceHistoryEntityRepository;
import com.roomx.infrastructure.persistence.repository.impl.ServiceRoomClassEntityRepository;
import com.roomx.shared.dto.resource.request.*;
import com.roomx.shared.dto.resource.response.*;
import com.roomx.domain.model.aggrerate.Room;
import com.roomx.shared.enums.RoomStatusType;
import com.roomx.domain.repository.PlaceRepository;
import com.roomx.domain.repository.RoomClassRepository;
import com.roomx.domain.repository.RoomRepository;
import com.roomx.infrastructure.persistence.dto.RoomFilter;
import com.roomx.infrastructure.persistence.repository.jpa.JpaRoomEntityRepository;
import com.roomx.infrastructure.persistence.service.RoomEntityService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomAppService {
    private final RoomRepository roomRepository;
    private final RoomAppMapper roomAppMapper;
    private final PlaceRepository placeRepository;
    private final RoomClassRepository roomClassRepository;
    private final RoomClassPriceHistoryRepository roomClassPriceHistoryRepository;
    private final JpaRoomEntityRepository jpaRoomEntityRepository;
    private final RoomEntityService roomEntityService;
    private final EquipmentRoomClassEntityRepository equipmentRoomClassEntityRepository;
    private final ServicePriceHistoryEntityRepository servicePriceHistoryEntityRepository;
    private final RoomClassAppMapper roomClassAppMapper;
    private final EquipmentRoomClassAppMapper equipmentRoomClassAppMapper;
    private final ServiceRoomClassAppMapper serviceRoomClassAppMapper;
    private final EquipmentPriceHistoryEntityRepository equipmentPriceHistoryEntityRepository;
    private final ServiceRoomClassEntityRepository serviceRoomClassEntityRepository;
    private final PlaceAppMapper placeAppMapper;

    @Transactional
    public RoomDetailResponse createRoom(RoomCreateRequest request) {
        if(roomRepository.checkExistsRoomCode(request.getRoomCode())){
            throw new AppException(ErrorCode.ROOM_CONFLICT, request.getRoomCode());
        }

        var placeDomain = placeRepository.findById(request.getPlaceId())
                .orElseThrow(() -> new AppException(ErrorCode.PLACE_NOT_FOUND, request.getPlaceId()));

        var roomClassDomain = roomClassRepository.findById(request.getRoomClassId())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_CLASS_NOT_FOUND, request.getRoomClassId()));

        var roomClassPriceDomain = roomClassPriceHistoryRepository.findLatestValidFrom(request.getRoomClassId())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_CLASS_NOT_FOUND, request.getRoomClassId()));



        var roomDomain = Room.builder()
                .roomClass(roomClassDomain)
                .place(placeDomain)
                .roomCode(request.getRoomCode())
                .status(request.getStatus() == null || request.getStatus().isEmpty() ? RoomStatusType.AVAILABLE.toString() : request.getStatus())
                .description(request.getDescription())
                .imageUrls(request.getImageUrls() == null ? new ArrayList<String>() : request.getImageUrls())
                .build();

        var savedRoom = roomRepository.save(roomDomain);

        savedRoom.getRoomClass().setPrice(roomClassPriceDomain);

        var response = roomAppMapper.toResponseDetail(savedRoom);
        response.setTotalPrice(roomClassPriceHistoryRepository
                .calculateTotalPrice(roomClassDomain.getId().toString()).getTotalPrice());
        return response;
    }

    @Transactional
    public RoomResponse updateStatusRoom(String roomId, RoomUpdateStatusRequest request) {
        var roomDomain = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND, roomId));

        var roomStatusUpdate = request.status().toUpperCase();
        if(RoomStatusType.getStatusList().contains(roomStatusUpdate)){
            roomDomain.setStatus(request.status());
        } else {
            throw new AppException(ErrorCode.ROOM_STATUS_INVALID, null, request.status());
        }

        var updatedRoom = roomRepository.save(roomDomain);

        return roomAppMapper.toResponse(updatedRoom);
    }

    public Page<RoomFilterResponse> getListRoomPages(
            RoomFilterRequest filter,
            int page,
            int size,
            String sortBy,
            String direction){
        if (size == -1) {
            size = Integer.MAX_VALUE;
        }

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        RoomFilter roomFilter = RoomFilter.builder()
                .keyword(filter.keyword())
                .searchBy(filter.searchBy())
                .status(filter.status())
                .branchId(filter.branchId())
                .buildingId(filter.buildingId())
                .floorId(filter.floorId())
                .capacity(filter.capacity())
                .startPrice(filter.startPrice())
                .endPrice(filter.endPrice())
                .build();


        return roomEntityService
                .filterSearchPageRooms(roomFilter, pageable)
                .map(roomAppMapper::toResponseFilter);
    }



    public Room getRoomFree(String roomId) {
        var roomDomain = roomRepository.findById(roomId)
                .orElseThrow(()-> new AppException(ErrorCode.ROOM_NOT_FOUND, roomId));


        return roomDomain;
    }

    public RoomDetailResponse updateRoom(String roomId, RoomUpdateRequest request) {
        var roomDomain = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND, roomId));

        if (!request.getImageUrls().isEmpty()) {
            roomDomain.setImageUrls(new ArrayList<>(new LinkedHashSet<>(request.getImageUrls())));
        } else {
            roomDomain.setImageUrls(new ArrayList<>());
        }

        roomDomain.setDescription(request.getDescription());

        var updatedRoom = roomRepository.save(roomDomain);

        return roomAppMapper.toResponseDetail(updatedRoom);
    }

    public RoomDetailAllResponse getDetailRoom(String roomId) {
        var roomDomain = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND, roomId));
        var roomClassId = roomDomain.getRoomClass().getId().toString();
        var roomClassDomain = roomClassRepository.findById(roomClassId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_CLASS_NOT_FOUND, roomDomain.getRoomClass().getId().toString()));

        AtomicReference<BigDecimal> equipmentsTotalPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> servicesTotalPrice = new AtomicReference<>(BigDecimal.ZERO);


        var roomClassPriceHistoryDomain = roomClassPriceHistoryRepository.findLatestValidFrom(roomClassId)
                .orElse(null);

        if (roomClassPriceHistoryDomain != null) {
            roomClassDomain.setPrice(roomClassPriceHistoryDomain);
        }

        var equipmentRoomClassList = new HashSet<>(equipmentRoomClassEntityRepository.findAllByRoomClassId(roomClassId));
        roomClassDomain.setEquipments(equipmentRoomClassList);

        var serviceRoomClassList = new HashSet<>(serviceRoomClassEntityRepository.findAllByRoomClassId(roomClassId));
        roomClassDomain.setServices(serviceRoomClassList);

        var equipmentsResponse = equipmentRoomClassList.stream()
                .map(equipmentRoomClass -> {

                    var equipmentPriceHistoryDomain = equipmentPriceHistoryEntityRepository.findLatestValidFrom(equipmentRoomClass.getEquipment().getId().toString())
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

                    var servicePriceHistoryDomain = servicePriceHistoryEntityRepository.findLatestValidFrom(serviceRoomClass.getService().getId().toString())
                            .orElse(null);
                    serviceRoomClass.setPrice(servicePriceHistoryDomain);

                    if (serviceRoomClass.getTotalPrice() != null) {
                        servicesTotalPrice.updateAndGet(total -> total.add(serviceRoomClass.getTotalPrice()));
                    }

                    return serviceRoomClassAppMapper.toResponseDetail(serviceRoomClass);
                })
                .toList();


        BigDecimal totalPrice = equipmentsTotalPrice.get().add(servicesTotalPrice.get()).add(roomClassDomain.getPrice().getBasePrice());

        return RoomDetailAllResponse.builder()
                .id(roomDomain.getId().toString())
                .place(placeAppMapper.toResponse(roomDomain.getPlace()))
                .description(roomDomain.getDescription())
                .imageUrls(roomDomain.getImageUrls())
                .status(roomDomain.getStatus())
                .roomClass(roomClassAppMapper.toResponse(roomClassDomain))
                .equipments(equipmentsResponse)
                .services(servicesResponse)
                .equipmentsTotalPrice(equipmentsTotalPrice.get())
                .servicesTotalPrice(servicesTotalPrice.get())
                .totalPrice(totalPrice)
                .build();
    }


    public RoomMaxMinResponse getMaxMinRoomCapacity(){
        return roomRepository.findMinMaxCapacity();
    }
}
