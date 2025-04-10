package com.roomx.infrastructure.cache.redis.service;

import com.roomx.domain.model.entity.DateRequestException;
import com.roomx.shared.dto.booking.base.TimeRange;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public interface RoomCheckingCacheService {
    List<DateRequestException> getOtherUserCheckingSlots(String currentUserId);

}
