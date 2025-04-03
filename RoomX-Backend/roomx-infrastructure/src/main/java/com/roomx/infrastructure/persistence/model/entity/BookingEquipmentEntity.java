package com.roomx.infrastructure.persistence.model.entity;

import com.roomx.infrastructure.persistence.model.ids.BookingEquipmentEntityId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = BookingEquipmentEntity.TABLE_NAME)
public class BookingEquipmentEntity {
    public static final String TABLE_NAME = "booking_equipment";
    public static final String COLUMN_QUANTITY_NAME = "quantity";
    public static final String COLUMN_UNITPRICE_NAME = "unit_price";

    @EmbeddedId
    private BookingEquipmentEntityId id;

    @MapsId("equipmentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private EquipmentEntity equipment;

    @MapsId("bookingId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity booking;

    @Column(name = COLUMN_QUANTITY_NAME)
    private Short quantity;

}