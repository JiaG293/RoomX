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
public class ServiceRoomClassEntityId implements Serializable {
    public static final String COLUMN_ROOMCLASSID_NAME = "room_class_id";
    public static final String COLUMN_SERVICEID_NAME = "service_id";
    private static final long serialVersionUID = 2206602900526861921L;

    @NotNull
    @Column(name = COLUMN_ROOMCLASSID_NAME, nullable = false)
    private UUID roomClassId;

    @NotNull
    @Column(name = COLUMN_SERVICEID_NAME, nullable = false)
    private UUID serviceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ServiceRoomClassEntityId entity = (ServiceRoomClassEntityId) o;
        return Objects.equals(this.serviceId, entity.serviceId) &&
                Objects.equals(this.roomClassId, entity.roomClassId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, roomClassId);
    }

}