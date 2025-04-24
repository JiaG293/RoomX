package com.roomx.shared.dto.resource.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentPriceDto {
    private UUID id;
    private String equipmentCode;
    private String name;
    private String brand;
    private String description;
    private String quantity;
    private BigDecimal price;
}
