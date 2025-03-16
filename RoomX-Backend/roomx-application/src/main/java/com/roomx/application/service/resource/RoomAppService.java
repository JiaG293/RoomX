package com.roomx.application.service.resource;

import com.roomx.application.dto.resource.request.RoomQueryRequest;
import com.roomx.application.dto.resource.request.RoomCreateRequest;
import com.roomx.application.dto.resource.request.RoomUpdateStatusRequest;
import com.roomx.application.dto.resource.response.EquipmentResponse;
import com.roomx.application.dto.resource.response.RoomDetailResponse;
import com.roomx.application.dto.resource.response.RoomResponse;
import com.roomx.application.mapper.RoomAppMapper;
import com.roomx.domain.model.aggrerate.Room;
import com.roomx.domain.model.enums.RoomStatusType;
import com.roomx.domain.repository.PlaceRepository;
import com.roomx.domain.repository.RoomClassRepository;
import com.roomx.domain.repository.RoomRepository;
import com.roomx.infrastructure.multitenancy.persistence.dto.EquipmentFilter;
import com.roomx.infrastructure.multitenancy.persistence.dto.RoomFilter;
import com.roomx.infrastructure.multitenancy.persistence.repository.jpa.JpaRoomEntityRepository;
import com.roomx.infrastructure.multitenancy.persistence.service.RoomEntityService;
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
public class RoomAppService {
    private final RoomRepository roomRepository;
    private final RoomAppMapper roomAppMapper;
    private final PlaceRepository placeRepository;
    private final RoomClassRepository roomClassRepository;
    private final JpaRoomEntityRepository jpaRoomEntityRepository;
    private final RoomEntityService roomEntityService;

    @Transactional
    public RoomDetailResponse createRoom(RoomCreateRequest request) {
        if(roomRepository.checkExistsRoomCode(request.getRoomCode())){
            throw new AppException(ErrorCode.ROOM_CONFLICT, request.getRoomCode());
        }

        var placeDomain = placeRepository.findById(request.getPlaceId())
                .orElseThrow(() -> new AppException(ErrorCode.PLACE_NOT_FOUND, request.getPlaceId()));

        var roomClassDomain = roomClassRepository.findById(request.getRoomClassId())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_CLASS_NOT_FOUND, request.getRoomClassId()));


        var roomDomain = Room.builder()
                .roomClass(roomClassDomain)
                .place(placeDomain)
                .roomCode(request.getRoomCode())
                .status(request.getStatus() == null || request.getStatus().isEmpty() ? request.getStatus() : RoomStatusType.AVAILABLE.toString())
                .description(request.getDescription())
                .build();

        var savedRoom = roomRepository.save(roomDomain);

        var response = roomAppMapper.toResponseDetail(savedRoom);
        response.setTotalPrice(roomDomain.getRoomClass().getTotalPrice());
        return response;
    }

    @Transactional
    public RoomResponse updateStatusRoom(String roomId, RoomUpdateStatusRequest request) {
        var roomDomain = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND, roomId));

        roomDomain.setStatus(request.status());

        // BOOKING REQUEST MOVING ROOM ANOTHER

        return null;
    }

    public Page<RoomResponse> getListRoomPages(
            RoomQueryRequest filterRequest,
            int page,
            int size,
            String sortBy,
            String direction){
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        RoomFilter roomFilter = RoomFilter.builder()
                .id(filterRequest.getId())
                .roomCode(filterRequest.getRoomCode())
                .status(filterRequest.getStatus())
                .description(filterRequest.getDescription())
                .building(filterRequest.getBuilding())
                .floor(filterRequest.getFloor())
                .placeName(filterRequest.getPlaceName())
                .slug(filterRequest.getSlug())
                .branchName(filterRequest.getBranchName())
                .branchCode(filterRequest.getBranchCode())
                .build();


        var roomDomainPage = roomEntityService.filterPageRooms(roomFilter, pageable, filterRequest.isCompareType());


        return roomDomainPage.map(roomAppMapper::toResponse);
    }
}
