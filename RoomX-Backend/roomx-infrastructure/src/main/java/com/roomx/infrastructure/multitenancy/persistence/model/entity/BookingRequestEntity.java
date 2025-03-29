package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = BookingRequestEntity.TABLE_NAME)
public class BookingRequestEntity {
    public static final String TABLE_NAME = "booking_request";
    public static final String COLUMN_ID_NAME = "booking_request_id";
    public static final String COLUMN_PRIORITY_NAME = "priority";
    public static final String COLUMN_CREATEDAT_NAME = "created_at";
    public static final String COLUMN_UPDATEDAT_NAME = "updated_at";
    public static final String COLUMN_ENDDATEAPPROVAL_NAME = "end_date_approval";
    public static final String COLUMN_DAYSOFWEEK_NAME = "days_of_week";
    public static final String COLUMN_ENDTIME_NAME = "end_time";
    public static final String COLUMN_STARTTIME_NAME = "start_time";
    public static final String COLUMN_ENDDATE_NAME = "end_date";
    public static final String COLUMN_STARTDATE_NAME = "start_date";
    public static final String COLUMN_RECURRENCEINTERVAL_NAME = "recurrence_interval";
    public static final String COLUMN_RECURRENCETYPE_NAME = "recurrence_type";
    public static final String COLUMN_CAPACITY_NAME = "capacity";
    public static final String COLUMN_REQUESTER_NAME = "requester";
    public static final String COLUMN_BRANCHID_NAME = "branch_id";
    public static final String COLUMN_ROOMID_NAME = "room_id";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = COLUMN_REQUESTER_NAME, nullable = false)
    private UUID requester;

    @Column(name = COLUMN_PRIORITY_NAME)
    private Short priority;

    @Column(name = COLUMN_CAPACITY_NAME)
    private int capacity;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_CREATEDAT_NAME)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_UPDATEDAT_NAME)
    private Instant updatedAt;

    @Column(name = COLUMN_ENDDATEAPPROVAL_NAME)
    private Instant endDateApproval;

    @Column(name = COLUMN_BRANCHID_NAME)
    private UUID branchId;

    @Column(name = COLUMN_ROOMID_NAME)
    private UUID roomId;

    @Column(name = COLUMN_DAYSOFWEEK_NAME, length = Integer.MAX_VALUE)
    private String daysOfWeek;

    @Column(name = COLUMN_ENDTIME_NAME)
    private LocalTime endTime;

    @Column(name = COLUMN_STARTTIME_NAME)
    private LocalTime startTime;

    @Column(name = COLUMN_ENDDATE_NAME)
    private LocalDate endDate;

    @Column(name = COLUMN_STARTDATE_NAME)
    private LocalDate startDate;

    @Column(name = COLUMN_RECURRENCEINTERVAL_NAME)
    private Short recurrenceInterval;

    @Size(max = 32)
    @Column(name = COLUMN_RECURRENCETYPE_NAME, length = 32)
    private String recurrenceType;

    @ElementCollection
    @CollectionTable(name = "booking_request_participant", joinColumns = @JoinColumn(name = "booking_request_id"))
    @Column(name = "participants", length = 512)
    private List<String> participants = new ArrayList<>();

}