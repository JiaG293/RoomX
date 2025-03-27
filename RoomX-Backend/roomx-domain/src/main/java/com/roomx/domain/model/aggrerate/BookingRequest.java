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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
        return Arrays.stream(daysOfWeek.split(","))
                .filter(day -> !day.isEmpty())
                .toList();
    }

    public void setDaysOfWeekList(List<String> newDays) {
        this.daysOfWeek = String.join(",", newDays);
    }

    public boolean isDayInSchedule(String day) {
        return getDaysOfWeekList().contains(day);
    }


    public List<LocalDate> getOccurrences() {
        if (startDate == null || endDate == null || recurrenceType == null) {
            throw new AppException(ErrorCode.RECURRENCE_DATE_INVALID, "Missing required fields");
        }

        RecurrenceType type = getRecurrenceTypeEnum();
        if (type == null) {
            throw new AppException(ErrorCode.RECURRENCE_DATE_INVALID, "Invalid recurrence type");
        }

        List<LocalDate> occurrences = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            occurrences.add(currentDate);
            currentDate = type.nextDate(currentDate, recurrenceInterval);
        }
        return occurrences;
    }


}
