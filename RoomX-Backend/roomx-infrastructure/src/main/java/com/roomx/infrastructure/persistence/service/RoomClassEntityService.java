package com.roomx.infrastructure.persistence.service;

import com.roomx.domain.model.aggrerate.RoomClass;
import com.roomx.infrastructure.persistence.dto.RoomClassFilter;
import com.roomx.infrastructure.persistence.model.projection.RoomClassProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomClassEntityService {
    Page<RoomClassProjection> filterSearchRoomClass(RoomClassFilter roomClassFilter, Pageable pageable);
}
