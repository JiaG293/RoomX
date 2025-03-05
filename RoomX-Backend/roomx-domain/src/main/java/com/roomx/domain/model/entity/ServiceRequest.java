package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.aggrerate.Service;
import com.roomx.domain.model.vo.ServiceRequestId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ServiceRequest {
    private ServiceRequestId id;
    private BookingRequest bookingRequest;
    private Service service;
    private BigDecimal unitPrice;
    private Short quantity;



}
