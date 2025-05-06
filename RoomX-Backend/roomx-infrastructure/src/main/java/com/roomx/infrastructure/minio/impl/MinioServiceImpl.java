package com.roomx.infrastructure.minio.impl;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.roomx.infrastructure.minio.MinioService;
import com.roomx.infrastructure.multitenancy.context.TenantContextHolder;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
    private final MinioClient minioClient;

    @Value("${minio.bucket-policy-custom}")
    private String customBucketPolicy;

    @Value("${minio.host-proxy}")
    private String hostProxy;

    private String getTenantBucketName() {
        return TenantContextHolder.getRequiredTenantIdentifier().toLowerCase();
    }

    private String generateFileName(String extensionFile) {
        UUID uuid = UUID.randomUUID();
        Instant now = Instant.now();
        long epochNanos = now.toEpochMilli() * 1_000_000 + now.getNano();
        return uuid.toString() + "-" + epochNanos + extensionFile;
    }

    private void ensureBucketExists(String bucketName) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                try {

                    minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                    log.info("Bucket '{}' created.", bucketName);


                    String bucketPolicy = String.format(customBucketPolicy, bucketName);
                    minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                            .bucket(bucketName)
                            .config(bucketPolicy)
                            .build());
                    log.error("Bucket set policy custom {}: {}", bucketName, bucketPolicy);

                } catch (ErrorResponseException e) {
                    log.error("Failed to create bucket '{}': {}", bucketName, e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            log.error("Failed to ensure bucket existence for tenant '{}': {}", TenantContextHolder.getRequiredTenantIdentifier(), e.getMessage(), e);
            throw new AppException(ErrorCode.MINIO_FAILED, bucketName);
        }
    }

    @Override
    public String uploadFile(
            MultipartFile file,
            String path,
            Boolean makePrivate,
            Integer duration,
            TimeUnit timeType,
            Map<String, String> metadata
    ) {
        String bucketName = getTenantBucketName();
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }


        String newFileName = generateFileName(fileExtension);

        ensureBucketExists(bucketName);

        String objectPath = (path == null || path.isEmpty())
                ? newFileName
                : path + "/" + newFileName;
        String objectPathPublic = "public/" + objectPath;

        try {
            var putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(makePrivate ? objectPath : objectPathPublic)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType());

            if (metadata != null && !metadata.isEmpty()) {
                putObjectArgs.userMetadata(metadata);
            }

            minioClient.putObject(putObjectArgs.build());

            return makePrivate ? generateSignedUrl(objectPath, duration, timeType)
                    : getFileUrl(objectPathPublic);
        } catch (Exception e) {
            log.error("Failed to upload file '{}' to bucket '{}' at path '{}': {}", file.getOriginalFilename(), bucketName, objectPath, e.getMessage(), e);
            throw new AppException(ErrorCode.MINIO_FAILED, file.getOriginalFilename());
        }
    }

    @Override
    public List<String> uploadFiles(
            List<MultipartFile> files,
            String path,
            Boolean makePrivate,
            Integer duration,
            TimeUnit timeType,
            Map<String, String> metadata
    ) {
        List<String> uploadedFileUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                var fileUrl = uploadFile(file, path, makePrivate, duration, timeType, metadata);

                uploadedFileUrls.add(fileUrl);
            } catch (Exception e) {
                log.error("Failed to upload files '{}' due to: {}", file.getOriginalFilename(), e.getMessage(), e);
            }
        }
        return uploadedFileUrls;
    }

