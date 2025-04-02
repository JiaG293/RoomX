package com.roomx.infrastructure.persistence.model.ids;

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
public class ServiceRequestEntityId implements Serializable {
    public static final String COLUMN_BOOKINGREQUESTID_NAME = "booking_request_id";
    public static final String COLUMN_SERVICEID_NAME = "service_id";
    private static final long serialVersionUID = -7354845637187952529L;

    @NotNull
    @Column(name = COLUMN_BOOKINGREQUESTID_NAME, nullable = false)
    private UUID bookingRequestId;

    @NotNull
    @Column(name = COLUMN_SERVICEID_NAME, nullable = false)
    private UUID serviceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ServiceRequestEntityId entity = (ServiceRequestEntityId) o;
        return Objects.equals(this.bookingRequestId, entity.bookingRequestId) &&
                Objects.equals(this.serviceId, entity.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingRequestId, serviceId);
    }

}