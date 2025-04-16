package com.roomx.infrastructure.cache.redis.service;

import java.util.Set;

public interface RedisFcmTokenService {
    void addObjectToSet(String key, Object value);
    <T> Set<T> getObjectSet(String key, Class<T> clazz);
    void removeObjectFromSet(String key, Object value);
}
