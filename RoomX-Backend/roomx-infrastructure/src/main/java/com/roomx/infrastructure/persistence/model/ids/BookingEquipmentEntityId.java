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
public class BookingEquipmentEntityId implements Serializable {
    public static final String COLUMN_EQUIPMENTID_NAME = "equipment_id";
    public static final String COLUMN_BOOKINGID_NAME = "booking_id";
    private static final long serialVersionUID = -5098798814701683956L;

    @NotNull
    @Column(name = COLUMN_EQUIPMENTID_NAME, nullable = false)
    private UUID equipmentId;

    @NotNull
    @Column(name = COLUMN_BOOKINGID_NAME, nullable = false)
    private UUID bookingId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookingEquipmentEntityId entity = (BookingEquipmentEntityId) o;
        return Objects.equals(this.equipmentId, entity.equipmentId) &&
                Objects.equals(this.bookingId, entity.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equipmentId, bookingId);
    }

}