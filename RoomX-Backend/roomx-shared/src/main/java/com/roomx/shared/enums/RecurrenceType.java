package com.roomx.shared.enums;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RecurrenceType {
    DAILY("daily") {
        @Override
        public LocalDate nextDate(LocalDate date, Short interval) {
            return date.plusDays(interval == null ? 1 : interval);
        }
    },
    WEEKLY("weekly") {
        @Override
        public LocalDate nextDate(LocalDate date, Short interval) {
            return date.plusWeeks(interval == null ? 1 : interval);
        }
    },
    MONTHLY("monthly") {
        @Override
        public LocalDate nextDate(LocalDate date, Short interval) {
            return date.plusMonths(interval == null ? 1 : interval);
        }
    },
    YEARLY("yearly") {
        @Override
        public LocalDate nextDate(LocalDate date, Short interval) {
            return date.plusYears(interval == null ? 1 : interval);
        }
    },
    CUSTOM("custom") {
        @Override
        public LocalDate nextDate(LocalDate date, Short interval) {
            return date.plusDays(interval == null ? 1 : interval);
        }
    };

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
        return (displayName != null) ? DISPLAY_NAME_MAP.get(displayName.toLowerCase()) : null;
    }

    public abstract LocalDate nextDate(LocalDate date, Short interval);
}

