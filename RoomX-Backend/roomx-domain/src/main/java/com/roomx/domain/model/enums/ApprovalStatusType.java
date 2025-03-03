package com.roomx.domain.model.enums;


import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ApprovalStatusType {
    PENDING(""),
    APPROVED(""),
    REJECTED(""),
    CANCELLED(""),
    MOVED("");
    private static final Map<String, ApprovalStatusType> DISPLAY_NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(ApprovalStatusType::getDisplayName, e -> e));

    private final String displayName;

    ApprovalStatusType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ApprovalStatusType fromDisplayName(String displayName) {
        return DISPLAY_NAME_MAP.getOrDefault(displayName, null);
    }
}
