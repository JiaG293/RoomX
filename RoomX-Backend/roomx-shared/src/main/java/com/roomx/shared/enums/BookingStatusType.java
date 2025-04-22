package com.roomx.shared.enums;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BookingStatusType {
    SCHEDULED("Đã lên lịch"),
    IN_PROGRESS ("Đang diễn ra"),
    COMPLETED ("Hoàn thành"),
    CANCELLED("Hủy"),
    MOVED("Di chuyển phòng khác");
    private static final Map<String, BookingStatusType> DISPLAY_NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(BookingStatusType::getDisplayName, e -> e));

    private final String displayName;

    BookingStatusType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static BookingStatusType fromDisplayName(String displayName) {
        return DISPLAY_NAME_MAP.getOrDefault(displayName, null);
    }

    public static List<String> getListAccept(){
        return List.of(SCHEDULED.toString(), IN_PROGRESS.toString());
    }

    public static List<String> getList(){
        return List.of(SCHEDULED.toString(), IN_PROGRESS.toString(), COMPLETED.toString(), MOVED.toString(), CANCELLED.toString());
    }
}
