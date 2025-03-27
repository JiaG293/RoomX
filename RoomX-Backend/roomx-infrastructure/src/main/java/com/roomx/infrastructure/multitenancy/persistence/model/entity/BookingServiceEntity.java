package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import com.roomx.infrastructure.multitenancy.persistence.model.ids.BookingServiceEntityId;
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
@Table(name = BookingServiceEntity.TABLE_NAME)
public class BookingServiceEntity {
    public static final String TABLE_NAME = "booking_service";
    public static final String COLUMN_QUANTITY_NAME = "quantity";
    public static final String COLUMN_UNITPRICE_NAME = "unit_price";

    @EmbeddedId
    private BookingServiceEntityId id;

    @MapsId("bookingId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity bookingEntity;

    @MapsId("serviceId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @Column(name = COLUMN_QUANTITY_NAME)
    private Short quantity;

    @Column(name = COLUMN_UNITPRICE_NAME)
    private BigDecimal unitPrice;

}