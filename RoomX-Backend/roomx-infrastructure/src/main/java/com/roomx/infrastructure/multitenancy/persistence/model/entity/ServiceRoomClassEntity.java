package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import com.roomx.infrastructure.multitenancy.persistence.model.ids.ServiceRoomClassEntityId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = ServiceRoomClassEntity.TABLE_NAME)
public class ServiceRoomClassEntity {
    public static final String TABLE_NAME = "service_room_class";
    public static final String COLUMN_QUANTITY_NAME = "quantity";
    public static final String COLUMN_UNITPRICE_NAME = "unit_price";

    @EmbeddedId
    private ServiceRoomClassEntityId id;

    @MapsId("roomClassId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_class_id", nullable = false)
    private RoomClassEntity roomClassEntity;

    @MapsId("serviceId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @Column(name = COLUMN_QUANTITY_NAME)
    private Short quantity;

    @Column(name = COLUMN_UNITPRICE_NAME)
    private BigDecimal unitPrice;

}