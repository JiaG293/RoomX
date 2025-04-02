package com.roomx.infrastructure.persistence.service.impl;

import com.roomx.domain.model.aggrerate.Room;
import com.roomx.infrastructure.persistence.dto.RoomFilter;
import com.roomx.infrastructure.persistence.mapper.RoomEntityMapper;
import com.roomx.infrastructure.persistence.model.base.GenericSpecification;
import com.roomx.infrastructure.persistence.model.base.SearchCriteria;
import com.roomx.infrastructure.persistence.model.entity.RoomEntity;
import com.roomx.infrastructure.persistence.repository.jpa.JpaRoomEntityRepository;
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
    public Page<Room> filterPageRooms(RoomFilter filter, Pageable pageable, boolean typeCompare) {

        // use filter search like, AND
        String method = "%";
        boolean condition = typeCompare; // TRUE = OR || FALSE = AND
        List<SearchCriteria> filters = Stream.of(
                        new AbstractMap.SimpleEntry<>("id", filter.getId()),
                        new AbstractMap.SimpleEntry<>("roomCode", filter.getRoomCode()),
                        new AbstractMap.SimpleEntry<>("status", filter.getStatus()),
                        new AbstractMap.SimpleEntry<>("description", filter.getDescription()),
                       /* new AbstractMap.SimpleEntry<>("roomClass.price", filter.getEndPrice()),
                        (filter.getStartPrice() != null && filter.getEndPrice() != null)
                                ? new AbstractMap.SimpleEntry<>("roomClass.price", List.of(filter.getStartPrice(), filter.getEndPrice()))
                                : null,*/
                        new AbstractMap.SimpleEntry<>("place.building", filter.getBuilding()),
                        new AbstractMap.SimpleEntry<>("place.floor", filter.getFloor()),
                        new AbstractMap.SimpleEntry<>("place.name", filter.getPlaceName()),
                        new AbstractMap.SimpleEntry<>("branch.slug", filter.getSlug()),
                        new AbstractMap.SimpleEntry<>("branch.name", filter.getBranchName()),
                        new AbstractMap.SimpleEntry<>("branch.code", filter.getBranchCode())
                ).filter(entry -> entry.getValue() != null && !entry.getValue().toString().isBlank())
                .map(entry -> new SearchCriteria(entry.getKey(), entry.getValue(), method, condition))
                .toList();

        log.info("Searching data: {}", filters);

        Specification<RoomEntity> spec = new GenericSpecification<>(filters);

        var roomEntityPage = jpaRoomEntityRepository.findAll(spec, pageable);


        return roomEntityPage.map(roomEntityMapper::toDomain);
    }
}
