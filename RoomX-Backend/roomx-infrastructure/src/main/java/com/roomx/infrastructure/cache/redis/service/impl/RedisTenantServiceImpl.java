package com.roomx.infrastructure.cache.redis.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomx.infrastructure.cache.redis.service.RedisTenantService;
import com.roomx.infrastructure.multitenancy.context.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisTenantServiceImpl implements RedisTenantService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private String getTenantWithKey(String key) {
        return TenantContextHolder.getTenantIdentifier() + ":" + key;
    }

    @Override
    public void setString(String key, String value) {
        if (!StringUtils.hasLength(key) || value == null) {
            log.error("setString error: key is null or empty, value is null, key: {}", key);
            return;
        }
        String tenantKey = getTenantWithKey(key);
        redisTemplate.opsForValue().set(tenantKey, value);
    }

    @Override
    public String getString(String key) {
        if (!StringUtils.hasLength(key)) {
            log.error("getString error: key is null or empty");
            return null;
        }
        String tenantKey = getTenantWithKey(key);
        return Optional.ofNullable(redisTemplate.opsForValue().get(tenantKey))
                .map(String::valueOf)
                .orElse(null);
    }

    @Override
    public void setObject(String key, Object value) {
        if (!StringUtils.hasLength(key) || value == null) {
            log.error("setObject error: key is null or empty, value is null, key: {}", key);
            return;
        }
        String tenantKey = getTenantWithKey(key);
        try {
            redisTemplate.opsForValue().set(tenantKey, value);
            log.info("Set object value in Redis for key: {}", tenantKey);
        } catch (Exception e) {
            log.error("setObject error: {}", e.getMessage());
        }
    }

    @Override
    public <T> T getObject(String key, Class<T> targetClass) {
        if (!StringUtils.hasLength(key)) {
            log.error("getObject error: key is null or empty");
            return null;
        }
        String tenantKey = getTenantWithKey(key);
        Object result = redisTemplate.opsForValue().get(tenantKey);
        if (result == null) {
            return null;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if (result instanceof String) {
                return objectMapper.readValue((String) result, targetClass);
            } else if (result instanceof Map<?, ?>) {
                return objectMapper.convertValue(result, targetClass);
            } else {
                return objectMapper.convertValue(result, targetClass);
            }
        } catch (JsonProcessingException e) {
            log.error("getObject error: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void put(String key, Object value, long timeout, TimeUnit unit) {
        if (!StringUtils.hasLength(key) || value == null) {
            log.error("put error: key is null or empty, value is null, key: {}", key);
            return;
        }
        String tenantKey = getTenantWithKey(key);
        redisTemplate.opsForValue().set(tenantKey, value, timeout, unit);
        log.info("Put object in Redis with timeout for key: {}", tenantKey);
    }

    @Override
    public void put(String key, Object value, long expireTime) {
        if (!StringUtils.hasLength(key) || value == null) {
            log.error("put error: key is null or empty, value is null, key: {}", key);
            return;
        }
        String tenantKey = getTenantWithKey(key);
        redisTemplate.opsForValue().set(tenantKey, value, expireTime, TimeUnit.MILLISECONDS);
        log.info("Put object in Redis with expiration for key: {}", tenantKey);
    }

    public void delete(String key) {
        String tenantKey = getTenantWithKey(key);
        redisTemplate.delete(tenantKey);
    }

    @Override
    public void rightPushObjectToList(String key, Object value) {
        try {
            String json = objectMapper.writeValueAsString(value);
            redisTemplate.opsForList().rightPush(key, json);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing object", e);
        }
    }

    @Override
    public <T> List<T> rangeObjectFromList(String key, long start, long end, Class<T> clazz) {
        List<Object> rawList = redisTemplate.opsForList().range(key, start, end);
        if (rawList == null) return Collections.emptyList();

        return rawList.stream().map(obj -> {
            try {
                return objectMapper.readValue(obj.toString(), clazz);
            } catch (Exception e) {
                throw new RuntimeException("Error deserializing object", e);
            }
        }).collect(Collectors.toList());
    }


}
