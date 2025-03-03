package com.roomx.domain.model.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RecurrenceType {
    DAILY(""),
    WEEKLY(""),
    MONTHLY(""),
    YEARLY(""),
    CUSTOM("");

    private static final Map<String, RecurrenceType> DISPLAY_NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(RecurrenceType::getDisplayName, e -> e));

    private final String displayName;

    RecurrenceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static RecurrenceType fromDisplayName(String displayName) {
        return DISPLAY_NAME_MAP.getOrDefault(displayName, null);
    }
}
