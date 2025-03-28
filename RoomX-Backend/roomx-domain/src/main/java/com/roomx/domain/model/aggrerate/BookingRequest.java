package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.entity.EquipmentRequest;
import com.roomx.domain.model.entity.ServiceRequest;

import com.roomx.shared.enums.RecurrenceType;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import lombok.*;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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



    @Builder.Default
    private List<ServiceRequest> services = new ArrayList<>();

    @Builder.Default
    private List<EquipmentRequest> equipments= new ArrayList<>();

    private List<String> participants;


    public RecurrenceType getRecurrenceTypeEnum() {
        return RecurrenceType.fromDisplayName(recurrenceType);
    }

    public void setRecurrenceTypeEnum(RecurrenceType type) {
        this.recurrenceType = type != null ? type.getDisplayName() : null;
    }


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
        List<String> allowedDays = getDaysOfWeekList();

        while (!currentDate.isAfter(endDate)) {
            if (allowedDays.contains(currentDate.getDayOfWeek().name().substring(0, 2))) {
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
