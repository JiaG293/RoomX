package com.roomx.shared.util;



import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class DateTimeUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Instant parseToInstant(String dateStr, boolean isEndOfDay) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            return Instant.parse(dateStr);
        } catch (DateTimeParseException e) {
            LocalDate localDate = LocalDate.parse(dateStr, DATE_FORMATTER);
            LocalDateTime dateTime = isEndOfDay ? localDate.atTime(23, 59, 59, 999_999_999) : localDate.atStartOfDay();
            return dateTime.toInstant(ZoneOffset.UTC);
        }
    }

}