package com.roomx.application.dto.resource.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRoomClassDetailResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RoomClassResponse roomClass;
    private ServiceResponse service;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal totalPrice;
}
