package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.aggrerate.Service;
import com.roomx.domain.model.vo.ServiceRequestId;
import lombok.*;

import java.math.BigDecimal;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class ServiceRequest {
    private ServiceRequestId id;
    private BookingRequest bookingRequest;
    private Service service;
    private Short quantity;

}
