package com.roomx.infrastructure.cache.redis.service.impl;

import com.roomx.domain.model.entity.DateRequestException;
import com.roomx.infrastructure.cache.redis.service.RedisTenantService;
import com.roomx.infrastructure.cache.redis.service.RoomCheckingCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomCheckingCacheServiceImpl implements RoomCheckingCacheService {
    private final RedisTenantService redisTenantService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<DateRequestException> getOtherUserCheckingSlots(String currentUserId) {
        {
            Set<String> keys = redisTemplate.keys("checking:user:*");
            if (keys == null) return Collections.emptyList();

            List<DateRequestException> result = new ArrayList<>();
            for (String key : keys) {
                if (!key.equals("checking:user:" + currentUserId)) {
                    List<DateRequestException> slots = (List<DateRequestException>) redisTemplate.opsForValue().get(key);
                    if (slots != null) result.addAll(slots);
                }
            }
            return result;
        }
    }

}
