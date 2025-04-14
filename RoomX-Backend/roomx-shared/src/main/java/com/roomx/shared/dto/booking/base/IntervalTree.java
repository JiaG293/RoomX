package com.roomx.shared.dto.booking.base;


import com.google.common.collect.TreeMultimap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Builder@Data
@AllArgsConstructor
public class IntervalTree {
    private TreeMultimap<LocalTime, LocalTime> intervals;

    public IntervalTree() {
        this.intervals = TreeMultimap.create();
    }

    public void addInterval(IntervalEvent interval) {
        intervals.put(interval.start, interval.end);
    }

    public boolean hasOverlap(LocalTime start, LocalTime end, int bufferTime) {
        LocalTime bufferedStart = start.minusMinutes(bufferTime);
        LocalTime bufferedEnd = end.plusMinutes(bufferTime);
        //Tìm tất cả các interval có điểm bắt đầu <= end
        var startingBeforeEnd = intervals.entries().stream()
                .filter(e -> !e.getKey().isAfter(bufferedEnd))
                .toList();
        //Sau đó trong list vừa có kiếm điểm kết thúc >= start
        return startingBeforeEnd.stream().anyMatch(e -> !e.getValue().isBefore(bufferedStart));
    }

    public List<IntervalEvent> getIntervals(){
        return intervals.entries().stream()
                .map(e -> new IntervalEvent(e.getKey(), e.getValue())).collect(Collectors.toList());
    }
}