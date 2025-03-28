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
@Table(name = ApprovalFormEntity.TABLE_NAME)
public class ApprovalFormEntity {
    public static final String TABLE_NAME = "approval_form";
    public static final String COLUMN_ID_NAME = "approval_form_id";
    public static final String COLUMN_STATUS_NAME = "status";
    public static final String COLUMN_NOTE_NAME = "note";
    public static final String COLUMN_APPROVER_NAME = "approver";
    public static final String COLUMN_CREATEDAT_NAME = "created_at";
    public static final String COLUMN_UPDATEDAT_NAME = "updated_at";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = COLUMN_APPROVER_NAME)
    private UUID approver;

    @Size(max = 32)
    @Column(name = COLUMN_STATUS_NAME, length = 32)
    private String status;

    @Column(name = COLUMN_NOTE_NAME, length = Integer.MAX_VALUE)
    private String note;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_CREATEDAT_NAME)
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = COLUMN_UPDATEDAT_NAME)
    private Instant updatedAt;


    //RELATIONSHIP

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_request_id", nullable = false)
    private BookingRequestEntity bookingRequest;
}