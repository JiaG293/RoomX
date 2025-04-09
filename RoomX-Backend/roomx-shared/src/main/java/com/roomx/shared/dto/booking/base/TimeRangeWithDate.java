package com.roomx.shared.dto.booking.base;

import java.time.LocalDate;
import java.time.LocalTime;

public record TimeRangeWithDate(LocalDate date, LocalTime start, LocalTime end) {}