package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.model.vo.EquipmentRequestId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EquipmentRequest {
    private EquipmentRequestId id;
    private BookingRequest bookingRequest;
    private Equipment equipment;
    private BigDecimal unitPrice;
    private Short quantity;



}
