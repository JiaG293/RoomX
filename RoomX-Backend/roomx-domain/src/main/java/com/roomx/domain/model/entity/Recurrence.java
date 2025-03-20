package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Recurrence {
    private UUID id;
    private UUID bookingRequestId;
    private String recurrenceType;
    private Short interval;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    @Builder.Default
    private String daysOfWeek = "MO,TU,WE,TH,FR";


   /* public List<LocalDate> getOccurrences() {
        List<LocalDate> occurrences = new ArrayList<>();
        LocalDate start = startDate.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = endDate.atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate currentDate = start;

        switch (recurrenceType.toUpperCase()) {
            case "DAILY":
                while (!currentDate.isAfter(end)) {
                    occurrences.add(currentDate);
                    currentDate = currentDate.plusDays(1);
                }
                break;
            case "WEEKLY":
                while (!currentDate.isAfter(end)) {
                    occurrences.add(currentDate);
                    currentDate = currentDate.plusWeeks(1);
                }
                break;
            case "MONTHLY":
                while (!currentDate.isAfter(end)) {
                    occurrences.add(currentDate);
                    currentDate = currentDate.plusMonths(1);
                }
                break;
            case "YEARLY":
                while (!currentDate.isAfter(end)) {
                    occurrences.add(currentDate);
                    currentDate = currentDate.plusYears(1);
                }
                break;
            default: throw new AppException(ErrorCode.RECURRENCE_DATE_INVALID, "DAILY | WEEKLY | MONTHLY | YEARLY");
        }

        return occurrences;
    }*/





}
