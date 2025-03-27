package com.roomx.application.service.booking;

import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.aggrerate.Room;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.model.entity.BookingParticipant;
import com.roomx.domain.model.vo.BookingParticipantId;
import com.roomx.domain.repository.*;
import com.roomx.shared.enums.RoomStatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class RoomSchedulerAppService {
    private final RoomRepository roomRepository;
    private final RoomClassRepository roomClassRepository;
    private final BookingRepository bookingRepository;
    private final BookingRequestRepository bookingRequestRepository;
    private final UserRepository userRepository;
    private final BookingParticipantRepository bookingParticipantRepository;

    public Optional<Room> findAvailableRoom(
            int requiredCapacity,
            LocalDate date,
            LocalTime timeStart,
            LocalTime timeEnd,
            List<String> participants) {
        List<Room> rooms = roomRepository.findAllByCapacityGreaterThanOrEqualAndStatus(requiredCapacity, RoomStatusType.AVAILABLE.toString());

        log.info("room la: {}", rooms);
       /* rooms.stream().map(room -> {
            roomClassRepository.findById(room.getRoom)
        })*/

        for (Room room : rooms) {
            if (isRoomAvailable(room, date, timeStart, timeEnd, participants)) {
                return Optional.of(room);
            }
        }
        return Optional.empty();
    }

    private boolean isRoomAvailable(Room room, LocalDate date, LocalTime start, LocalTime end, List<String> participants) {
        List<Booking> existingBookings = bookingRepository.findAllByMeetingDateAndRoomId(date, room.getId().toString());

        for (Booking booking : existingBookings) {
            if (timeOverlap(booking.getMeetingStart(), booking.getMeetingEnd(), start, end)) {
                return false;
            }
        }
        return true;
    }

    private boolean timeOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return !(end1.isBefore(start2) || start1.isAfter(end2));
    }

    public List<Booking> scheduleRecurringMeeting(BookingRequest request) {
        List<Booking> createdBookings = new ArrayList<>();
        List<LocalDate> occurrenceDates = request.getOccurrences();

        for (LocalDate date : occurrenceDates) {
            Optional<Room> roomOpt = findAvailableRoom(
                    request.getCapacity(),
                    date, request.getStartTime(),
                    request.getEndTime(),
                    request.getParticipants());
            if (roomOpt.isPresent()) {
                Room room = roomOpt.get();
                var bookingDomain = Booking.builder()
                        .bookingCode("BK_" + date.getDayOfMonth())
                        .bookingRequest(request)
                        .room(room)
                        .meetingStart(request.getStartTime())
                        .meetingEnd(request.getEndTime())
                        .meetingDate(date)
                        .count(request.getCapacity())
                        .build();

                bookingRepository.save(bookingDomain);

                var bookingParticipants = request.getParticipants().stream().map(email -> {
                    var userFind = userRepository.findByEmail(email, true).orElse(null);
                    if(userFind != null){
                        return BookingParticipant.builder()
                                .id(new BookingParticipantId(bookingDomain.getId(), userFind.getId()))
                                .booking(bookingDomain)
                                .user(userFind)
                                .build();
                    } else return null;
                }).toList();
                bookingParticipantRepository.saveAll(bookingParticipants);
                createdBookings.add(bookingDomain);
            }
        }
        return createdBookings;
    }


}
