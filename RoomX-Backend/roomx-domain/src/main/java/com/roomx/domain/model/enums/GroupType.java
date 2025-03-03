package com.roomx.domain.model.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum GroupType {
    DEPARTMENT(""),
    COMPANY(""),
    BRANCH(""),
    PARTNER(""),
    SELF(""),
    BOOKING("");

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
