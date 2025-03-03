package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import com.roomx.infrastructure.multitenancy.persistence.model.ids.BookingRequestParticipantEntityId;
import jakarta.persistence.*;
import lombok.*;

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

    @EmbeddedId
    private BookingRequestParticipantEntityId id;

    @MapsId("bookingRequestId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_request_id", nullable = false)
    private com.roomx.infrastructure.multitenancy.persistence.model.entity.BookingRequestEntity bookingRequest;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private com.roomx.infrastructure.multitenancy.persistence.model.entity.UserEntity user;

}