// Giả định hostProxy được inject hoặc cấu hình
// @Value("${minio.public-endpoint}") // Ví dụ cấu hình public endpoint
// private String hostProxy;

    @Override
    public String getFileUrl(String filePath) {

        var bucketName = getTenantBucketName();
        String publicUrl;

        try {
            String baseUrl = hostProxy.endsWith("/") ? hostProxy.substring(0, hostProxy.length() - 1) : hostProxy;
            String cleanedFilePath = filePath.startsWith("/") ? filePath.substring(1) : filePath;

            publicUrl = String.format("%s/%s/%s",
                    baseUrl,
                    bucketName,
                    cleanedFilePath);

            log.debug("Generated public URL for {}: {}", filePath, publicUrl);
            return publicUrl;

        } catch (Exception e) {
            log.error("getFileUrl: Failed to generate public URL for file '{}' in bucket '{}': {}", filePath, bucketName, e.getMessage(), e);
            throw new AppException(ErrorCode.MINIO_FAILED, filePath, "Failed to generate public URL: " + e.getMessage());
        }
    }

    @Override
    public String generateSignedUrl(String filePath, Integer duration, TimeUnit timeType) {
        String bucketName = getTenantBucketName();
        int expireTime = 1;
        TimeUnit timeUnit = TimeUnit.HOURS;

        if (duration != null) {
            expireTime = duration;
        }

        if (timeType != null) {
            timeUnit = timeType;
        }


        log.info("check var {} | {} | {} | {}", filePath, duration, timeType, timeUnit);
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(filePath)
                            .expiry(expireTime, timeUnit)
                            .build()
            );
        } catch (Exception e) {
            log.error("Failed to generate signed URL for file '{}' in bucket '{}': {}", filePath, bucketName, e.getMessage(), e);
            throw new AppException(ErrorCode.MINIO_FAILED, filePath);
        }
    }

    @Override
    public Optional<String> findFileByName(String fileName) {
        String bucketName = getTenantBucketName();

        try {
            ListObjectsArgs listArgs = ListObjectsArgs.builder().bucket(bucketName).build();

            return StreamSupport.stream(minioClient.listObjects(listArgs).spliterator(), false)
                    .map(result -> {
                        try {
                            return result.get().objectName();
                        } catch (Exception e) {
                            log.error("Error getting object name: {}", e.getMessage(), e);
                            return null;
                        }
                    })
                    .filter(objectName -> objectName != null && objectName.equals(fileName)) // Check for null and matching file name
                    .findFirst();

        } catch (Exception e) {
            log.error("Failed to find file by name '{}' in bucket '{}': {}", fileName, bucketName, e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public InputStream downloadFile(String filePath) {
        String bucketName = getTenantBucketName();
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filePath)
                            .build()
            );
        } catch (Exception e) {
            log.error("Failed to download file '{}' from bucket '{}': {}", filePath, bucketName, e.getMessage(), e);
            throw new AppException(ErrorCode.MINIO_FAILED, filePath);
        }
    }

    @Override
    public void deleteFile(String filePath) {
        String bucketName = getTenantBucketName();
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filePath)
                            .build()
            );
            log.info("File '{}' deleted from bucket '{}'", filePath, bucketName);
        } catch (Exception e) {
            log.error("Error deleting file {} from bucket {}: {}", filePath, bucketName, e.getMessage(), e);
        }
    }

    @Override
    public boolean fileExists(String filePath) {
        String bucketName = getTenantBucketName();
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filePath)
                            .build()
            );
            return true;
        } catch (Exception e) {
            log.debug("File '{}' does not exist in bucket '{}'", filePath, bucketName); //Use debug here - it's normal for files to not exist.
            return false;
        }
    }


    @Override
    public List<String> listFiles() {
        String bucketName = getTenantBucketName();
        List<String> files = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
            for (Result<Item> result : results) {
                try {
                    Item item = result.get();
                    files.add(item.objectName());
                    log.debug("Found object: {}", item.objectName());
                } catch (Exception e) {
                    log.error("Error getting object name: {}", e.getMessage(), e);
                }
            }
            return files;
        } catch (Exception e) {
            log.error("Failed to list files in bucket '{}': {}", bucketName, e.getMessage(), e);
            throw e;
        }
    }


}
