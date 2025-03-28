package com.roomx.application.service.booking;

import com.roomx.domain.event.Event;
import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.aggrerate.Room;
import com.roomx.domain.model.entity.BookingParticipant;
import com.roomx.domain.model.vo.BookingParticipantId;
import com.roomx.domain.repository.*;
import com.roomx.shared.dto.booking.base.RoomScheduleResultDto;
import com.roomx.shared.enums.BookingStatusType;
import com.roomx.shared.enums.RoomStatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


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
                    if (userFind != null) {
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


    /*public List<RoomScheduleResultDto> checkScheduleAndFindOptimalRoom(
            List<LocalDate> occurrences,
            LocalTime timeStart,
            LocalTime timeEnd,
            Integer capacity,
            List<String> participants
    ) {
        List<RoomScheduleResultDto> results = new ArrayList<>();
        int requiredCapacity = (capacity != null && capacity > 0) ? capacity : participants.size();

        for (LocalDate date : occurrences) {
            List<Booking> existingBookings = bookingRepository.findAllByMeetingDateAndContainsStatus(date, BookingStatusType.getListAccept());
            String conflictRoomId = null;

            boolean hasConflict = false;
            for (Booking booking : existingBookings) {
                if (timeOverlap(booking.getMeetingStart(), booking.getMeetingEnd(), timeStart, timeEnd)) {
                    var bookingParticipants = bookingParticipantRepository
                            .findAllBookingId(booking.getId().toString());
                    boolean participantConflict = bookingParticipants.stream()
                            .anyMatch(p -> participants.contains(p.getUser().getEmail()));

                    if (participantConflict) {
                        hasConflict = true;
                        conflictRoomId = booking.getRoom().getId().toString(); // Lưu lại room gây xung đột
                        break;
                    }
                }
            }

            // Tìm phòng tối ưu (đủ sức chứa nhỏ nhất)
            Optional<Room> optimalRoom = roomRepository.findAllByStatus(RoomStatusType.AVAILABLE.toString()).stream()
                    .filter(room -> room.getRoomClass().getCapacity() >= requiredCapacity)
                    .min(Comparator.comparingInt(room -> room.getRoomClass().getCapacity()));

            // Tìm phòng thay thế (lớn hơn nhưng không phải tốt nhất)
            Optional<Room> alternativeRoom = roomRepository.findAllByStatus(RoomStatusType.AVAILABLE.toString()).stream()
                    .filter(room -> room.getRoomClass().getCapacity() >= requiredCapacity &&
                            (!optimalRoom.isPresent() || room.getRoomClass().getCapacity() > optimalRoom.get().getRoomClass().getCapacity()))
                    .min(Comparator.comparingInt(room -> room.getRoomClass().getCapacity()));

            // Nếu có conflict, trả về cả phòng cũ và phòng thay thế
            String alternativeRoomId = hasConflict ? alternativeRoom.map(Room::getId).map(Object::toString).orElse(null) : null;

            results.add(new RoomScheduleResultDto(date, hasConflict, optimalRoom.map(Room::getId).map(Object::toString).orElse(null), conflictRoomId, alternativeRoomId));
        }
        return results;
    }*/

    /*public List<RoomScheduleResultDto> checkScheduleAndFindOptimalRoom(
            List<LocalDate> occurrences, LocalTime timeStart, LocalTime timeEnd,
            Integer capacity, List<String> participants) {

        List<RoomScheduleResultDto> results = new ArrayList<>();
        // Nếu không cung cấp sức chứa sẽ lấy theo số lượng thành viên tham gia
        int requiredCapacity = (capacity != null && capacity > 0) ? capacity : participants.size() + 1;

        // Lấy ra ngày theo chu kỳ do người dùng lựa chọn
        for (LocalDate date : occurrences) {
            List<Booking> existingBookings = bookingRepository.findAllByMeetingDateAndContainsStatus(date, BookingStatusType.getListAccept());
            List<Room> availableRooms = roomRepository.findAllByStatus(RoomStatusType.AVAILABLE.toString());

            List<Event> bookingEvents = new ArrayList<>();

            // Tạo ra line các cuộc họp đã lên lịch
            for (Booking booking : existingBookings) {
                if (booking.getRoom() == null || booking.getRoom().getRoomClass() == null) {
                    continue; // Bỏ qua booking không có phòng hợp lệ
                }
                bookingEvents.add(new Event(booking.getMeetingStart(), booking.getRoom().getRoomClass().getCapacity(), true)); // Giờ họp bắt đầu - sức chứa
                bookingEvents.add(new Event(booking.getMeetingEnd(), booking.getRoom().getRoomClass().getCapacity(), false)); // Giờ họp kết thúc - sức chứa
            }

            // Thêm sự kiện cho cuộc họp cần đặt
            bookingEvents.add(new Event(timeStart, requiredCapacity, true));
            bookingEvents.add(new Event(timeEnd, requiredCapacity, false));

            // Sắp xếp theo thời gian, nếu trùng ưu tiên sự kiện kết thúc trước
            bookingEvents.sort((event1, event2) ->
                    event1.getTime().equals(event2.getTime()) ?
                            Boolean.compare(event1.isStart(), event2.isStart()) :
                            event1.getTime().compareTo(event2.getTime())
            );

            int currentCapacity = 0;
            boolean hasConflict = false;

            for (Event event : bookingEvents) {
                if (event.isStart()) {
                    currentCapacity += event.getCapacity();
                    if (currentCapacity > availableRooms.stream().mapToInt(r -> r.getRoomClass().getCapacity()).max().orElse(0)) {
                        hasConflict = true;
                        break;
                    }
                } else {
                    currentCapacity -= event.getCapacity();
                }
            }

            // Tìm phòng tốt nhất có thể sử dụng
            Optional<Room> optimalRoom = availableRooms.stream()
                    .filter(room -> room.getRoomClass().getCapacity() >= requiredCapacity)
                    .min(Comparator.comparingInt(room -> room.getRoomClass().getCapacity()));

            // Nếu có xung đột, tìm phòng thay thế
            String alternativeRoomId = null;
            if (hasConflict) {
                alternativeRoomId = availableRooms.stream()
                        .filter(room -> room.getRoomClass().getCapacity() >= requiredCapacity &&
                                (!optimalRoom.isPresent() || room.getRoomClass().getCapacity() > optimalRoom.get().getRoomClass().getCapacity()))
                        .min(Comparator.comparingInt(room -> room.getRoomClass().getCapacity()))
                        .map(Room::getId)
                        .map(Object::toString)
                        .orElse(null);
            }

            results.add(new RoomScheduleResultDto(date, hasConflict, optimalRoom.map(Room::getId).map(Object::toString).orElse(null), alternativeRoomId));
        }
        return results;
    }*/

   /* public List<RoomScheduleResultDto> checkScheduleAndFindOptimalRoom(
            String branchId,
            List<LocalDate> occurrences, LocalTime timeStart, LocalTime timeEnd,
            Integer capacity, List<String> participants) {

        List<RoomScheduleResultDto> results = new ArrayList<>();
        int requiredCapacity = (capacity != null && capacity > 0) ? capacity : participants.size() + 1;

        for (LocalDate date : occurrences) {
            List<Booking> existingBookings = bookingRepository.findAllByMeetingDateAndContainsStatus(
                    date, BookingStatusType.getListAccept());

            List<Room> availableRooms = (branchId != null)
                    ? roomRepository.findAllByBranchIdAndStatus(branchId, RoomStatusType.AVAILABLE.toString())
                    : roomRepository.findAllByStatus(RoomStatusType.AVAILABLE.toString());

            List<Event> events = new ArrayList<>();

            for (Booking booking : existingBookings) {
                if (booking.getRoom() != null && booking.getRoom().getRoomClass() != null) {
                    int roomCapacity = booking.getRoom().getRoomClass().getCapacity();
                    events.add(new Event(booking.getMeetingStart(), roomCapacity, true));
                    events.add(new Event(booking.getMeetingEnd(), roomCapacity, false));
                }
            }

            events.add(new Event(timeStart, requiredCapacity, true));
            events.add(new Event(timeEnd, requiredCapacity, false));

            events.sort((e1, e2) -> e1.getTime().equals(e2.getTime())
                    ? Boolean.compare(e1.isStart(), e2.isStart())
                    : e1.getTime().compareTo(e2.getTime()));

            int currentCapacity = 0;
            int maxCapacityNeeded = 0;
            for (Event event : events) {
                currentCapacity += event.isStart() ? event.getCapacity() : -event.getCapacity();
                maxCapacityNeeded = Math.max(maxCapacityNeeded, currentCapacity);
            }

            int finalMaxCapacityNeeded = maxCapacityNeeded;
            Optional<Room> optimalRoom = availableRooms.stream()
                    .filter(room -> room.getRoomClass().getCapacity() >= finalMaxCapacityNeeded)
                    .min(Comparator.comparingInt(room -> room.getRoomClass().getCapacity()));

            String alternativeRoomId = availableRooms.stream()
                    .filter(room -> room.getRoomClass().getCapacity() >= finalMaxCapacityNeeded &&
                            (optimalRoom.isEmpty() || room.getRoomClass().getCapacity() > optimalRoom.get().getRoomClass().getCapacity()))
                    .min(Comparator.comparingInt(room -> room.getRoomClass().getCapacity()))
                    .map(Room::getId)
                    .map(Object::toString)
                    .orElse(null);

            results.add(new RoomScheduleResultDto(
                    date, maxCapacityNeeded > availableRooms.stream().mapToInt(r -> r.getRoomClass().getCapacity()).max().orElse(0),
                    optimalRoom.map(Room::getId).map(Object::toString).orElse(null), alternativeRoomId));
        }
        return results;
    }*/

    public List<RoomScheduleResultDto> checkScheduleAndFindOptimalRoom1(
            String branchId,
            List<LocalDate> occurrences, LocalTime timeStart, LocalTime timeEnd,
            Integer capacity, List<String> participants) {

        List<RoomScheduleResultDto> results = new ArrayList<>();
        int requiredCapacity = (capacity != null && capacity > 0) ? capacity : participants.size() + 1;

        for (LocalDate date : occurrences) {
            // Lấy tất cả các booking trong ngày mà có trạng thái hợp lệ (không lọc theo branchId)
            List<Booking> existingBookings = bookingRepository.findAllByMeetingDateAndContainsStatus(
                    date, BookingStatusType.getListAccept());

            // Lọc phòng theo branchId nếu có, nếu không thì lấy tất cả các phòng khả dụng
            List<Room> availableRooms = (branchId != null)
                    ? roomRepository.findAllByBranchIdAndStatus(branchId, RoomStatusType.AVAILABLE.toString())
                    : roomRepository.findAllByStatus(RoomStatusType.AVAILABLE.toString());

            List<Event> events = new ArrayList<>();

            for (Booking booking : existingBookings) {
                if (booking.getRoom() != null && booking.getRoom().getRoomClass() != null) {
                    int roomCapacity = booking.getRoom().getRoomClass().getCapacity();
                    events.add(new Event(booking.getMeetingStart(), roomCapacity, true));
                    events.add(new Event(booking.getMeetingEnd(), roomCapacity, false));
                }
            }

            events.add(new Event(timeStart, requiredCapacity, true));
            events.add(new Event(timeEnd, requiredCapacity, false));

            // Sắp xếp các sự kiện theo thời gian (ưu tiên sự kiện kết thúc trước nếu trùng thời gian)
            events.sort((e1, e2) -> e1.getTime().equals(e2.getTime())
                    ? Boolean.compare(e1.isStart(), e2.isStart())
                    : e1.getTime().compareTo(e2.getTime()));

            int currentCapacity = 0;
            int maxCapacityNeeded = 0;
            for (Event event : events) {
                currentCapacity += event.isStart() ? event.getCapacity() : -event.getCapacity();
                maxCapacityNeeded = Math.max(maxCapacityNeeded, currentCapacity);
            }

            // Tìm phòng tối ưu có sức chứa nhỏ nhất nhưng vẫn đủ chỗ
            int finalMaxCapacityNeeded = maxCapacityNeeded;
            Room optimalRoom = availableRooms.stream()
                    .filter(room -> room.getRoomClass().getCapacity() >= finalMaxCapacityNeeded)
                    .sorted(Comparator.comparingInt(room -> room.getRoomClass().getCapacity())) // Sắp xếp tăng dần theo sức chứa
                    .findFirst()
                    .orElse(null);

            // Lấy danh sách các phòng thay thế có sức chứa lớn hơn phòng tối ưu
            int finalMaxCapacityNeeded1 = maxCapacityNeeded;
            List<String> alternativeRooms = availableRooms.stream()
                    .filter(room -> room.getRoomClass().getCapacity() >= finalMaxCapacityNeeded1 &&
                            (optimalRoom == null || room.getRoomClass().getCapacity() > optimalRoom.getRoomClass().getCapacity()))
                    .sorted(Comparator.comparingInt(room -> room.getRoomClass().getCapacity()))
                    .map(room -> room.getId().toString())
                    .collect(Collectors.toList());

            results.add(new RoomScheduleResultDto(
                    date, maxCapacityNeeded > availableRooms.stream().mapToInt(r -> r.getRoomClass().getCapacity()).max().orElse(0),
                    (optimalRoom != null) ? optimalRoom.getId().toString() : null,
                    alternativeRooms));
        }
        return results;
    }





}
