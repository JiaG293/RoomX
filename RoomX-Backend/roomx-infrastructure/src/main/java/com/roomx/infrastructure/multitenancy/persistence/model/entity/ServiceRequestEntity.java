package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import com.roomx.infrastructure.multitenancy.persistence.model.ids.ServiceRequestEntityId;
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
@Table(name = ServiceRequestEntity.TABLE_NAME)
public class ServiceRequestEntity {
    public static final String TABLE_NAME = "service_request";
    public static final String COLUMN_UNITPRICE_NAME = "unit_price";
    public static final String COLUMN_QUANTITY_NAME = "quantity";

    @EmbeddedId
    private ServiceRequestEntityId id;

    @MapsId("bookingRequestId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_request_id", nullable = false)
    private BookingRequestEntity bookingRequest;

    @MapsId("serviceId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @Column(name = COLUMN_UNITPRICE_NAME)
    private BigDecimal unitPrice;

    @Column(name = COLUMN_QUANTITY_NAME)
    private Short quantity;

}