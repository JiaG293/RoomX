package com.roomx.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentType {
    private UUID equipmentTypeId;
    private Double price;
    private String name;
}
