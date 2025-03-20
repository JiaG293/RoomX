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
public class BookingServiceEntityId implements Serializable {
    public static final String COLUMN_BOOKINGID_NAME = "booking_id";
    public static final String COLUMN_SERVICEID_NAME = "service_id";
    private static final long serialVersionUID = 3010327444498471013L;

    @NotNull
    @Column(name = COLUMN_BOOKINGID_NAME, nullable = false)
    private UUID bookingId;

    @NotNull
    @Column(name = COLUMN_SERVICEID_NAME, nullable = false)
    private UUID serviceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookingServiceEntityId entity = (BookingServiceEntityId) o;
        return Objects.equals(this.serviceId, entity.serviceId) &&
                Objects.equals(this.bookingId, entity.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, bookingId);
    }

}