package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.model.aggrerate.RoomClass;
import com.roomx.domain.model.vo.EquipmentRoomClassId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EquipmentRoomClass {
    private EquipmentRoomClassId id;
    private RoomClass roomClass;
    private Equipment equipment;
    private Short quantity;
    private BigDecimal unitPrice;


}
