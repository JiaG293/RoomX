package com.roomx.infrastructure.persistence.service;

import com.roomx.domain.model.aggrerate.Room;
import com.roomx.infrastructure.persistence.dto.RoomFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomEntityService {
    Page<Room> filterPageRooms(RoomFilter filter, Pageable pageable, boolean typeCompare);
}
