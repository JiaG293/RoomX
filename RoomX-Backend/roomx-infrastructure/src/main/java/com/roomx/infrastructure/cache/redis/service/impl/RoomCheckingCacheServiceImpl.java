package com.roomx.infrastructure.cache.redis.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.entity.DateRequestException;
import com.roomx.infrastructure.cache.redis.service.RedisTenantService;
import com.roomx.infrastructure.cache.redis.service.RoomCheckingCacheService;
import com.roomx.shared.dto.booking.base.OfferBookingSlot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomCheckingCacheServiceImpl implements RoomCheckingCacheService {
    private final RedisTenantService redisTenantService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

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

    @Override
    public void pushPendingBookingRequestToRedis(BookingRequest request) {
        List<LocalDate> occurrences = request.getOccurrences();

        for (LocalDate date : occurrences) {
            LocalTime startTime = request.getStartTime();
            LocalTime endTime = request.getEndTime();

            if (request.getDateRequestExceptions() != null) {
                for (DateRequestException exception : request.getDateRequestExceptions()) {
                    if (exception.getDate().equals(date)) {
                        startTime = exception.getStartTime();
                        endTime = exception.getEndTime();
                        break;
                    }
                }
            }

            String redisKey = String.format("pending:booking:%s", date);
            String value = String.format("%s|%s|%s", request.getId(), startTime, endTime);

            List<String> existing = stringRedisTemplate.opsForList().range(redisKey, 0, -1);
            if (existing != null) {
                List<String> toRemove = existing.stream()
                        .filter(s -> s.startsWith(request.getId().toString() + "|"))
                        .collect(Collectors.toList());
                toRemove.forEach(val -> redisTemplate.opsForList().remove(redisKey, 0, val));
            }

            redisTemplate.opsForList().rightPush(redisKey, value);
            redisTemplate.expire(redisKey, Duration.ofHours(1));
        }
    }

    @Override
    public boolean checkRequestConflictWithPending(
            LocalDate date,
            LocalTime checkStart,
            LocalTime checkEnd
    ) {
        String redisKey = String.format("pending:booking:%s", date);
        List<String> pending = stringRedisTemplate.opsForList().range(redisKey, 0, -1);

        if (pending == null) return false;

        for (String val : pending) {
            String[] parts = val.split("\\|");
            if (parts.length != 3) continue;

            LocalTime start = LocalTime.parse(parts[1]);
            LocalTime end = LocalTime.parse(parts[2]);

            if (isTimeOverlap(start, end, checkStart, checkEnd)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void removeBookingRequestFromRedis(UUID bookingRequestId, List<LocalDate> dates) {
        for (LocalDate date : dates) {
            String redisKey = String.format("pending:booking:%s", date);
            List<String> existing = stringRedisTemplate.opsForList().range(redisKey, 0, -1);
            if (existing != null) {
                List<String> toRemove = existing.stream()
                        .filter(s -> s.startsWith(bookingRequestId.toString() + "|"))
                        .collect(Collectors.toList());
                toRemove.forEach(val -> redisTemplate.opsForList().remove(redisKey, 0, val));
            }
        }
    }

    private boolean isTimeOverlap(LocalTime s1, LocalTime e1, LocalTime s2, LocalTime e2) {
        return !e1.isBefore(s2) && !s1.isAfter(e2);
    }




}
