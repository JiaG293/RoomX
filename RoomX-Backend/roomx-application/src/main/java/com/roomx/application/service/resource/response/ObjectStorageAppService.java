package com.roomx.application.service.resource.response;

import com.roomx.infrastructure.minio.MinioPathStorageType;
import com.roomx.infrastructure.minio.MinioService;
import com.roomx.shared.dto.minio.UploadFileRequest;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ObjectStorageAppService {
    private final MinioService minioService;

    private final MinioClient minioClient;

    public Object uploadFile(UploadFileRequest request) {
        if (request.type().equals("user")) {
//            minioService.uploadFile(request.file(), MinioPathStorageType.SERVICE.getPath(), )

        }
        return null;
    }
}
