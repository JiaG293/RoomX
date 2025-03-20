package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = RecurrenceEntity.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(name = "unq_recurrence_booking_request_id", columnNames = {"booking_request_id"})
})
public class RecurrenceEntity {
    public static final String TABLE_NAME = "recurrence";
    public static final String COLUMN_ID_NAME = "recurrence_id";
    public static final String COLUMN_BOOKINGREQUESTID_NAME = "booking_request_id";
    public static final String COLUMN_RECURRENCETYPE_NAME = "recurrence_type";
    public static final String COLUMN_INTERVAL_NAME = "\"interval\"";
    public static final String COLUMN_STARTDATE_NAME = "start_date";
    public static final String COLUMN_ENDDATE_NAME = "end_date";
    public static final String COLUMN_TIMESTART_NAME = "time_start";
    public static final String COLUMN_TIMEEND_NAME = "time_end";
    public static final String COLUMN_DAYSOFWEEK_NAME = "days_of_week";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private UUID id;

    @Column(name = COLUMN_BOOKINGREQUESTID_NAME)
    private UUID bookingRequestId;

    @Size(max = 32)
    @NotNull
    @Column(name = COLUMN_RECURRENCETYPE_NAME, nullable = false, length = 32)
    private String recurrenceType;

    @Column(name = COLUMN_INTERVAL_NAME)
    private Short interval;

    @Column(name = COLUMN_STARTDATE_NAME)
    private LocalDate startDate;

    @Column(name = COLUMN_ENDDATE_NAME)
    private LocalDate endDate;

    @Column(name = COLUMN_TIMESTART_NAME)
    private LocalTime timeStart;

    @Column(name = COLUMN_TIMEEND_NAME)
    private LocalTime timeEnd;

//    @ColumnDefault("MO,TU,WE,TH,FR")
    @Column(name = COLUMN_DAYSOFWEEK_NAME)
    private String daysOfWeek;

}