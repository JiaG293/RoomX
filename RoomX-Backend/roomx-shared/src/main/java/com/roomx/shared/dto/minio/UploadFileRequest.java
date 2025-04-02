package com.roomx.shared.dto.minio;

import org.springframework.web.multipart.MultipartFile;


public record UploadFileRequest(
        MultipartFile file,
        String type
//        Boolean makePrivate,
//        Integer duration,
//        TimeUnit timeUnit
) {
}
