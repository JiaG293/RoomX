package com.roomx.shared.enums;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ApprovalStatusType {
    PENDING("Đang chờ duyệt"),
    APPROVED("Đã duyệt"),
    REJECT("Từ chối"),
    CANCELLED("Hủy"),
    CONFLICT("Xung đột có thể cập nhật");
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

    public static List<String> getListCanApproval(){
        return List.of(PENDING.toString(), CONFLICT.toString());
    }

    public static List<String> getListCantApproval(){
        return List.of(CANCELLED.toString(), APPROVED.toString());
    }

    public static List<String> getList(){
        return List.of(CANCELLED.toString(), APPROVED.toString(), REJECT.toString(), PENDING.toString(), CONFLICT.toString());
    }
}
