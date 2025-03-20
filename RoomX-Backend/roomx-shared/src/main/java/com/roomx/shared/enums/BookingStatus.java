package com.roomx.shared.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BookingStatus {
    SCHEDULED("Đã lên lịch"),
    IN_PROGRESS ("Đang diễn ra"),
    COMPLETED ("Hoàn thành"),
    CANCELLED("Hủy"),
    MOVED("Di chuyển phòng khác");
    private static final Map<String, BookingStatus> DISPLAY_NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(BookingStatus::getDisplayName, e -> e));

    private final String displayName;

    BookingStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static BookingStatus fromDisplayName(String displayName) {
        return DISPLAY_NAME_MAP.getOrDefault(displayName, null);
    }
}
