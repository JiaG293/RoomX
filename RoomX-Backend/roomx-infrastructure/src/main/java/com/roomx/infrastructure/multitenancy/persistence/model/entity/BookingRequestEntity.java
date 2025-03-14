package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = BookingRequestEntity.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(name = "unq_booking_request", columnNames = {"booking_request_code"})
})
public class BookingRequestEntity {
    public static final String TABLE_NAME = "booking_request";
    public static final String COLUMN_ID_NAME = "booking_request_id";
    public static final String COLUMN_APPROVALSTATUS_NAME = "approval_status";
    public static final String COLUMN_PRIORITY_NAME = "priority";
    public static final String COLUMN_STARTDATE_NAME = "start_date";
    public static final String COLUMN_ENDDATE_NAME = "end_date";
    public static final String COLUMN_CREATEDAT_NAME = "created_at";
    public static final String COLUMN_UPDATEDAT_NAME = "updated_at";
    public static final String COLUMN_BOOKINGREQUESTCODE_NAME = "booking_request_code";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private UUID id;

    @Size(max = 32)
    @Column(name = COLUMN_BOOKINGREQUESTCODE_NAME, length = 32)
    private String bookingRequestCode;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @Size(max = 32)
    @Column(name = COLUMN_APPROVALSTATUS_NAME, length = 32)
    private String approvalStatus;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private UserEntity requester;

    @Column(name = COLUMN_PRIORITY_NAME)
    private Short priority;

    @Column(name = COLUMN_STARTDATE_NAME)
    private Instant startDate;

    @Column(name = COLUMN_ENDDATE_NAME)
    private Instant endDate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_CREATEDAT_NAME)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_UPDATEDAT_NAME)
    private Instant updatedAt;

}