package com.roomx.application.dto.booking.response;

import com.roomx.application.dto.resource.response.ServiceResponse;

import java.math.BigDecimal;

public class ServiceBookingReponse {
    private String id;
    private ServiceResponse service;
    private BigDecimal unitPrice;
    private int quantity;
}
