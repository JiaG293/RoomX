package com.roomx.application.service.booking;

import com.roomx.shared.dto.booking.base.TimeRange;
import com.roomx.shared.dto.booking.base.TimeRangeWithDate;
import com.roomx.domain.event.Event;
import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.aggrerate.Room;
import com.roomx.domain.model.entity.BookingParticipant;
import com.roomx.domain.model.entity.DateRequestException;
import com.roomx.domain.model.vo.BookingParticipantId;
import com.roomx.domain.repository.*;
import com.roomx.shared.dto.booking.base.RoomScheduleResultDto;
import com.roomx.shared.dto.booking.base.SuggestedTimeSlotDto;
import com.roomx.shared.enums.BookingStatusType;
import com.roomx.shared.enums.RoomStatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.Duration;
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
    private final PlaceRepository placeRepository;

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

    /*public List<RoomScheduleResultDto> checkScheduleAndFindOptimalRoom1(
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
                    date,
                    maxCapacityNeeded > availableRooms.stream().mapToInt(r -> r.getRoomClass().getCapacity()).max().orElse(0),
                    (optimalRoom != null) ? optimalRoom.getId().toString() : null,
                    alternativeRooms));
        }
        return results;
    }*/

    /*public List<RoomScheduleResultDto> checkScheduleAndFindOptimalRoom2(
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

            // Sắp xếp các sự kiện theo thời gian
            events.sort((e1, e2) -> e1.getTime().equals(e2.getTime())
                    ? Boolean.compare(e1.isStart(), e2.isStart())
                    : e1.getTime().compareTo(e2.getTime()));

            int currentCapacity = 0;
            int maxCapacityNeeded = 0;
            for (Event event : events) {
                currentCapacity += event.isStart() ? event.getCapacity() : -event.getCapacity();
                maxCapacityNeeded = Math.max(maxCapacityNeeded, currentCapacity);
            }

            // Tìm phòng tối ưu
            int finalMaxCapacityNeeded = maxCapacityNeeded;
            Room optimalRoom = availableRooms.stream()
                    .filter(room -> room.getRoomClass().getCapacity() >= finalMaxCapacityNeeded)
                    .sorted(Comparator.comparingInt(room -> room.getRoomClass().getCapacity()))
                    .findFirst()
                    .orElse(null);

            boolean hasConflict = (optimalRoom == null);
            List<String> suggestedTimeSlots = new ArrayList<>();

            if (hasConflict) {
                // Tìm khung giờ gợi ý cho các phòng có sức chứa đủ
                for (Room room : availableRooms) {
                    if (room.getRoomClass().getCapacity() >= finalMaxCapacityNeeded) {
                        List<Booking> roomBookings = existingBookings.stream()
                                .filter(b -> b.getRoom().getId().equals(room.getId()))
                                .collect(Collectors.toList());

                        // Tìm các khoảng thời gian trống trong ngày cho phòng này
                        LocalTime previousEnd = LocalTime.MIN;
                        for (Booking booking : roomBookings) {
                            if (previousEnd.isBefore(booking.getMeetingStart())) {
                                if (previousEnd.isBefore(timeStart) && booking.getMeetingStart().isAfter(timeEnd)) {
                                    suggestedTimeSlots.add(previousEnd + " - " + booking.getMeetingStart());
                                }
                            }
                            previousEnd = booking.getMeetingEnd();
                        }

                        // Khung giờ sau cuộc họp cuối cùng trong ngày
                        if (previousEnd.isBefore(LocalTime.MAX)) {
                            suggestedTimeSlots.add(previousEnd + " - " + LocalTime.MAX);
                        }
                    }
                }
            }

            results.add(new RoomScheduleResultDto(
                    date,
                    hasConflict,
                    (optimalRoom != null) ? optimalRoom.getId().toString() : null,
                    suggestedTimeSlots
            ));
        }
        return results;
    }
*/
    /*public List<RoomScheduleResultDto> checkScheduleAndFindOptimalRoom3(
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
            Room optimalRoom = availableRooms.stream()
                    .filter(room -> room.getRoomClass().getCapacity() >= finalMaxCapacityNeeded)
                    .sorted(Comparator.comparingInt(room -> room.getRoomClass().getCapacity()))
                    .findFirst()
                    .orElse(null);

            boolean hasConflict = (optimalRoom == null);
            List<String> suggestedTimeSlots = new ArrayList<>();

            if (hasConflict) {
                for (Room room : availableRooms) {
                    if (room.getRoomClass().getCapacity() >= finalMaxCapacityNeeded) {
                        List<Booking> roomBookings = existingBookings.stream()
                                .filter(b -> b.getRoom().getId().equals(room.getId()))
                                .sorted(Comparator.comparing(Booking::getMeetingStart))
                                .collect(Collectors.toList());

                        LocalTime previousEnd = LocalTime.MIN;

                        for (Booking booking : roomBookings) {
                            if (previousEnd.isBefore(booking.getMeetingStart())) {
                                if (previousEnd.plusMinutes(Duration.between(timeStart, timeEnd).toMinutes())
                                        .isBefore(booking.getMeetingStart())) {

                                    suggestedTimeSlots.add(previousEnd + " - " + previousEnd.plusMinutes(Duration.between(timeStart, timeEnd).toMinutes()));
                                }
                            }
                            previousEnd = booking.getMeetingEnd();
                        }

                        if (previousEnd.plusMinutes(Duration.between(timeStart, timeEnd).toMinutes()).isBefore(LocalTime.MAX)) {
                            suggestedTimeSlots.add(previousEnd + " - " + previousEnd.plusMinutes(Duration.between(timeStart, timeEnd).toMinutes()));
                        }
                    }
                }

                suggestedTimeSlots = suggestedTimeSlots.stream()
                        .sorted(Comparator.comparing(slot -> Math.abs(Duration.between(timeStart, LocalTime.parse(slot.split(" - ")[0])).toMinutes())))
                        .limit(5) // Chỉ trả về 5 gợi ý gần nhất
                        .collect(Collectors.toList());
            }

            results.add(new RoomScheduleResultDto(
                    date,
                    hasConflict,
                    (optimalRoom != null) ? optimalRoom.getId().toString() : null,
                    suggestedTimeSlots
            ));
        }
        return results;
    }*/

    public List<RoomScheduleResultDto> checkScheduleAndFindOptimalRoomSameRoomIdWithBranchOptional(
            String bookingRequestId,
            String branchId,
            List<LocalDate> occurrences, LocalTime timeStart, LocalTime timeEnd,
            Integer capacity, List<String> participants, Integer bufferTime) {

        List<RoomScheduleResultDto> results = new ArrayList<>();
        int requiredCapacity = (capacity != null && capacity > 0) ? capacity : participants.size() + 1;

        for (LocalDate date : occurrences) {
            List<Booking> existingBookings = bookingRepository.findAllByMeetingDateAndContainsStatus(
                    date, BookingStatusType.getListAccept());

            log.info("\n\n\n\n\n\n\n\n\n existingBookings: {} \n\n\n\n\n\n\n", existingBookings);

            List<UUID> bookedRoomIds = existingBookings.stream()
                    .map(booking -> booking.getRoom().getId())
                    .collect(Collectors.toList());

            List<Room> availableRooms = (branchId != null)
                    ? roomRepository.findAllByBranchIdAndStatus(branchId, RoomStatusType.AVAILABLE.toString())
                    : roomRepository.findAllByStatus(RoomStatusType.AVAILABLE.toString());

            // Lọc danh sách phòng, chỉ lấy các phòng chưa được đặt trước đó
            availableRooms = availableRooms.stream()
                    .filter(room -> !bookedRoomIds.contains(room.getId()))
                    .collect(Collectors.toList());

            List<Event> events = new ArrayList<>();

            for (Booking booking : existingBookings) {
                if (booking.getRoom() != null && booking.getRoom().getRoomClass() != null) {
                    int roomCapacity = booking.getRoom().getRoomClass().getCapacity();
                    events.add(new Event(booking.getMeetingStart().minusMinutes(bufferTime), roomCapacity, true));
                    events.add(new Event(booking.getMeetingEnd().plusMinutes(bufferTime), roomCapacity, false));
                }
            }

            events.add(new Event(timeStart.minusMinutes(bufferTime), requiredCapacity, true));
            events.add(new Event(timeEnd.plusMinutes(bufferTime), requiredCapacity, false));

            events.sort((e1, e2) -> e1.getTime().equals(e2.getTime())
                    ? Boolean.compare(e1.isStart(), e2.isStart())
                    : e1.getTime().compareTo(e2.getTime()));

            int currentCapacity = 0;
            int maxCapacityNeeded = 0;
            List<LocalTime> availableTimeSlots = new ArrayList<>();
            LocalTime lastEndTime = LocalTime.MIN;

            for (Event event : events) {
                if (currentCapacity == 0 && lastEndTime.isBefore(event.getTime())) {
                    availableTimeSlots.add(lastEndTime);
                    availableTimeSlots.add(event.getTime());
                }

                currentCapacity += event.isStart() ? event.getCapacity() : -event.getCapacity();
                maxCapacityNeeded = Math.max(maxCapacityNeeded, currentCapacity);

                if (!event.isStart()) {
                    lastEndTime = event.getTime();
                }
            }

            // Thêm khoảng trống cuối ngày nếu còn
            LocalTime adjustedMax = LocalTime.of(23, 59);
            if (lastEndTime.isBefore(adjustedMax)) {
                availableTimeSlots.add(lastEndTime);
                availableTimeSlots.add(adjustedMax);
            }

            int finalMaxCapacityNeeded = maxCapacityNeeded;
            Room optimalRoom = availableRooms.stream()
                    .filter(room -> room.getRoomClass().getCapacity() >= finalMaxCapacityNeeded)
                    .sorted(Comparator.comparingInt(room -> room.getRoomClass().getCapacity()))
                    .findFirst()
                    .orElse(null);

            if (optimalRoom != null) {
                results.add(new RoomScheduleResultDto(
                        date,
                        false,
                        optimalRoom.getId().toString(),
                        null
                ));
                continue;
            }

            for (Room room : availableRooms) {
                List<String> timeMorning = new ArrayList<>();
                List<String> timeAfternoon = new ArrayList<>();

                for (int i = 0; i < availableTimeSlots.size() - 1; i += 2) {
                    LocalTime start = availableTimeSlots.get(i).plusMinutes(bufferTime);
                    LocalTime end = availableTimeSlots.get(i + 1).minusMinutes(bufferTime);

                    if (start.isBefore(end)) {
                        if (end.isBefore(LocalTime.NOON)) { // Khung thời gian trong buổi sáng
                            timeMorning.add(start + " - " + end);
                        } else if (start.isAfter(LocalTime.NOON)) { // Khung thời gian buổi chiều
                            timeAfternoon.add(start + " - " + end);
                        } else { // Giao thoa giữa sáng và chiều
                            if (start.isBefore(LocalTime.NOON)) { // Khung thời gian buổi sáng
                                timeMorning.add(start + " - " + LocalTime.NOON);
                            }
                            if (end.isAfter(LocalTime.NOON)) { // Khung thời gian buổi chiều
                                timeAfternoon.add(LocalTime.NOON + " - " + end);
                            }
                        }
                    }
                }

                if (!timeMorning.isEmpty() || !timeAfternoon.isEmpty()) {
                    SuggestedTimeSlotDto suggestedTimeSlots = new SuggestedTimeSlotDto(timeMorning, timeAfternoon);

                    results.add(new RoomScheduleResultDto(
                            date,
                            true,
                            room.getId().toString(),
                            suggestedTimeSlots
                    ));
                }
            }
        }
        return results;
    }














    /*public List<RoomScheduleResultDto> checkScheduleAndFindOptimalRoomSameRoomIdWithBranchOptionalV2(
            String branchId,
            List<LocalDate> occurrences, List<DateRequestException> dateRequestExceptions,
            LocalTime timeStart, LocalTime timeEnd,
            Integer capacity, List<String> participants, Integer bufferTime) {

        List<RoomScheduleResultDto> results = new ArrayList<>();
        int requiredCapacity = (capacity != null && capacity > 0) ? capacity : participants.size() + 1;

        for (LocalDate date : occurrences) {
            List<Booking> existingBookings = bookingRepository.findAllByMeetingDateAndContainsStatus(
                    date, BookingStatusType.getListAccept());

            log.info("\n\n\n\n\n\n\n\n\n existingBookings: {} \n\n\n\n\n\n\n", existingBookings);

            List<UUID> bookedRoomIds = existingBookings.stream()
                    .map(booking -> booking.getRoom().getId())
                    .collect(Collectors.toList());

            List<Room> availableRooms = (branchId != null)
                    ? roomRepository.findAllByBranchIdAndStatus(branchId, RoomStatusType.AVAILABLE.toString())
                    : roomRepository.findAllByStatus(RoomStatusType.AVAILABLE.toString());

            // Lọc danh sách phòng, chỉ lấy các phòng chưa được đặt trước đó
            availableRooms = availableRooms.stream()
                    .filter(room -> !bookedRoomIds.contains(room.getId()))
                    .collect(Collectors.toList());

            List<Event> events = new ArrayList<>();

            for (Booking booking : existingBookings) {
                if (booking.getRoom() != null && booking.getRoom().getRoomClass() != null) {
                    int roomCapacity = booking.getRoom().getRoomClass().getCapacity();
                    events.add(new Event(booking.getMeetingStart().minusMinutes(bufferTime), roomCapacity, true));
                    events.add(new Event(booking.getMeetingEnd().plusMinutes(bufferTime), roomCapacity, false));
                }
            }

            events.add(new Event(timeStart.minusMinutes(bufferTime), requiredCapacity, true));
            events.add(new Event(timeEnd.plusMinutes(bufferTime), requiredCapacity, false));

            events.sort((e1, e2) -> e1.getTime().equals(e2.getTime())
                    ? Boolean.compare(e1.isStart(), e2.isStart())
                    : e1.getTime().compareTo(e2.getTime()));

            int currentCapacity = 0;
            int maxCapacityNeeded = 0;
            List<LocalTime> availableTimeSlots = new ArrayList<>();
            LocalTime lastEndTime = LocalTime.MIN;

            for (Event event : events) {
                if (currentCapacity == 0 && lastEndTime.isBefore(event.getTime())) {
                    availableTimeSlots.add(lastEndTime);
                    availableTimeSlots.add(event.getTime());
                }

                currentCapacity += event.isStart() ? event.getCapacity() : -event.getCapacity();
                maxCapacityNeeded = Math.max(maxCapacityNeeded, currentCapacity);

                if (!event.isStart()) {
                    lastEndTime = event.getTime();
                }
            }

            // Thêm khoảng trống cuối ngày nếu còn
            LocalTime adjustedMax = LocalTime.of(23, 59);
            if (lastEndTime.isBefore(adjustedMax)) {
                availableTimeSlots.add(lastEndTime);
                availableTimeSlots.add(adjustedMax);
            }

            int finalMaxCapacityNeeded = maxCapacityNeeded;
            Room optimalRoom = availableRooms.stream()
                    .filter(room -> room.getRoomClass().getCapacity() >= finalMaxCapacityNeeded)
                    .sorted(Comparator.comparingInt(room -> room.getRoomClass().getCapacity()))
                    .findFirst()
                    .orElse(null);

            if (optimalRoom != null) {
                results.add(new RoomScheduleResultDto(
                        date,
                        false,
                        optimalRoom.getId().toString(),
                        null
                ));
                continue;
            }

            for (Room room : availableRooms) {
                List<String> timeMorning = new ArrayList<>();
                List<String> timeAfternoon = new ArrayList<>();

                for (int i = 0; i < availableTimeSlots.size() - 1; i += 2) {
                    LocalTime start = availableTimeSlots.get(i).plusMinutes(bufferTime);
                    LocalTime end = availableTimeSlots.get(i + 1).minusMinutes(bufferTime);

                    if (start.isBefore(end)) {
                        if (end.isBefore(LocalTime.NOON)) { // Khung thời gian trong buổi sáng
                            timeMorning.add(start + " - " + end);
                        } else if (start.isAfter(LocalTime.NOON)) { // Khung thời gian buổi chiều
                            timeAfternoon.add(start + " - " + end);
                        } else { // Giao thoa giữa sáng và chiều
                            if (start.isBefore(LocalTime.NOON)) { // Khung thời gian buổi sáng
                                timeMorning.add(start + " - " + LocalTime.NOON);
                            }
                            if (end.isAfter(LocalTime.NOON)) { // Khung thời gian buổi chiều
                                timeAfternoon.add(LocalTime.NOON + " - " + end);
                            }
                        }
                    }
                }

                if (!timeMorning.isEmpty() || !timeAfternoon.isEmpty()) {
                    SuggestedTimeSlotDto suggestedTimeSlots = new SuggestedTimeSlotDto(timeMorning, timeAfternoon);

                    results.add(new RoomScheduleResultDto(
                            date,
                            true,
                            room.getId().toString(),
                            suggestedTimeSlots
                    ));
                }
            }
        }
        return results;
    }*/


    public List<RoomScheduleResultDto> checkScheduleAndFindOptimalRoomSameRoomIdWithBranchOptionalV2(
            String branchId,
            List<LocalDate> occurrences,
            List<DateRequestException> dateRequestExceptions,
            LocalTime timeStart, LocalTime timeEnd,
            Integer capacity,
            List<String> participants,
            Integer bufferTime) {

        // Tính ra participants size nếu người dùng không yêu cầu sức chứa
        List<RoomScheduleResultDto> results = new ArrayList<>();
        int requiredCapacity = (capacity != null && capacity > 0) ? capacity : participants.size() + 1;

        Map<LocalDate, DateRequestException> exceptionMap = dateRequestExceptions.stream()
                .collect(Collectors.toMap(DateRequestException::getDate, e -> e));

        // Lấy ra các phòng khả dụng tại thời điểm hiện tại
        List<Room> availableRooms = (branchId != null)
                ? roomRepository.findAllByBranchIdAndStatus(branchId, RoomStatusType.AVAILABLE.toString())
                : roomRepository.findAllByStatus(RoomStatusType.AVAILABLE.toString());

        // Duyệt qua tất cả các ngày để áp dụng sweepline
        for (LocalDate date : occurrences) {
            // Lấy tất cả các phòng hiện đang được họp hoặc đã lên lịch
            List<Booking> bookings = bookingRepository.findAllByMeetingDateAndContainsStatus(
                    date, BookingStatusType.getListAccept());

            Map<UUID, List<Booking>> bookingsByRoom = bookings.stream()
                    .filter(b -> b.getRoom() != null)
                    .collect(Collectors.groupingBy(b -> b.getRoom().getId()));

            LocalTime originalStart = timeStart;
            LocalTime originalEnd = timeEnd;

            Room optimalRoom = availableRooms.stream()
                    .sorted(Comparator.comparingInt(r -> r.getRoomClass().getCapacity()))
                    .filter(room -> {
                        if (room.getRoomClass().getCapacity() < requiredCapacity) return false;
                        List<Booking> roomBookings = bookingsByRoom.getOrDefault(room.getId(), Collections.emptyList());
                        return !hasConflict(roomBookings, originalStart, originalEnd, bufferTime);
                    })
                    .findFirst()
                    .orElse(null);

            if (optimalRoom != null) {
                results.add(new RoomScheduleResultDto(date, false, optimalRoom.getId().toString(), null));
                continue;
            }

            // Gợi ý slot nếu không phòng nào trống (nhiều phòng một ngày)
            /*for (Room room : availableRooms) {
                if (room.getRoomClass().getCapacity() < requiredCapacity) continue;

                List<Booking> roomBookings = bookingsByRoom.getOrDefault(room.getId(), Collections.emptyList());
                List<TimeRange> busy = roomBookings.stream()
                        .map(b -> new TimeRange(
                                b.getMeetingStart().minusMinutes(bufferTime),
                                b.getMeetingEnd().plusMinutes(bufferTime)))
                        .sorted(Comparator.comparing(TimeRange::start))
                        .collect(Collectors.toList());

                List<TimeRange> free = findFreeTimeSlots(busy);

                List<String> timeMorning = new ArrayList<>();
                List<String> timeAfternoon = new ArrayList<>();

                for (TimeRange slot : free) {
                    LocalTime start = slot.start().plusMinutes(bufferTime);
                    LocalTime end = slot.end().minusMinutes(bufferTime);
                    if (!start.isBefore(end)) continue;

                    if (end.isBefore(LocalTime.NOON)) {
                        timeMorning.add(start + " - " + end);
                    } else if (start.isAfter(LocalTime.NOON)) {
                        timeAfternoon.add(start + " - " + end);
                    } else {
                        if (start.isBefore(LocalTime.NOON)) {
                            timeMorning.add(start + " - " + LocalTime.NOON);
                        }
                        if (end.isAfter(LocalTime.NOON)) {
                            timeAfternoon.add(LocalTime.NOON + " - " + end);
                        }
                    }
                }

                SuggestedTimeSlotDto suggestedTimeSlotDto = null;
                boolean hasConflict = true;

                // Kiểm tra các date request exception thay thế thời gian
                if (exceptionMap.containsKey(date)) {
                    DateRequestException exception = exceptionMap.get(date);
                    LocalTime newStart = exception.getStartTime();
                    LocalTime newEnd = exception.getEndTime();

                    for (TimeRange slot : free) {
                        LocalTime start = slot.start().plusMinutes(bufferTime);
                        LocalTime end = slot.end().minusMinutes(bufferTime);
                        if (!start.isBefore(end)) continue;

                        if (!newStart.isBefore(start) && !newEnd.isAfter(end)) {
                            hasConflict = false;
                            break;
                        }
                    }
                }

                if (hasConflict) {
                    if (!timeMorning.isEmpty() || !timeAfternoon.isEmpty()) {
                        suggestedTimeSlotDto = new SuggestedTimeSlotDto(timeMorning, timeAfternoon);
                    } else {
                        continue;
                    }
                }

                results.add(new RoomScheduleResultDto(
                        date,
                        hasConflict,
                        room.getId().toString(),
                        suggestedTimeSlotDto
                ));
            }*/

            // Chỉ lấy phòng tối ưu nhất
            for (Room room : availableRooms) {
                if (room.getRoomClass().getCapacity() < requiredCapacity) continue;

                List<Booking> roomBookings = bookingsByRoom.getOrDefault(room.getId(), Collections.emptyList());
                List<TimeRange> busy = roomBookings.stream()
                        .map(b -> new TimeRange(
                                b.getMeetingStart().minusMinutes(bufferTime),
                                b.getMeetingEnd().plusMinutes(bufferTime)))
                        .sorted(Comparator.comparing(TimeRange::start))
                        .collect(Collectors.toList());

                List<TimeRange> free = findFreeTimeSlots(busy);

                List<String> timeMorning = new ArrayList<>();
                List<String> timeAfternoon = new ArrayList<>();

                for (TimeRange slot : free) {
                    LocalTime start = slot.start().plusMinutes(bufferTime);
                    LocalTime end = slot.end().minusMinutes(bufferTime);
                    if (!start.isBefore(end)) continue;

                    if (end.isBefore(LocalTime.NOON)) {
                        timeMorning.add(start + " - " + end);
                    } else if (start.isAfter(LocalTime.NOON)) {
                        timeAfternoon.add(start + " - " + end);
                    } else {
                        if (start.isBefore(LocalTime.NOON)) {
                            timeMorning.add(start + " - " + LocalTime.NOON);
                        }
                        if (end.isAfter(LocalTime.NOON)) {
                            timeAfternoon.add(LocalTime.NOON + " - " + end);
                        }
                    }
                }

                if (!timeMorning.isEmpty() || !timeAfternoon.isEmpty()) {
                    results.add(new RoomScheduleResultDto(date, true, room.getId().toString(),
                            new SuggestedTimeSlotDto(timeMorning, timeAfternoon)));
                    break;
                }
            }
        }

        return results;
    }


    private boolean hasConflict(List<Booking> bookings, LocalTime start, LocalTime end, int bufferTime) {
        for (Booking b : bookings) {
            LocalTime bStart = b.getMeetingStart().minusMinutes(bufferTime);
            LocalTime bEnd = b.getMeetingEnd().plusMinutes(bufferTime);
            if (!end.isBefore(bStart) && !start.isAfter(bEnd)) {
                return true;
            }
        }
        return false;
    }

    private List<TimeRange> findFreeTimeSlots(List<TimeRange> busySlots) {
        List<TimeRange> free = new ArrayList<>();
        LocalTime current = LocalTime.MIN;

        for (TimeRange busy : busySlots) {
            if (current.isBefore(busy.start())) {
                free.add(new TimeRange(current, busy.start()));
            }
            current = busy.end().isAfter(current) ? busy.end() : current;
        }

        if (current.isBefore(LocalTime.of(23, 59))) {
            free.add(new TimeRange(current, LocalTime.of(23, 59)));
        }

        return free;
    }










}
