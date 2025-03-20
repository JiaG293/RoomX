package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.model.vo.EquipmentRequestId;
import lombok.*;

import java.math.BigDecimal;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentRequest {
    private EquipmentRequestId id;
    private BookingRequest bookingRequest;
    private Equipment equipment;
    private Short quantity;


}
