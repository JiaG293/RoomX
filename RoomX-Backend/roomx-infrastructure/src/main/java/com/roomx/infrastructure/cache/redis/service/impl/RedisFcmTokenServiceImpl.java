package com.roomx.infrastructure.cache.redis.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomx.infrastructure.cache.redis.service.RedisFcmTokenService;
import com.roomx.infrastructure.multitenancy.context.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisFcmTokenServiceImpl implements RedisFcmTokenService {

    private final RedisTemplate<String, Object> redisTemplate;

    private String getTenantWithKey(String key) {
        return TenantContextHolder.getTenantIdentifier() + ":" + key;
    }

    @Override
    public void addObjectToSet(String key, Object value) {
        redisTemplate.opsForSet().add(getTenantWithKey(key), value);
    }

    @Override
    public <T> Set<T> getObjectSet(String key, Class<T> clazz) {
        Set<Object> raw = redisTemplate.opsForSet().members(getTenantWithKey(key));
        return raw != null
                ? raw.stream().map(clazz::cast).collect(Collectors.toSet())
                : Collections.emptySet();
    }

    @Override
    public void removeObjectFromSet(String key, Object value) {
        redisTemplate.opsForSet().remove(getTenantWithKey(key), value);
    }

    public Set<String> getKeysByPattern(String pattern) {
        return redisTemplate.keys(getTenantWithKey(pattern));
    }
}
