package com.roomx.infrastructure.multitenancy.persistence.model.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class BookingRequestParticipantEntityId implements Serializable {
    public static final String COLUMN_BOOKINGREQUESTID_NAME = "booking_request_id";
    public static final String COLUMN_USERID_NAME = "user_id";
    private static final long serialVersionUID = 8548406367639153212L;

    @NotNull
    @Column(name = COLUMN_BOOKINGREQUESTID_NAME, nullable = false)
    private UUID bookingRequestId;

    @NotNull
    @Column(name = COLUMN_USERID_NAME, nullable = false)
    private UUID userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookingRequestParticipantEntityId entity = (BookingRequestParticipantEntityId) o;
        return Objects.equals(this.bookingRequestId, entity.bookingRequestId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingRequestId, userId);
    }

}