package com.roomx.domain.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum RoleType {
    USER(1, "Người dùng"),
    SUPPORTER(2, "Hỗ trợ kĩ thuật"),
    APPROVER(2, "Kiểm duyệt"),
    ADMIN(3, "Quản trị viên"),
    OWNER(4, "Chủ sở hữu");

    private static final Map<String, RoleType> DISPLAY_NAME_MAP = new HashMap<>();
    private static final Map<String, RoleType> ROLE_NAME_MAP = new HashMap<>();

    static {
        for (RoleType role : values()) {
            DISPLAY_NAME_MAP.put(role.displayName, role);
            ROLE_NAME_MAP.put(role.name(), role);
        }
    }

    private final int level;
    private final String displayName;

    RoleType(int level, String displayName) {
        this.level = level;
        this.displayName = displayName;
    }

    public int getLevel() {
        return level;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static RoleType fromDisplayName(String displayName) {
        return DISPLAY_NAME_MAP.get(displayName);
    }

    public static RoleType fromString(String roleName) {
        return ROLE_NAME_MAP.get(roleName.toUpperCase());
    }

    public static boolean isHigherThan(String roleRequest, String targetRole) {
        RoleType requestType = RoleType.valueOf(roleRequest);
        RoleType targetType = RoleType.valueOf(targetRole);
        return requestType.level > targetType.level;
    }
}
