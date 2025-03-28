package com.roomx.domain.event;

import java.time.LocalTime;

public class Event {
    LocalTime time;
    int capacity;
    boolean isStart;

    public Event(LocalTime time, int capacity, boolean isStart) {
        this.time = time;
        this.capacity = capacity;
        this.isStart = isStart;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isStart() {
        return isStart;
    }
}
