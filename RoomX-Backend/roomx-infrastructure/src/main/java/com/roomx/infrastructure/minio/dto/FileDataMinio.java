package com.roomx.infrastructure.minio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDataMinio {
    private String id;
    private String filePath;
    private String extension;
    private String url;
    private boolean isPrivate;
}
