package com.roomx.infrastructure.persistence.model.entity;

import com.roomx.infrastructure.persistence.model.ids.ServiceRequestEntityId;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = ServiceRequestEntity.TABLE_NAME)
public class ServiceRequestEntity {
    public static final String TABLE_NAME = "service_request";
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

    @Column(name = COLUMN_QUANTITY_NAME)
    private Short quantity;

}