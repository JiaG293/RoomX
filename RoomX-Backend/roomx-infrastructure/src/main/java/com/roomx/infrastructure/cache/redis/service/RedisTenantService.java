package com.roomx.infrastructure.cache.redis.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface RedisTenantService {
    void setString(String key, String value);
    String getString(String key);

    void setObject(String key, Object value);
    <T> T getObject(String key, Class<T> targetClass);

    void put(String key, Object value, long timeout, TimeUnit unit);
    void put(String key, Object value, long expireTime);

    void delete(String key);

    void rightPushObjectToList(String key, Object value);
    <T> List<T> rangeObjectFromList(String key, long start, long end, Class<T> clazz);
}
