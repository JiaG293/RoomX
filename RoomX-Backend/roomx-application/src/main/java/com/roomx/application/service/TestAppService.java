package com.roomx.application.service;

import com.roomx.infrastructure.cache.redis.RedisTenantService;
import com.roomx.infrastructure.minio.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestAppService {
    private final RedisTenantService redisTenantService;
    private final MinioService minioService;


    public Object testAppService() {

        var map = new HashMap<String, List<String>>();
        map.put("1", List.of("value1", "value2"));
        map.put("2", List.of("value1", "value2"));

        var key = "test" + UUID.randomUUID().toString();
        redisTenantService.put(key, map, 30, TimeUnit.SECONDS);

        var result = redisTenantService.getObject(key, HashMap.class);
        return result;
    }

    public Object testMinio(List<MultipartFile> request, boolean makePrivate, String path) {
        var result = new ArrayList<String>();

        request.forEach(file -> {
            result.add(minioService.uploadFile(file, path, makePrivate,7, TimeUnit.DAYS));
        });


        return result;
    }
}
