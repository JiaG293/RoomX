package com.roomx.shared.dto.booking.response;

import com.roomx.shared.dto.resource.response.ServiceResponse;

import java.math.BigDecimal;

public class ServiceBookingReponse {
    private String id;
    private ServiceResponse service;
    private BigDecimal unitPrice;
    private int quantity;
}
