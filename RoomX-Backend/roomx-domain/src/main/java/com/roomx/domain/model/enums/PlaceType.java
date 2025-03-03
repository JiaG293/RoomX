package com.roomx.domain.model.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum PlaceType {
    BRACNH(""),
    DEPARTMENT(""),
    ROOM("");
    private static final Map<String, PlaceType> DISPLAY_NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(PlaceType::getDisplayName, e -> e));

    private final String displayName;

    PlaceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static PlaceType fromDisplayName(String displayName) {
        return DISPLAY_NAME_MAP.getOrDefault(displayName, null);
    }
}
