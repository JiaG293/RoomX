package com.roomx.domain.event;

import java.time.LocalTime;

public class Event {
    private LocalTime time;
    private int type; // +1 for start, -1 for end

    public Event(LocalTime time, int type) {
        this.time = time;
        this.type = type;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getType() {
        return type;
    }
}
