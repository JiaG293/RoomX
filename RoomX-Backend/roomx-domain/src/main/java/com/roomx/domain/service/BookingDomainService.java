package com.roomx.domain.service;

import com.roomx.domain.event.BookingEvent;
import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.repository.BookingRepository;
import com.roomx.shared.enums.BookingStatusType;
import com.roomx.shared.exception.exception.AppException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class BookingDomainService {
    private final BookingRepository bookingRepository;

    public BookingDomainService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(
            String roomId,
            LocalDate startDate,
            LocalTime startTime,
            LocalTime endTime) {
        var listBooking = bookingRepository.findAllByStatus(BookingStatusType.SCHEDULED.toString());


        return null;
    }

    public Map<UUID, Boolean> checkRoomAvailability(
            List<UUID> roomIds,
            LocalDate date,
            LocalTime start,
            LocalTime end,
            Map<UUID, List<Booking>> bookingsMap) {
        Map<UUID, Boolean> result = new HashMap<>();

        for (UUID roomId : roomIds) {
            List<Booking> bookings = bookingsMap.getOrDefault(roomId, Collections.emptyList());
            boolean available = isRoomAvailable(roomId, date, start, end, bookings);
            result.put(roomId, available);
        }

        return result; // Trả về danh sách phòng có thể đặt (true) hoặc bị xung đột (false)
    }

    public boolean isRoomAvailable(UUID roomId, LocalDate date, LocalTime start, LocalTime end, List<Booking> bookings) {
        List<BookingEvent> events = new ArrayList<>();

        // Thêm sự kiện từ các booking đã có
        for (Booking booking : bookings) {
            if (booking.getRoom().getId().equals(roomId) && booking.getMeetingDate().equals(date)) {
                events.add(new BookingEvent(booking.getMeetingStart(), +1)); // Cuộc họp bắt đầu
                events.add(new BookingEvent(booking.getMeetingEnd(), -1));   // Cuộc họp kết thúc
            }
        }

        // Thêm sự kiện từ khoảng thời gian cần kiểm tra
        events.add(new BookingEvent(start, +1));
        events.add(new BookingEvent(end, -1));

        // Sắp xếp sự kiện theo thời gian tăng dần
        events.sort(Comparator.comparing(BookingEvent::getTime)
                .thenComparing(BookingEvent::getType)); // Sắp xếp sự kiện kết thúc trước nếu cùng thời gian

        int activeMeetings = 0;
        for (BookingEvent event : events) {
            activeMeetings += event.getType();
            if (activeMeetings > 1) { // Nếu có từ 2 cuộc họp trở lên cùng lúc thì bị xung đột
                return false;
            }
        }
        return true;
    }

}
