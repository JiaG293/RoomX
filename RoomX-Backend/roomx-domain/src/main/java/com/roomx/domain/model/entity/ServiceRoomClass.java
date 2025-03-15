package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.RoomClass;
import com.roomx.domain.model.aggrerate.Service;
import com.roomx.domain.model.vo.ServiceRoomClassId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ServiceRoomClass {
    private ServiceRoomClassId id;
    private RoomClass roomClass;
    private Service service;
    private Short quantity;
    private BigDecimal unitPrice;

    public BigDecimal getTotalPrice() {
        return BigDecimal.valueOf(quantity).multiply(unitPrice);
    }


}
