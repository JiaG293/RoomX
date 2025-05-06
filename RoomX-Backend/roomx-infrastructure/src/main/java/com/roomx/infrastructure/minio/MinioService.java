package com.roomx.infrastructure.minio;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface MinioService {
    String uploadFile(MultipartFile file, String path, Boolean makePrivate, Integer duration, TimeUnit timeType, Map<String, String> metadata);
    String getFileUrl(String filePath);
    Optional<String> findFileByName(String fileName);
    InputStream downloadFile(String filePath);
    void deleteFile(String filePath);
    boolean fileExists(String filePath);
    String generateSignedUrl(String filePath, Integer duration, TimeUnit timeType);

    List<String> listFiles();

    List<String> uploadFiles(List<MultipartFile> files, String path, Boolean makePrivate, Integer duration, TimeUnit timeTye, Map<String, String> metadata);

}
