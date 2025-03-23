package com.roomx.shared.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ImageUrlType {
    SERVICE("Dịch vụ"),
    EQUIPMENT("Thiết bị"),
    ROOM("Phòng");
    private static final Map<String, ImageUrlType> DISPLAY_NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(ImageUrlType::getDisplayName, e -> e));

    private final String displayName;

    ImageUrlType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ImageUrlType fromDisplayName(String displayName) {
        return DISPLAY_NAME_MAP.getOrDefault(displayName, null);
    }
}
