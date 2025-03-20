package com.roomx.shared.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DeleteStatusType {
    INACTIVE("Đã xóa"),
    ACTIVE ("Hoạt động");
    private static final Map<String, DeleteStatusType> DISPLAY_NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(DeleteStatusType::getDisplayName, e -> e));

    private final String displayName;

    DeleteStatusType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static DeleteStatusType fromDisplayName(String displayName) {
        return DISPLAY_NAME_MAP.getOrDefault(displayName, null);
    }

    public static DeleteStatusType getDefault(){
        return ACTIVE;
    }

    public static String getDefaultString(){
        return ACTIVE.toString();
    }
}
