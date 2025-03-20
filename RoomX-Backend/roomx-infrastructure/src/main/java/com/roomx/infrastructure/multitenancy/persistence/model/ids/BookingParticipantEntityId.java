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
public class BookingParticipantEntityId implements Serializable {
    public static final String COLUMN_BOOKINGID_NAME = "booking_id";
    public static final String COLUMN_USERID_NAME = "user_id";
    private static final long serialVersionUID = -4716241341948011778L;

    @NotNull
    @Column(name = COLUMN_BOOKINGID_NAME, nullable = false)
    private UUID bookingId;

    @NotNull
    @Column(name = COLUMN_USERID_NAME, nullable = false)
    private UUID userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookingParticipantEntityId entity = (BookingParticipantEntityId) o;
        return Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.bookingId, entity.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookingId);
    }

}