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
public class EquipmentRequestEntityId implements Serializable {
    public static final String COLUMN_BOOKINGREQUESTID_NAME = "booking_request_id";
    public static final String COLUMN_EQUIPMENTID_NAME = "equipment_id";
    private static final long serialVersionUID = 407661889836712465L;

    @NotNull
    @Column(name = COLUMN_BOOKINGREQUESTID_NAME, nullable = false)
    private UUID bookingRequestId;

    @NotNull
    @Column(name = COLUMN_EQUIPMENTID_NAME, nullable = false)
    private UUID equipmentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EquipmentRequestEntityId entity = (EquipmentRequestEntityId) o;
        return Objects.equals(this.bookingRequestId, entity.bookingRequestId) &&
                Objects.equals(this.equipmentId, entity.equipmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingRequestId, equipmentId);
    }

}