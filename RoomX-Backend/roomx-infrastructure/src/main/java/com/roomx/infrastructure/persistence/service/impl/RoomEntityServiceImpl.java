package com.roomx.infrastructure.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Place;
import com.roomx.domain.model.aggrerate.Room;
import com.roomx.domain.model.aggrerate.RoomClass;
import com.roomx.infrastructure.persistence.dto.RoomFilter;
import com.roomx.infrastructure.persistence.mapper.RoomEntityMapper;
import com.roomx.infrastructure.persistence.model.base.GenericSpecification;
import com.roomx.infrastructure.persistence.model.base.SearchCriteria;
import com.roomx.infrastructure.persistence.model.entity.RoomEntity;
import com.roomx.infrastructure.persistence.model.projection.RoomProjection;
import com.roomx.infrastructure.persistence.repository.jpa.JpaRoomEntityRepository;
import com.roomx.infrastructure.persistence.repository.specification.RoomSpecification;
import com.roomx.infrastructure.persistence.service.RoomEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomEntityServiceImpl implements RoomEntityService {
    private final JpaRoomEntityRepository jpaRoomEntityRepository;
    private final RoomEntityMapper roomEntityMapper;


    @Override
    public Page<RoomProjection> filterSearchPageRooms(RoomFilter filter, Pageable pageable) {

       /* return jpaRoomEntityRepository
                .findRoomsWithFilters(
                        filter.getId(),
                        filter.getRoomCode(),
                        filter.getStatus(),
                        filter.getStartPrice(),
                        filter.getEndPrice(),
                        filter.getBranchId(),
                        filter.getBuildingId(),
                        filter.getFloorId(),
                        filter.getCapacity(),
                        filter.getSearchBy(),
                        filter.getKeyword(),
                        pageable)
                .map(roomProjection -> Room.builder()
                        .id(roomProjection.getId())
                        .roomCode(roomProjection.getRoomCode())
                        .status(roomProjection.getStatus())
                        .description(roomProjection.getDescription())
                        .place(Place.builder()
                                .id(roomProjection.getFloorPlaceId())
                                .build())
                        .roomClass(RoomClass.builder()
                                .id(roomProjection.getRoomClassId())
                                .roomClassCode(roomProjection.getRoomClassCode())
                                .capacity(roomProjection.getCapacity())
                                .roomClassPriceHistories(roomProjection.getE)
                                .build())
                        .build());*/
        return jpaRoomEntityRepository
                .findRoomsWithFilters(
                        filter.getId(),
                        filter.getRoomCode(),
                        filter.getStatus(),
                        filter.getStartPrice(),
                        filter.getEndPrice(),
                        filter.getBranchId(),
                        filter.getBuildingId(),
                        filter.getFloorId(),
                        filter.getCapacity(),
                        filter.getSearchBy(),
                        filter.getKeyword(),
                        pageable
                );
    }
}
