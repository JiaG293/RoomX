package com.roomx.infrastructure.persistence.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = DateRequestExceptionEntity.TABLE_NAME)
public class DateRequestExceptionEntity {
    public static final String TABLE_NAME = "date_request_exception";
    public static final String COLUMN_ID_NAME = "date_booking_id";
    public static final String COLUMN_DATE_NAME = "date";
    public static final String COLUMN_STARTTIME_NAME = "start_time";
    public static final String COLUMN_ENDTIME_NAME = "end_time";
    public static final String COLUMN_ROOMID_NAME = "room_id";


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private UUID id;

    @Column(name = COLUMN_DATE_NAME)
    private LocalDate date;

    @Column(name = COLUMN_STARTTIME_NAME)
    private LocalTime startTime;

    @Column(name = COLUMN_ENDTIME_NAME)
    private LocalTime endTime;

    @Column(name = COLUMN_ROOMID_NAME)
    private UUID roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_request_id")
    private BookingRequestEntity bookingRequest;

}