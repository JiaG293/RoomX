package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = RecurrenceEntity.TABLE_NAME)
public class RecurrenceEntity {
    public static final String TABLE_NAME = "recurrence";
    public static final String COLUMN_ID_NAME = "recurrence_id";
    public static final String COLUMN_RECURRENCETYPE_NAME = "recurrence_type";
    public static final String COLUMN_REPEATCOUNT_NAME = "repeat_count";
    public static final String COLUMN_STARTDATE_NAME = "start_date";
    public static final String COLUMN_ENDDATE_NAME = "end_date";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_request_id", nullable = false)
    private BookingRequestEntity bookingRequest;

    @Size(max = 32)
    @NotNull
    @Column(name = COLUMN_RECURRENCETYPE_NAME, nullable = false, length = 32)
    private String recurrenceType;

    @Column(name = COLUMN_REPEATCOUNT_NAME)
    private Short repeatCount;

    @Column(name = COLUMN_STARTDATE_NAME)
    private LocalDate startDate;

    @Column(name = COLUMN_ENDDATE_NAME)
    private LocalDate endDate;

}