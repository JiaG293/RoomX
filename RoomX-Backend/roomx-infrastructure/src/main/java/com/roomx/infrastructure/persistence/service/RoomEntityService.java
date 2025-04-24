package com.roomx.infrastructure.persistence.service;

import com.roomx.domain.model.aggrerate.Room;
import com.roomx.infrastructure.persistence.dto.RoomFilter;
import com.roomx.infrastructure.persistence.model.projection.RoomProjection;
import com.roomx.shared.dto.resource.response.RoomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomEntityService {
    Page<RoomProjection> filterSearchPageRooms(RoomFilter filter, Pageable pageable);

}
