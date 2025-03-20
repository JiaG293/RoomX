package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import com.roomx.infrastructure.multitenancy.persistence.model.ids.BookingParticipantEntityId;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = BookingParticipantEntity.TABLE_NAME)
public class BookingParticipantEntity {
    public static final String TABLE_NAME = "booking_participant";

    @EmbeddedId
    private BookingParticipantEntityId id;

    @MapsId("bookingId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private com.roomx.infrastructure.multitenancy.persistence.model.entity.BookingEntity booking;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private com.roomx.infrastructure.multitenancy.persistence.model.entity.UserEntity user;

}