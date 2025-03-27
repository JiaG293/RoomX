/*
package com.roomx.domain.service;

import com.roomx.domain.event.Event;
import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.aggrerate.Room;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.model.entity.Recurrence;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class RoomSchedulerService {
    private List<Room> rooms;
    private List<Booking> bookings;
    private boolean checkParticipantConflict;

    public RoomSchedulerService(List<Room> rooms, List<Booking> bookings, boolean checkParticipantConflict) {
        this.rooms = rooms;
        this.bookings = bookings;
        this.checkParticipantConflict = checkParticipantConflict;
    }

    public Optional<Room> findAvailableRoom(int requiredCapacity, LocalDate date, LocalTime start, LocalTime end, List<User> participants) {
        List<Room> sortedRooms = new ArrayList<>(rooms);
        sortedRooms.sort(Comparator.comparingInt(room-> room.getRoomClass().getCapacity()));

        for (Room room : sortedRooms) {
            if (room.getRoomClass().getCapacity() >= requiredCapacity && isRoomAvailable(room, date, start, end, participants)) {
                return Optional.of(room);
            }
        }
        return Optional.empty();
    }

    private boolean isRoomAvailable(Room room, LocalDate date, LocalTime start, LocalTime end, List<User> participants) {
        for (Booking b : bookings) {
            if (b.getRoom().equals(room) && b.getMeetingDate().equals(date) && timeOverlap(b.getMeetingStart(), b.getMeetingEnd(), start, end)) {
                return false;
            }
            if (checkParticipantConflict && hasParticipantConflict(date, start, end, participants)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasParticipantConflict(LocalDate date, LocalTime start, LocalTime end, List<User> participants) {
        for (Booking b : bookings) {
            if (b.getMeetingDate().equals(date) && timeOverlap(b.getMeetingStart(), b.getMeetingEnd(), start, end)) {
                if (participants.stream().anyMatch(b.getParticipants()::contains)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean timeOverlap(LocalTime start, LocalTime end, LocalTime start, LocalTime end) {
        return !(end.isBefore(start) || start.isAfter(end));
    }

    public List<Booking> scheduleRecurringMeeting(BookingRequest request, Recurrence recurrence) {
        List<Booking> createdBookings = new ArrayList<>();
        List<LocalDate> occurrenceDates = recurrence.getOccurrences();

        for (LocalDate date : occurrenceDates) {
            Optional<Room> roomOpt = findAvailableRoom(request.getRoom().getCapacity(), date,
                    request.getMeetingStart(), request.getMeetingEnd(), request.getParticipants());
            if (roomOpt.isPresent()) {
                Room room = roomOpt.get();
                Booking booking = new Booking(UUID.randomUUID(), request, room, request.getMeetingStart(),
                        request.getMeetingEnd(), date, Instant.now());
                bookings.add(booking);
                createdBookings.add(booking);
            } else {
                // Tìm giờ khác nếu trùng lặp
                Optional<LocalTime> alternativeTime = findAlternativeTime(date, request.getMeetingStart(), request.getMeetingEnd());
                if (alternativeTime.isPresent()) {
                    roomOpt = findAvailableRoom(request.getRoom().getCapacity(), date, alternativeTime.get(), alternativeTime.get().plusMinutes(request.getDuration()), request.getParticipants());
                    roomOpt.ifPresent(room -> {
                        Booking newBooking = new Booking(UUID.randomUUID(), request, room, alternativeTime.get(),
                                alternativeTime.get().plusMinutes(request.getDuration()), date, Instant.now());
                        bookings.add(newBooking);
                        createdBookings.add(newBooking);
                    });
                }
            }
        }
        if (createdBookings.isEmpty()) {
            throw new RuntimeException("No available room found for recurring bookings");
        }
        return createdBookings;
    }

    private Optional<LocalTime> findAlternativeTime(LocalDate date, LocalTime start, LocalTime end) {
        for (int offset = 15; offset <= 60; offset += 15) {
            LocalTime newStart = start.plusMinutes(offset);
            LocalTime newEnd = end.plusMinutes(offset);
            boolean available = bookings.stream().noneMatch(b -> b.getMeetingDate().equals(date) && timeOverlap(b.getMeetingStart(), b.getMeetingEnd(), newStart, newEnd));
            if (available) {
                return Optional.of(newStart);
            }
        }
        return Optional.empty();
    }
}
*/
