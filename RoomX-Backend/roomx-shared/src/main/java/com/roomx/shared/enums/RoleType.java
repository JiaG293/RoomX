package com.roomx.shared.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum RoleType {
    USER("Người dùng"),
    SUPPORTER("Hỗ trợ kĩ thuật"),
    APPROVER("Kiểm duyệt"),
    ADMIN("Quản trị viên"),
    OWNER("Chủ sở hữu");

    private static final Map<String, RoleType> DISPLAY_NAME_MAP = new HashMap<>();
    private static final Map<String, RoleType> ROLE_NAME_MAP = new HashMap<>();

    static {
        for (RoleType role : values()) {
            DISPLAY_NAME_MAP.put(role.displayName, role);
            ROLE_NAME_MAP.put(role.name(), role);
        }
    }

    private final String displayName;

    RoleType(String displayName) {
        this.displayName = displayName;
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


    public static List<String> listRoleApproval() {
        return Arrays.asList(ADMIN.toString(), APPROVER.toString(), OWNER.toString());
    }


}

