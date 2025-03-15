package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.model.aggrerate.RoomClass;
import com.roomx.domain.model.vo.EquipmentRoomClassId;
import lombok.*;

import java.math.BigDecimal;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentRoomClass {
    private EquipmentRoomClassId id;
    private RoomClass roomClass;
    private Equipment equipment;
    private Short quantity;
    private BigDecimal unitPrice;

    public BigDecimal getTotalPrice() {
        return BigDecimal.valueOf(quantity).multiply(unitPrice);
    }


}
