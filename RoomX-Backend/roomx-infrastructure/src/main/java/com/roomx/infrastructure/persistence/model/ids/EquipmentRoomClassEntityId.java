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
public class EquipmentRoomClassEntityId implements Serializable {
    public static final String COLUMN_ROOMCLASSID_NAME = "room_class_id";
    public static final String COLUMN_EQUIPMENTID_NAME = "equipment_id";
    private static final long serialVersionUID = -5404626469392094622L;

    @NotNull
    @Column(name = COLUMN_ROOMCLASSID_NAME, nullable = false)
    private UUID roomClassId;

    @NotNull
    @Column(name = COLUMN_EQUIPMENTID_NAME, nullable = false)
    private UUID equipmentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EquipmentRoomClassEntityId entity = (EquipmentRoomClassEntityId) o;
        return Objects.equals(this.roomClassId, entity.roomClassId) &&
                Objects.equals(this.equipmentId, entity.equipmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomClassId, equipmentId);
    }

}