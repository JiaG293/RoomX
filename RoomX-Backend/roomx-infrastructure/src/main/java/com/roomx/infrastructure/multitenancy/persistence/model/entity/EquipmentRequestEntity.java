package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import com.roomx.infrastructure.multitenancy.persistence.model.ids.EquipmentRequestEntityId;
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
@Table(name = EquipmentRequestEntity.TABLE_NAME)
public class EquipmentRequestEntity {
    public static final String TABLE_NAME = "equipment_request";
    public static final String COLUMN_UNITPRICE_NAME = "unit_price";
    public static final String COLUMN_QUANTITY_NAME = "quantity";

    @EmbeddedId
    private EquipmentRequestEntityId id;

    @MapsId("bookingRequestId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_request_id", nullable = false)
    private BookingRequestEntity bookingRequest;

    @MapsId("equipmentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private EquipmentEntity equipment;

    @Column(name = COLUMN_UNITPRICE_NAME)
    private BigDecimal unitPrice;

    @Column(name = COLUMN_QUANTITY_NAME)
    private Short quantity;

}