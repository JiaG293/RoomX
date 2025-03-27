/*
package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = BookingRequestParticipantEntity.TABLE_NAME)
public class BookingRequestParticipantEntity {
    public static final String TABLE_NAME = "booking_request_participant";
    public static final String COLUMN_ID_NAME = "booking_request_participant_id";
    public static final String COLUMN_USERID_NAME = "user_id";

    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private UUID id;

    @NotNull
    @Column(name = COLUMN_USERID_NAME, nullable = false)
    private UUID userId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_request_id", nullable = false)
    private BookingRequestEntity bookingRequest;

}*/
