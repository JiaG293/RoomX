package com.roomx.domain.model.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum UserType {

    PARTNER("Đối tác"),
    EMPLOYEE("Nhân viên");

    private static final Map<String, UserType> DISPLAY_NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(UserType::getDisplayName, e -> e));

    private final String displayName;

    UserType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static UserType fromDisplayName(String displayName) {
        return DISPLAY_NAME_MAP.get(displayName);
    }
}
