package com.roomx.shared.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum GroupType {
    DEPARTMENT("Phòng ban"),
    PARTNER("Đối tác"),
    SELF("Cá nhân");

    private static final Map<String, GroupType> DISPLAY_NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(GroupType::getDisplayName, e -> e));

    private final String displayName; 

    GroupType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static GroupType fromDisplayName(String displayName) {
        return DISPLAY_NAME_MAP.getOrDefault(displayName, null);
    }
}
