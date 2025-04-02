package com.roomx.infrastructure.persistence.model.entity;

import com.roomx.infrastructure.persistence.model.ids.BookingParticipantEntityId;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = BookingParticipantEntity.TABLE_NAME)
public class BookingParticipantEntity {
    public static final String TABLE_NAME = "booking_participant";

    @EmbeddedId
    private BookingParticipantEntityId id;

    @MapsId("bookingId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity booking;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;


}