package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
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
@Table(name = BookingEntity.TABLE_NAME, indexes = {
        @Index(name = "idx_booking", columnList = "meeting_start, meeting_end")
}, uniqueConstraints = {
        @UniqueConstraint(name = "unq_booking", columnNames = {"booking_code"}),
        @UniqueConstraint(name = "unq_booking_room_id", columnNames = {"room_id"})
})
public class BookingEntity {
    public static final String TABLE_NAME = "booking";
    public static final String COLUMN_ID_NAME = "booking_id";
    public static final String COLUMN_BOOKINGCODE_NAME = "booking_code";
    public static final String COLUMN_ROOMID_NAME = "room_id";
    public static final String COLUMN_MEETINGSTART_NAME = "meeting_start";
    public static final String COLUMN_MEETINGEND_NAME = "meeting_end";
    public static final String COLUMN_COUNT_NAME = "count";
    public static final String COLUMN_TOTALPRICE_NAME = "total_price";
    public static final String COLUMN_STATUS_NAME = "status";
    public static final String COLUMN_CREATEDAT_NAME = "created_at";
    public static final String COLUMN_UPDATEDAT_NAME = "updated_at";
    public static final String COLUMN_PREVIOUSROOMID_NAME = "previous_room_id";
    public static final String COLUMN_MEETINGDATE_NAME = "meeting_date";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(max = 32)
    @NotNull
    @Column(name = COLUMN_BOOKINGCODE_NAME, nullable = false, length = 32)
    private String bookingCode;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_request_id", nullable = false)
    private BookingRequestEntity bookingRequest;

    @NotNull
    @Column(name = COLUMN_ROOMID_NAME, nullable = false)
    private UUID roomId;

    @Column(name = COLUMN_MEETINGSTART_NAME)
    private LocalTime meetingStart;

    @Column(name = COLUMN_MEETINGEND_NAME)
    private LocalTime meetingEnd;

    @Column(name = COLUMN_MEETINGDATE_NAME)
    private LocalDate meetingDate;

    @Column(name = COLUMN_COUNT_NAME)
    private Short count;

    @Column(name = COLUMN_TOTALPRICE_NAME)
    private BigDecimal totalPrice;

    @Size(max = 32)
    @Column(name = COLUMN_STATUS_NAME, length = 32)
    private String status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_CREATEDAT_NAME)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_UPDATEDAT_NAME)
    private Instant updatedAt;

    @Column(name = COLUMN_PREVIOUSROOMID_NAME)
    private UUID previousRoomId;

}