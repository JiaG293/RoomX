package com.roomx.shared.enums;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RoomStatusType {
    AVAILABLE("kha dung"),
    RESERVED("đang sử dụng"),
    BLOCKED("bao tri"),
    CLEANED("Ve sinh");

    private static final Map<String, RoomStatusType> DISPLAY_NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(RoomStatusType::getDisplayName, e -> e));

    private final String displayName;

    RoomStatusType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static RoomStatusType fromDisplayName(String displayName) {
        return DISPLAY_NAME_MAP.get(displayName);
    }

    public static List<String> getStatusList(){
        return Stream.of(AVAILABLE, RESERVED, BLOCKED, CLEANED)
                .map(RoomStatusType::toString)
                .toList();
    }
}
