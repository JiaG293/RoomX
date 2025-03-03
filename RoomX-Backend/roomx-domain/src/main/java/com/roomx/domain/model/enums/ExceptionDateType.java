package com.roomx.domain.model.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ExceptionDateType {
    HOLIDAY(""),
    GLOBAL_HOLIDAY(""),
    SATURDAY(""),
    SUNDAY(""),
    BRANCH("");
    private static final Map<String, ExceptionDateType> DISPLAY_NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(ExceptionDateType::getDisplayName, e -> e));

    private final String displayName;

    ExceptionDateType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ExceptionDateType fromDisplayName(String displayName) {
        return DISPLAY_NAME_MAP.getOrDefault(displayName, null);
    }
}
