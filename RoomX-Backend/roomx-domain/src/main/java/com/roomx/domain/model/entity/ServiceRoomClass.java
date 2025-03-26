package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.RoomClass;
import com.roomx.domain.model.aggrerate.Service;
import com.roomx.domain.model.vo.ServiceRoomClassId;
import lombok.*;

import java.math.BigDecimal;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class ServiceRoomClass {
    private ServiceRoomClassId id;
    private RoomClass roomClass;
    private Service service;
    private Short quantity;
    private ServicePriceHistory price;

    public BigDecimal getTotalPrice() {
        return price != null ? price.getUnitPrice().multiply(BigDecimal.valueOf(quantity)) : BigDecimal.ZERO;
    }


}
