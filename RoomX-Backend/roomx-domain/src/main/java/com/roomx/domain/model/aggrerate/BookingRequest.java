package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.entity.EquipmentRequest;
import com.roomx.domain.model.entity.ServiceRequest;

import com.roomx.shared.enums.RecurrenceType;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.*;


import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class BookingRequest {
    private UUID id;
    private UUID requester;
    private Short priority;
    private int capacity;
    private String title;
    private String description;
    private String recurrenceType;
    private Short recurrenceInterval;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private UUID branchId;
    private UUID roomId;
    @Builder.Default
    private String daysOfWeek = "MO,TU,WE,TH,FR";
    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();

    private Instant endDateApproval;
    private String approvalStatus;



    @Builder.Default
    private List<ServiceRequest> services = new ArrayList<>();

    @Builder.Default
    private List<EquipmentRequest> equipments= new ArrayList<>();

    private List<String> participants;


    private static final Map<String, DayOfWeek> SHORT_DAY_MAP = Map.of(
            "MO", DayOfWeek.MONDAY,
            "TU", DayOfWeek.TUESDAY,
            "WE", DayOfWeek.WEDNESDAY,
            "TH", DayOfWeek.THURSDAY,
            "FR", DayOfWeek.FRIDAY,
            "SA", DayOfWeek.SATURDAY,
            "SU", DayOfWeek.SUNDAY
    );

    public RecurrenceType getRecurrenceTypeEnum() {
        return RecurrenceType.fromDisplayName(recurrenceType);
    }

    public void setRecurrenceTypeEnum(RecurrenceType type) {
        this.recurrenceType = type != null ? type.getDisplayName() : null;
    }

    // Lấy danh sách ngày trong tuần dưới dạng DayOfWeek enum
    public List<DayOfWeek> getAllowedDays() {
        return getDaysOfWeekList().stream()
                .map(String::trim)
                .map(SHORT_DAY_MAP::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // Convert danh sách string ngày trong tuần thành List<String>
    public List<String> getDaysOfWeekList() {
        return daysOfWeek != null ?
                Arrays.stream(daysOfWeek.split(","))
                        .filter(day -> !day.isEmpty())
                        .toList()
                : List.of();
    }

    public void setDaysOfWeekList(List<String> newDays) {
        this.daysOfWeek = String.join(",", newDays);
    }

    public boolean isDayInSchedule(String day) {
        return getDaysOfWeekList().contains(day);
    }

    // Get các ngày lặp lại
    public List<LocalDate> getOccurrences() {
        if (startDate == null || endDate == null || recurrenceType == null) {
            throw new AppException(ErrorCode.RECURRENCE_DATE_INVALID, startDate + " | " + endDate + " | " + recurrenceType);
        }

        RecurrenceType type = getRecurrenceTypeEnum();
        if (type == null) {
            throw new AppException(ErrorCode.RECURRENCE_DATE_INVALID, recurrenceType);
        }

        List<LocalDate> occurrences = new ArrayList<>();
        LocalDate currentDate = startDate;
        List<DayOfWeek> allowedDays = getAllowedDays(); // Dùng danh sách DayOfWeek enum

        while (!currentDate.isAfter(endDate)) {

            System.out.println("Checking date: " + currentDate + " (" + currentDate.getDayOfWeek() + ")");

            if (allowedDays.contains(currentDate.getDayOfWeek())) {
                occurrences.add(currentDate);
            }

            if (type == RecurrenceType.CUSTOM) {
                // Recurrence type là CUSTOM
                if (recurrenceInterval == null || recurrenceInterval < 1) {
                    throw new AppException(ErrorCode.RECURRENCE_DATE_INVALID, recurrenceInterval);
                }
                currentDate = type.nextDate(currentDate, recurrenceInterval);
            } else {
                currentDate = type.nextDate(currentDate, (short) 1); // Các loại khác mặc định là 1
            }
        }
        return occurrences;
    }








}
