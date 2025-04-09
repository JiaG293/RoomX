package com.roomx.shared.dto.booking.base;

import java.time.LocalTime;

public class BookingEvent implements Comparable<BookingEvent> {
    private LocalTime time;
    private int type; // +1: Start, -1: End

    public BookingEvent(LocalTime time, int type) {
        this.time = time;
        this.type = type;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getType() {
        return type;
    }

    @Override
    public int compareTo(BookingEvent other) {
        if (!this.time.equals(other.time)) {
            return this.time.compareTo(other.time);
        }
        return Integer.compare(this.type, other.type); // Ưu tiên sự kiện kết thúc trước khi bắt đầu
    }
}

