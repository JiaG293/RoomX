package com.roomx.infrastructure.minio;

import lombok.Getter;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

@Getter
public enum MinioPathStorageType {
    ROOM("/room"),
    USER("/user"),
    EQUIPMENT("/equipment"),
    SERVICE("/service"),
    USERINFO("/user");

    private String path;

    MinioPathStorageType(String path) {
        this.path = path;
    }


}
