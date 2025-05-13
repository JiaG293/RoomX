package com.roomx.infrastructure.persistence.service.impl;

import com.roomx.domain.model.aggrerate.RoomClass;
import com.roomx.domain.repository.RoomClassRepository;
import com.roomx.infrastructure.persistence.dto.RoomClassFilter;
import com.roomx.infrastructure.persistence.model.projection.RoomClassProjection;
import com.roomx.infrastructure.persistence.repository.jpa.JpaRoomClassEntityRepository;
import com.roomx.infrastructure.persistence.service.RoomClassEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomClassEntityServiceImpl implements RoomClassEntityService {
    private final JpaRoomClassEntityRepository jpaRoomClassEntityRepository;


    @Override
    public Page<RoomClassProjection> filterSearchRoomClass(RoomClassFilter filter, Pageable pageable) {
        return jpaRoomClassEntityRepository.filterSearchRoomClass(
                filter.getKeyword(),
                filter.getSearchBy(),
                filter.getStatus(),
                filter.getCapacity(),
                filter.getStartPrice(),
                filter.getEndPrice(),
                pageable
        );
    }
}
