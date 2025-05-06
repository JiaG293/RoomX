package com.roomx.application.service.file;

import com.roomx.infrastructure.minio.MinioService;
import com.roomx.infrastructure.security.oauth.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioAppService {
    private final MinioService minioService;
    private final SecurityUtil securityUtil;

    public Object uploadFiles(List<MultipartFile> request, boolean makePrivate, String path, Map<String, String> metadata) {
        var result = new ArrayList<String>();
        var userId = securityUtil.getCurrentUserId();
        var fileMetadata = new HashMap<String, String>();
        if (metadata != null) fileMetadata.putAll(metadata);

        fileMetadata.put("userId", userId);



        request.forEach(file -> {
            result.add(minioService.uploadFile(file, path, makePrivate, 7, TimeUnit.DAYS, fileMetadata));
        });

        return result;
    }

    public Object uploadFile(MultipartFile file, boolean makePrivate, String path, Map<String, String> metadata) {
        var userId = securityUtil.getCurrentUserId();
        var fileMetadata = new HashMap<String, String>();

        if (metadata != null) fileMetadata.putAll(metadata);
        fileMetadata.put("userId", userId);

        var result = minioService.uploadFile(file, path, makePrivate, 7, TimeUnit.DAYS, fileMetadata);

        return result;
    }
}
